package com.bookstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Book;
import com.bookstore.entity.Category;

public class BookDAOTest extends BaseDAOTest {

	private static BookDAO bookDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseDAOTest.setUpBeforeClass();
		bookDAO = new BookDAO(entityManager);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BaseDAOTest.tearDownAfterClass();
	}

	@Test
	public void testCreateBook() throws ParseException, IOException {
		Book book = new Book();

		Category category = new CategoryDAO(entityManager).get(11);
		book.setCategory(category);

		book.setTitle("Effective Java (2nd Edition)");
		book.setAuthor("Joshua Bloch");
		book.setDescription(
				"Are you looking for a deeper understanding of the Java™ programming language so that you can write code that is clearer, more correct, more robust, and more reusable? Look no further! Effective Java™, Second Edition, brings together seventy-eight indispensable programmer’s rules of thumb: working, best-practice solutions for the programming challenges you encounter every day.\r\n"
						+ " \r\n"
						+ "This highly anticipated new edition of the classic, Jolt Award-winning work has been thoroughly updated to cover Java SE 5 and Java SE 6 features introduced since the first edition. Bloch explores new design patterns and language idioms, showing you how to make the most of features ranging from generics to enums, annotations to autoboxing.\r\n"
						+ " \r\n"
						+ "Each chapter in the book consists of several “items” presented in the form of a short, standalone essay that provides specific advice, insight into Java platform subtleties, and outstanding code examples. The comprehensive descriptions and explanations for each item illuminate what to do, what not to do, and why.");
		book.setPrice(38.87f);
		book.setIsbn("0321356683");

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = dateFormat.parse("05/28/2008");
		book.setPublishDate(publishDate);

		String imagePath = "D:\\Udemy\\Java Servlet, JSP and Hibernate Build eCommerce Website\\66. Implement create() method\\books\\Effective Java.jpg";
		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		book.setImage(imageBytes);

		Book createdBook = bookDAO.create(book);

		assertTrue(createdBook.getBookId() > 0);
	}
	
	@Test
	public void testCreate2ndBook() throws ParseException, IOException {
		Book book = new Book();

		Category category = new CategoryDAO(entityManager).get(11);
		book.setCategory(category);

		book.setTitle("Java 8 in Action");
		book.setAuthor("Alan Mycroft");
		book.setDescription("Java 8 in Action is a clearly written guide to the new features of Java 8.");
		book.setPrice(36.72f);
		book.setIsbn("1617291994");

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = dateFormat.parse("08/28/2014");
		book.setPublishDate(publishDate);

		String imagePath = "D:\\Udemy\\Java Servlet, JSP and Hibernate Build eCommerce Website\\66. Implement create() method\\books\\Java 8 in Action.jpg";
		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		book.setImage(imageBytes);

		Book createdBook = bookDAO.create(book);

		assertTrue(createdBook.getBookId() > 0);
	}

	@Test
	public void testUpdateBook() throws ParseException, IOException {
		Book existBook = new Book();
		
		existBook.setBookId(32);

		Category category = new Category("Java");
		category.setCategoryId(15);
		existBook.setCategory(category);

		existBook.setTitle("Effective Java (3rd Edition)");
		existBook.setAuthor("Joshua Bloch");
		existBook.setDescription("New coverage of generics, enums, annotations, autoboxing");
		existBook.setPrice(40f);
		existBook.setIsbn("0321356683");

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = dateFormat.parse("05/28/2008");
		existBook.setPublishDate(publishDate);

		String imagePath = "D:\\Udemy\\Java Servlet, JSP and Hibernate Build eCommerce Website\\66. Implement create() method\\books\\Effective Java.jpg";

		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		existBook.setImage(imageBytes);

		Book updatedBook = bookDAO.update(existBook);

		assertEquals(updatedBook.getTitle(), "Effective Java (3rd Edition)");
	}
	
	@Test
	public void testGetBookFail() {
		Integer bookId = 134;
		Book book = bookDAO.get(bookId);
		
		assertNull(book);
	}
	
	@Test
	public void testGetBookSuccess() {
		Integer bookId = 33;
		Book book = bookDAO.get(bookId);
		
		assertNotNull(book);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testDeleteBookFail() {
		Integer bookId = 100;
		bookDAO.delete(bookId);
	}
	
	@Test
	public void testDeleteBookSuccess() {
		Integer bookId = 32;
		bookDAO.delete(bookId);
		
		assertTrue(true);
	}
	
	@Test
	public void testListAll() {
		List<Book> list = bookDAO.listAll();
		
		for (Book aBook: list) {
			System.out.println(aBook.getTitle() + " - " + aBook.getAuthor());
		}
		assertFalse(list.isEmpty());
	}
	
	@Test
	public void testFindByTitleNotExist() {
		String title = "Thinkin in Java";
		Book book = bookDAO.findByTitle(title);
		
		assertNull(book);
	}
	
	@Test
	public void testFindByTitle() {
		String title = "Effective Java (2nd Edition)";
		Book book = bookDAO.findByTitle(title);
		
		assertNotNull(book);
	}
	
	@Test
	public void testCount() {
		int totalBooks = (int) bookDAO.count();
		
		assertTrue(totalBooks == 2);
	}
	
	@Test
	public void testListByCategory() {
		int categgoryId = 11;
		
		List<Book> listByCategory = bookDAO.listByCategory(categgoryId);
		
		for (Book aBook : listByCategory) {
			System.out.println(aBook.getTitle());
		}
		
		assertTrue(listByCategory.size() > 0);
	}

}
