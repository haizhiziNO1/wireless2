package com.hj.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hj.domain.User;
import com.hj.service.UserService;

public class AutoLoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		// 获得cookie中用户名和密码 进行登录的操作

		String cookie_student_ID = null;

		String cookie_password = null;
		// 获得cookie
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {

				if ("cookie_student_ID ".equals(cookie.getName())) {
					cookie_student_ID = cookie.getValue();

				}
				if ("cookie_password".equals(cookie.getName())) {
					cookie_password = cookie.getValue();
				}
			}
		}

		if (cookie_student_ID != null && cookie_password != null) {
			// 登录的代码
			UserService service = new UserService();
			User user = null;

			user = service.userlogin(cookie_student_ID, cookie_password);

			// 将登录的用户的user对象存到session中
			session.setAttribute("user", user);
		}

		// 放行
		chain.doFilter(req, resp);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

}
