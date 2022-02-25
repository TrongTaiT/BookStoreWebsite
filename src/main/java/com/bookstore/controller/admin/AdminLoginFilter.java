package com.bookstore.controller.admin;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookstore.filter.HttpFilter;

@WebFilter("/admin/*")
public class AdminLoginFilter implements HttpFilter {

	public AdminLoginFilter() {
		super();
	}

	public void destroy() {
	}

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = request.getSession(false);

		String loginURI = request.getContextPath() + "/admin/login";
		boolean loginRequest = request.getRequestURI().equals(loginURI);

		boolean loggedIn = session != null//
				&& session.getAttribute("useremail") != null;

		boolean loginPage = request.getRequestURI().endsWith("login.jsp");

		// if user has logged in and try to go to login page again
		if (loggedIn && (loginRequest || loginPage)) {
			request.getRequestDispatcher("/admin/").forward(request, response);
		} else if (loggedIn || loginRequest) {
			chain.doFilter(request, response);
		} else {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
