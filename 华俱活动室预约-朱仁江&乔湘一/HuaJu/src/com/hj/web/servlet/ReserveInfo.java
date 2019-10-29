package com.hj.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.hj.domain.Room;
import com.hj.service.UserService;

public class ReserveInfo extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserService service = new UserService();
		// 预约日期
		String day = request.getParameter("day");

		day = day.substring(0, 10);

		if (day == null) {

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			day = df.format(new Date());// new Date()为获取当前系统时间
			request.setAttribute("today", day);

			// reserveInfo 该日期的预约情况
			Room reserveInfo = service.reserveinfo(day);

			request.setAttribute("reserveInfo", reserveInfo);
			request.getRequestDispatcher("./user/reserve.jsp").forward(request, response);
			return;

		} else {

			// reserveInfo 该日期的预约情况
			Room reserveInfo = service.reserveinfo(day);

			// 数据已json形式发送
			Gson gson = new Gson();
			String json = gson.toJson(reserveInfo);

			// 解决乱码
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(json);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}