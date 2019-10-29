package com.hj.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.hj.domain.Details;
import com.hj.domain.Room;
import com.hj.domain.User;
import com.hj.utils.DataSourceUtils;

public class UserDao {

	private String notR = "不可预约";
	private String canR = "可预约";
	private String hasR = "已预约";

	/*
	 * 根据学号和密码查询此人，如果有此用户，返回该用户，否则返回null
	 */

	public User queryuser(String student_ID, String password) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where student_ID=? and password=? ";
		return runner.query(sql, new BeanHandler<User>(User.class), student_ID, password);

	}

	/*
	 * 查询某一天活动室预约情况
	 */

	public Room queryroom(String day) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

		// 判断room表中有无该天的数据，没有，添加之
		String queryExist = "select count(*) from room where day=?";
		if (!((long) runner.query(queryExist, new ScalarHandler(), day) > 0)) {

			String insertroom = "insert into room values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			runner.update(insertroom, day, notR, notR, notR, notR, notR, notR, notR, notR, canR, canR, canR, canR, canR,
					canR, canR, canR, canR, canR, canR, canR, canR, canR, notR, notR);

		}

		String sql = "select * from room where day=?";
		return runner.query(sql, new BeanHandler<Room>(Room.class), day);
	}

	/*
	 * 查询某一时刻是否被预约
	 */
	public String isReserve(String day, int i, int j) throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select " + "t" + i + "_" + j + " from room where day=?";
		return (String) runner.query(sql, new ScalarHandler(), day);
	}

	/*
	 * 预约成功，更新数据库room表与user表
	 */
	public void updateroom(User user, String day, int i, int j) throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

		// 更新room表
		String updateroom = "update room set " + "t" + i + "_" + j + "=? where day=?";
		runner.update(updateroom, hasR, day);

		// 更新user表
		String querytimes = "select totaltimes from user where uid=?";
		int totaltimes = (int) runner.query(querytimes, new ScalarHandler(), user.getUid());
		String updatetimes = "update user set totaltimes=? where uid=?";
		runner.update(updatetimes, totaltimes + 1, user.getUid());
	}

	/*
	 * /* 预约成功，更新数据库reservation表
	 */

	public void updatereservation(User user, String day, int start, int end, String subject, String content)
			throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

		// 更新reservation表
		String insertreservation = "insert into reservation values(?,?,?,?,?,?,?)";
		runner.update(insertreservation, user.getUid(), day, start, end, end - start, subject, content);

	}

	/*
	 * 取消预约
	 */
	public void cancelroom(User user, String day, int i, int j) throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

		// 更新room表
		String updateroom = "update room set " + "t" + i + "_" + j + "=? where day=?";
		runner.update(updateroom, canR, day);

	}

	public void cancelreserve(User user, String day, int start, int end) throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

		// 删除reservation表
		String deletereservation = "delete from reservation where uid=? and day=? and start=? and end=?";
		runner.update(deletereservation, user.getUid(), day, start, end);

		// 更新user表
		String querytimes = "select totaltimes from user where uid=?";
		int totaltimes = (int) runner.query(querytimes, new ScalarHandler(), user.getUid());
		String updatetimes = "update user set totaltimes=? where uid=?";
		runner.update(updatetimes, totaltimes - 1, user.getUid());

	}

	/*
	 * 查询该用户本周的预约次数与预约总时长
	 */
	public int[] reserveTimesOrlength(User user, String dateOfMon, String dateOfSun, int length) throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		// 用户该周的预约次数与预约时长
		int timesAndlength[] = new int[2];

		// 查询该用户本周的预约次数
		String querytimes = "select count(*) from reservation where uid=? and day between ? and ?";
		Long times = (Long) runner.query(querytimes, new ScalarHandler(), user.getUid(), dateOfMon, dateOfSun);

		// 查询该用户本周的预约总时长
		String querylength = "select sum(length) from reservation where uid=? and day between ? and ? ";
		BigDecimal lengths = (BigDecimal) runner.query(querylength, new ScalarHandler(), user.getUid(), dateOfMon,
				dateOfSun);

		timesAndlength[0] = times.intValue() + 1;
		if (lengths == null)
			timesAndlength[1] = length;
		else
			timesAndlength[1] = lengths.intValue() + length;

		return timesAndlength;
	}

	/*
	 * 查询所有用户总的历史记录条数
	 */
	public int getTotalUserDetailsCount() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from reservation";
		Long query = (Long) runner.query(sql, new ScalarHandler());
		return query.intValue();
	}

	/*
	 * 查询某用户的所有预约记录
	 */
	public List<Details> querydetails(User user, int index, int currentCount) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from reservation where uid=? limit ?,?";
		return runner.query(sql, new BeanListHandler<Details>(Details.class), user.getUid(), index, currentCount);
	}

}
