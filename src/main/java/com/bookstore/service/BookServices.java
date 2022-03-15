package com.bookstore.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.Category;

public class BookServices {

	private EntityManager entityManager;
	private BookDAO bookDAO;
	private CategoryDAO categoryDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public BookServices(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		super();
		this.entityManager = entityManager;
		this.request = request;
		this.response = response;
		bookDAO = new BookDAO(entityManager);
		categoryDAO = new CategoryDAO(entityManager);
	}

	public void listBooks(String message) throws ServletException, IOException {
		List<Book> listBooks = bookDAO.listAll();
		request.setAttribute("listBooks", listBooks);

		if (message != null) {
			request.setAttribute("message", message);
		}

		String listPage = "book_list.jsp";
		request.getRequestDispatcher(listPage).forward(request, response);
	}

	public void listBooks() throws ServletException, IOException {
		this.listBooks(null);
	}

	public void showBookNewForm() throws ServletException, IOException {
		List<Category> listCategory = categoryDAO.listAll();
		request.setAttribute("listCategory", listCategory);

		String newPage = "book_form.jsp";
		request.getRequestDispatcher(newPage).forward(request, response);
	}

	public void createBook() throws ServletException, IOException {
		String title = request.getParameter("title");
		Book existBook = bookDAO.findByTitle(title);
		if (existBook != null) {
			String message = "Could not create new book because the title '" + title + "' already exists.";
			listBooks(message);
		}

		Book newBook = new Book();
		readBookFields(newBook);
		Book createdBook = bookDAO.create(newBook);

		if (createdBook.getBookId() > 0) {
			String message = "A new book has been created successfully.";
			listBooks(message);
		}
	}

	public void readBookFields(Book book) throws ServletException, IOException {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String description = request.getParameter("description");
		String isbn = request.getParameter("isbn");
		float price = Float.parseFloat(request.getParameter("price"));

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = null;

		try {
			publishDate = dateFormat.parse(request.getParameter("publishDate"));
		} catch (ParseException ex) {
			ex.printStackTrace();
			throw new ServletException("Error parsing publish date (format is MM/dd/yyyy)");
		}

		book.setTitle(title);
		book.setAuthor(author);
		book.setDescription(description);
		book.setIsbn(isbn);
		book.setPublishDate(publishDate);

		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		Category category = categoryDAO.get(categoryId);
		book.setCategory(category);

		book.setPrice(price);

		Part part = request.getPart("bookImage");

		if (part != null && part.getSize() > 0) {
			long size = part.getSize();
			byte[] imageBytes = new byte[(int) size];

			InputStream inputStream = part.getInputStream();
			inputStream.read(imageBytes);
			inputStream.close();

			book.setImage(imageBytes);
		}

	}

	public void editBook() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);

		String destinatePage = "book_form.jsp";
		if (book != null) {
			request.setAttribute("book", book);

			List<Category> listCategory = categoryDAO.listAll();
			request.setAttribute("listCategory", listCategory);
		} else {
			String message = "Could not find book with ID " + bookId;
			request.setAttribute("message", message);
			destinatePage = "message.jsp";
		}
		request.getRequestDispatcher(destinatePage).forward(request, response);
	}

	public void updateBook() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		Book existBook = bookDAO.get(bookId);
		if (existBook == null) {
			String message = "Could not find book with ID " + bookId;
			listBooks(message);
			return;
		}

		String title = request.getParameter("title");
		Book bookByTitle = bookDAO.findByTitle(title);

		if (bookByTitle != null && !existBook.equals(bookByTitle)) {
			String message = "Could not update book because there's another book having same title.";
			listBooks(message);
			return;
		}

		readBookFields(existBook);

		bookDAO.update(existBook);

		String message = "The book has been updated successfully.";
		listBooks(message);
	}

	public void deleteBook() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);

		String message = null;
		if (book != null) {
			bookDAO.delete(bookId);
			message = "The book has been deleted successfully.";
		} else {
			message = "Could not find book with ID " + bookId + " or it might have been deleted";
		}
		listBooks(message);
	}

	public void listBooksBycategory() throws ServletException, IOException {
		Integer categoryId = Integer.parseInt(request.getParameter("id"));
		List<Book> listBooks = bookDAO.listByCategory(categoryId);
		Category category = categoryDAO.get(categoryId);

		if (category == null) {
			String message = "Sorry, the category ID " + categoryId + " is not available.";
			request.setAttribute("message", message);
			request.getRequestDispatcher("frontend/message.jsp").forward(request, response);

			return;
		}

		List<Category> listCategory = categoryDAO.listAll();

		request.setAttribute("listCategory", listCategory);
		request.setAttribute("category", category);
		request.setAttribute("listBooks", listBooks);

		String listPage = "frontend/books_list_by_category.jsp";
		request.getRequestDispatcher(listPage).forward(request, response);
	}

	public void viewBookDetail() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);

		String destPage = "frontend/book_detail.jsp";

		if (book != null) {
			List<Category> listCategory = categoryDAO.listAll();

			request.setAttribute("book", book);
			request.setAttribute("listCategory", listCategory);
		} else {
			destPage = "frontend/message.jsp";
			String message = "Sorry, the book with ID " + bookId + " is not available.";
			request.setAttribute("message", message);
		}

		request.getRequestDispatcher(destPage).forward(request, response);
	}

	public void search() throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		List<Book> result = null;
		
		if (keyword.equals("")) {
			result = bookDAO.listAll();
		} else {
			result = bookDAO.search(keyword);
		}
		
		request.setAttribute("result", result);
		request.setAttribute("keyword", keyword);
		
		String resultPage = "frontend/search_result.jsp";
		request.getRequestDispatcher(resultPage).forward(request, response);
	}

}
