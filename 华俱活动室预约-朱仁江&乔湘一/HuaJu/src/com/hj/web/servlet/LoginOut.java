package com.hj.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginOut extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		// 从session中将user删除
		session.removeAttribute("user");

		// 将存储在客户端的cookie删除掉
		Cookie cookie_student_ID = new Cookie("cookie_student_ID", "");
		cookie_student_ID.setMaxAge(0);
		// 创建存储密码的cookie
		Cookie cookie_password = new Cookie("cookie_password", "");
		cookie_password.setMaxAge(0);

		response.addCookie(cookie_student_ID);
		response.addCookie(cookie_password);

		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}