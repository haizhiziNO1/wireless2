package com.hj.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hj.domain.Details;
import com.hj.domain.PageDetails;
import com.hj.domain.User;
import com.hj.service.UserService;

public class ReserveDetails extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserService service = new UserService();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String currentPageStr = request.getParameter("currentPage");
		if (currentPageStr == null)
			currentPageStr = "1";
		int currentPage = Integer.parseInt(currentPageStr);
		// 认为每页显示10条
		int currentCount = 10;

		PageDetails<Details> pagedetails = service.reservedatails(user, currentPage, currentCount);

		request.setAttribute("pagedetails", pagedetails);

		request.getRequestDispatcher("./user/details.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}