package com.hj.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hj.domain.Details;
import com.hj.domain.PageDetails;
import com.hj.domain.Room;
import com.hj.domain.User;
import com.hj.service.UserService;

public class ReserveRoom extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String day = request.getParameter("time");

		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String start = day.substring(11, 13);
		String end = day.substring(33, 35);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}

		String day1 = day.substring(0, 10);
		String day2 = day.substring(22, 32);

		if (!day1.equals(day2)) {
			request.setAttribute("returnInfo", "日期设置不正确");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		} else {
			day = day1;
		}

		UserService service = new UserService();

		// Info 不可预约、已预约、预约成功;
		String Info = service.reserve(user, day, start, end, subject, content);
		request.setAttribute("returnInfo", Info);

		// reserveInfo 该日期的预约情况
		Room reserveInfo = service.reserveinfo(day);
		request.setAttribute("reserveInfo", reserveInfo);

		String currentPageStr = "1";
		int currentPage = Integer.parseInt(currentPageStr);
		// 认为每页显示10条
		int currentCount = 10;
		PageDetails<Details> pagedetails = service.reservedatails(user, currentPage, currentCount);
		session.setAttribute("pagedetails", pagedetails);

		request.getRequestDispatcher("index.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}