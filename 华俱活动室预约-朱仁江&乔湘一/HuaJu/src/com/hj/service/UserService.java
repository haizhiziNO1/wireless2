package com.hj.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import com.hj.dao.UserDao;
import com.hj.domain.Details;
import com.hj.domain.PageDetails;
import com.hj.domain.Room;
import com.hj.domain.User;
import com.hj.utils.DataSourceUtils;

public class UserService {

	private String notR = "不可预约";
	private String canR = "可预约";
	private String hasR = "已预约";

	/*
	 * 根据学号和密码查询此人，如果有此用户，返回该用户，否则返回null
	 */
	public User userlogin(String student_ID, String password) {
		UserDao dao = new UserDao();
		User user = null;

		try {
			user = dao.queryuser(student_ID, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;

	}

	/*
	 * 1.权限设置：组员权限只能预约往后2天（48小时）内的活动室； 每7天最多预约3次，使用总时长不超过6小时。
	 * 组长权限只能预约往后2周（14天）内的活动室；但对于预约次数和预约总时长无限制。 2.预约时间段精度为1小时。预约最短时间为1小时，最长6小时。
	 */

	public String reserve(User user, String day, String startstr, String endstr, String subject, String content) {

		String info = null;

		UserDao dao = new UserDao();

		// 获取当前的日期
		LocalDate today = LocalDate.now();
		// 预约最长日期
		LocalDate maxday = null;
		// 预约日期
		LocalDate date = LocalDate.parse(day);

		// 获得预约日期的周一
		LocalDate localdateOfMon = date.with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1);
		String dateOfMon = localdateOfMon.toString();
		// 获得预约日期的周日
		LocalDate localdateOfSun = localdateOfMon.plusDays(6);
		String dateOfSun = localdateOfSun.toString();

		// 预约日期是否在最长日期内
		boolean flag = false;

		// 预约开始时刻
		int start = Integer.parseInt(startstr);
		// 预约结束时刻
		int end = Integer.parseInt(endstr);
		// 预约时长
		int length = end - start;

		// 用户该周的预约次数与预约时长
		int timesAndlength[] = new int[2];

		// 判断当次预约时长
		if (!(length <= 6 && length >= 1)) {
			info = "当次预约最短时间为1小时，最长6小时";
			return info;
		}

		try {
			if (user.getRole().equals("组员")) {
				// 预约最长日期 普通+2
				maxday = today.plusDays(2);
				// 判断组员每7天最多预约3次，使用总时长不超过6小时
				timesAndlength = dao.reserveTimesOrlength(user, dateOfMon, dateOfSun, length);
			} else {
				// 预约最长日期 组长/主席+14
				maxday = today.plusDays(14);
			}

			flag = maxday.compareTo(date) > 0 ? true : false;
			if (!flag) {
				info = "预约日期不在权限预约范围内";
				return info;
			}

			if (timesAndlength[0] > 3) {
				info = "本周预约次数超过3次";
				return info;
			}

			if (timesAndlength[1] > 6) {
				info = "本周预约总时长超过6小时";
				return info;
			}

			// 开启事务
			DataSourceUtils.startTransaction();
			// 判断能够预约
			for (int i = start; i < end; i++) {
				info = dao.isReserve(day, i, i + 1);
				if (!info.equals(canR)) {
					info = notR;
					return info;
				}
			}

			// 预约成功
			for (int i = start; i < end; i++) {
				// 更新room表
				dao.updateroom(user, day, i, i + 1);
			}

			// 更新reservation表
			dao.updatereservation(user, day, start, end, subject, content);

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				// 回滚
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		try {
			// 提交事务
			DataSourceUtils.commitAndRelease();
		} catch (SQLException e) {
		}
		return "预约成功";
	}

	/*
	 * 查询某一天的预约情况
	 */
	public Room reserveinfo(String day) {

		UserDao dao = new UserDao();
		Room reserveInfo = null;
		// String[] reserveinfostr = new String[24];
		try {
			// 开启事务
			DataSourceUtils.startTransaction();
			reserveInfo = dao.queryroom(day);

			/*
			 * reserveinfostr[0] = reserveInfo.getT0_1(); reserveinfostr[1] =
			 * reserveInfo.getT1_2(); reserveinfostr[2] = reserveInfo.getT2_3();
			 * reserveinfostr[3] = reserveInfo.getT3_4(); reserveinfostr[4] =
			 * reserveInfo.getT4_5(); reserveinfostr[5] = reserveInfo.getT5_6();
			 * reserveinfostr[6] = reserveInfo.getT6_7(); reserveinfostr[7] =
			 * reserveInfo.getT7_8(); reserveinfostr[8] = reserveInfo.getT8_9();
			 * reserveinfostr[9] = reserveInfo.getT9_10(); reserveinfostr[10] =
			 * reserveInfo.getT10_11(); reserveinfostr[11] = reserveInfo.getT11_12();
			 * reserveinfostr[12] = reserveInfo.getT12_13(); reserveinfostr[13] =
			 * reserveInfo.getT13_14(); reserveinfostr[14] = reserveInfo.getT14_15();
			 * reserveinfostr[15] = reserveInfo.getT15_16(); reserveinfostr[16] =
			 * reserveInfo.getT16_17(); reserveinfostr[17] = reserveInfo.getT17_18();
			 * reserveinfostr[18] = reserveInfo.getT18_19(); reserveinfostr[19] =
			 * reserveInfo.getT19_20(); reserveinfostr[20] = reserveInfo.getT20_21();
			 * reserveinfostr[21] = reserveInfo.getT21_22(); reserveinfostr[22] =
			 * reserveInfo.getT22_23(); reserveinfostr[23] = reserveInfo.getT23_24();
			 */

		} catch (SQLException e) {
			// 回滚
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		// 提交事务
		try {
			DataSourceUtils.commitAndRelease();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reserveInfo;
	}

	/*
	 * 取消预约
	 */

	public void cancel(User user, String day, String startstr, String endstr) {

		UserDao dao = new UserDao();
		// 预约开始时刻
		int start = Integer.parseInt(startstr);
		// 预约结束时刻
		int end = Integer.parseInt(endstr);

		try {
			// 开启事务
			DataSourceUtils.startTransaction();

			// 取消预约
			for (int i = start; i < end; i++) {
				dao.cancelroom(user, day, i, i + 1);
			}
			dao.cancelreserve(user, day, start, end);

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				// 回滚
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		try {
			// 提交事务
			DataSourceUtils.commitAndRelease();
		} catch (SQLException e) {
		}
	}

	/*
	 * 分页查询预约记录
	 */
	public PageDetails<Details> reservedatails(User user, int currentPage, int currentCount) {
		UserDao dao = new UserDao();
		PageDetails<Details> page = new PageDetails();

		// 1、当前页
		page.setCurrentPage(currentPage);
		// 2、当前页显示的条数
		page.setCurrentCount(currentCount);
		// 3、总条数
		int totalCount = user.getTotaltimes();

		/**
		 * try { totalCount = dao.getTotalUserDetailsCount(user); } catch (SQLException
		 * e) { e.printStackTrace(); }
		 */

		page.setTotalCount(totalCount);
		// 4、总页数
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
		page.setTotalPage(totalPage);
		int index = (currentPage - 1) * currentCount;
		List<Details> details = null;
		try {
			details = dao.querydetails(user, index, currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		page.setDetails(details);

		return page;
	}

}
