package com.bookstore.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.CategoryDAO;
import com.bookstore.entity.Category;

public class CategoryServices {

	private EntityManager entityManager;
	private CategoryDAO categoryDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public CategoryServices(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.entityManager = entityManager;
		categoryDAO = new CategoryDAO(entityManager);
	}

	public void listCategory(String message) throws ServletException, IOException {
		List<Category> listCategory = categoryDAO.listAll();

		request.setAttribute("listCategory", listCategory);

		if (message != null) {
			request.setAttribute("message", message);
		}

		String listPage = "category_list.jsp";
		request.getRequestDispatcher(listPage).forward(request, response);
	}

	public void listCategory() throws ServletException, IOException {
		listCategory(null);
	}

	public void createCategory() throws ServletException, IOException {
		String name = request.getParameter("name");

		Category existCategory = categoryDAO.findByName(name);

		if (existCategory != null) {
			String message = "Could not create category." //
					+ " A category with name \"" + name + "\" already exists.";

			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
		} else {
			Category newCategory = new Category(name);
			categoryDAO.create(newCategory);

			listCategory("New category created successfully");
		}
	}

	public void editCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("id"));
		Category category = categoryDAO.get(categoryId);

		String destPage = "category_form.jsp";

		if (category == null) {
			destPage = "message.jsp";
			String message = "Could not find category with ID " + categoryId;
			request.setAttribute("message", message);
		} else {
			request.setAttribute("category", category);
		}

		request.getRequestDispatcher(destPage).forward(request, response);
	}

	public void updateCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		String categoryName = request.getParameter("name");

		Category categoryById = categoryDAO.get(categoryId);
		Category categoryByName = categoryDAO.findByName(categoryName);

		if (categoryByName != null && categoryById.getCategoryId() != categoryByName.getCategoryId()) {
			String message = "Could not update category. " //
					+ "A category with name \"" + categoryName + "\" already exists.";
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
		} else {
			categoryById.setName(categoryName);
			categoryDAO.update(categoryById);
			listCategory("Category has been updated successfully");
		}
	}

	public void deleteCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("id"));

		Category category = categoryDAO.get(categoryId);

		String message = "Category has been removed successfully";
		if (category == null) {
			message = "Could not delete category with ID " + categoryId //
					+ ", or it might have been deleted";
		} else {
			categoryDAO.delete(categoryId);
		}
		listCategory(message);
	}

}
