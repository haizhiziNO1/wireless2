package com.hj.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hj.domain.Details;
import com.hj.domain.PageDetails;
import com.hj.domain.User;
import com.hj.service.UserService;

public class UserLogin extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 验证码校验
		// 获得页面输入的验证
		String checkCode_client = request.getParameter("checkCode");

		// 获得生成图片的文字的验证码
		String checkCode_session = (String) request.getSession().getAttribute("checkcode_session");

		// 比对页面的和生成图片的文字的验证码是否一致
		/*
		 * if (!checkCode_session.equals(checkCode_client)) {
		 * request.setAttribute("loginInfo", "您的验证码不正确");
		 * request.getRequestDispatcher("/user/login.jsp").forward(request, response);
		 * return; }
		 */

		HttpSession session = request.getSession();
		String student_ID = request.getParameter("student_ID");
		String password = request.getParameter("password");

		UserService service = new UserService();
		User user = service.userlogin(student_ID, password);
		if (user != null) {
			// 登录成功
			// 判断用户是否勾选自动登录
			String autoLogin = request.getParameter("autoLogin");
			if (autoLogin != null) {

				Cookie cookie_student_ID = new Cookie("cookie_student_ID", student_ID);
				Cookie cookie_password = new Cookie("cookie_password", password);
				// 设置cookie的持久化时间
				cookie_student_ID.setMaxAge(60 * 60);
				cookie_password.setMaxAge(60 * 60);
				// 设置cookie的携带路径
				cookie_student_ID.setPath(request.getContextPath());
				cookie_password.setPath(request.getContextPath());
				// 发送cookie
				response.addCookie(cookie_student_ID);
				response.addCookie(cookie_password);
			}

			// 将登录的用户的user对象存到session中
			session.setAttribute("user", user);

			String currentPageStr = "1";
			int currentPage = Integer.parseInt(currentPageStr);
			// 认为每页显示10条
			int currentCount = 10;

			PageDetails<Details> pagedetails = service.reservedatails(user, currentPage, currentCount);

			session.setAttribute("pagedetails", pagedetails);

			// 重定向到首页
			response.sendRedirect(request.getContextPath());
			// request.getSession().setAttribute("username", user.getUsername());

		} else {
			request.setAttribute("loginInfo", "您的账号或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}