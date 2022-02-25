package com.bookstore.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.UserDAO;
import com.bookstore.entity.Users;

public class UserServices {

	private EntityManager entityManager;
	private UserDAO userDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public UserServices(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.entityManager = entityManager;
		userDAO = new UserDAO(entityManager);
	}

	public void listUser(String message) throws ServletException, IOException {
		List<Users> listUsers = userDAO.listAll();

		request.setAttribute("listUsers", listUsers);

		if (message != null) {
			request.setAttribute("message", message);
		}

		String listPage = "user_list.jsp";
		request.getRequestDispatcher(listPage).forward(request, response);
	}

	public void listUser() throws ServletException, IOException {
		listUser(null);
	}

	public void createUser() throws ServletException, IOException {
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullName");
		String password = request.getParameter("password");

		Users existUser = userDAO.findByEmail(email);
		if (existUser != null) {
			String message = "Could not create user. A user with email " + email + " already exists";
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
		} else {
			Users newUser = new Users(email, fullName, password);
			userDAO.create(newUser);
			listUser("New user created successfully");
		}
	}

	public void editUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));
		Users user = userDAO.get(userId);

		String destinatePage = "user_form.jsp";
		if (user != null) {
			request.setAttribute("user", user);
		} else {
			String message = "Could not find user with ID " + userId;
			request.setAttribute("message", message);
			destinatePage = "message.jsp";
		}
		request.getRequestDispatcher(destinatePage).forward(request, response);
	}

	public void updateUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullName");
		String password = request.getParameter("password");

		Users userById = userDAO.get(userId);

		Users userByEmail = userDAO.findByEmail(email);

		if (userById == null) {
			String message = "Could not find user with ID " + userId;
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
		} else if ((userByEmail != null) && (userByEmail.getUserId() != userById.getUserId())) {
			String message = "Could not update user. \nAn User with email " + email + " already exists!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
		} else {
			Users user = new Users(userId, email, fullName, password);
			userDAO.update(user);

			String message = "An User has been updated successfully";
			listUser(message);
		}
	}

	public void deleteUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));
		Users user = userDAO.get(userId);

		String message = "User has been deleted successfully";
		if (user == null) {
			message = "Could not delete user with ID " + userId + ", or it might have been deleted by another admin";
		} else if (userId == 1) {
			message = "The default admin user account cannot be deleted.";
		} else {
			userDAO.delete(userId);
		}
		listUser(message);
	}

	public void login() throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		boolean loginResult = userDAO.checkLogin(email, password);

		if (loginResult) {
			request.getSession().setAttribute("useremail", email);

			request.getRequestDispatcher("/admin/").forward(request, response);
		} else {
			String message = "Login failed!";
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}

	}
}
