package com.hj.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.hj.domain.Details;
import com.hj.domain.PageDetails;
import com.hj.domain.User;
import com.hj.service.UserService;

public class CancelReserve extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String day = request.getParameter("day");

		String start = request.getParameter("start");
		String end = request.getParameter("end");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		UserService service = new UserService();
		service.cancel(user, day, start, end);

		// 预约详情
		String currentPageStr = "1";
		int currentPage = Integer.parseInt(currentPageStr);
		// 认为每页显示10条
		int currentCount = 10;
		PageDetails<Details> pagedetails = service.reservedatails(user, currentPage, currentCount);
		session.setAttribute("pagedetails", pagedetails);
		// 数据已json形式发送
		Gson gson = new Gson();
		String json = gson.toJson(pagedetails);

		// 解决乱码
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}