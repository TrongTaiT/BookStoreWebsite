package com.bookstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Users;

public class UserDAOTest extends BaseDAOTest {

	private static UserDAO userDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseDAOTest.setUpBeforeClass();
		userDAO = new UserDAO(entityManager);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BaseDAOTest.tearDownAfterClass();
	}

	@Test
	public void testCreateUsers() {
		Users user1 = new Users();
		user1.setEmail("nguyentrongtai.ait@gmail.com");
		user1.setFullName("Nguyễn Trọng Tài");
		user1.setPassword("trongtai");

		userDAO.create(user1);

		assertTrue(user1.getUserId() > 0);
	}

	@Test(expected = PersistenceException.class)
	public void testCreateUsersFieldsNotSet() {
		Users user1 = new Users();

		userDAO.create(user1);

	}

	@Test
	public void testUpdateUsers() {
		Users user = new Users();
		user.setUserId(19);
		user.setEmail("nam@codejava.net");
		user.setFullName("Nam Ha Minh");
		user.setPassword("mysecret");

		user = userDAO.update(user);

		String expected = "mysecret";
		String actual = user.getPassword();

		assertEquals(expected, actual);
	}

	@Test
	public void testGetUsers() {
		Integer userId = 20;
		Users user = userDAO.get(userId);

		assertNotNull(user);
	}

	@Test
	public void testGetUsersNotFound() {
		Integer userId = 99;
		Users user = userDAO.get(userId);

		assertNull(user);
	}

	@Test
	public void testDeleteUsers() {
		Integer userId = 26;
		userDAO.delete(userId);

		Users user = userDAO.get(userId);

		assertNull(user);
	}

	@Test(expected = EntityNotFoundException.class)
	public void testDeleteNonExistUsers() {
		Integer userId = 99;
		userDAO.delete(userId);

		Users user = userDAO.get(userId);

		assertNull(user);
	}

	@Test
	public void testListAll() {
		List<Users> listUsers = userDAO.listAll();

		for (Users user : listUsers) {
			System.out.println(user.toString());
		}

		assertTrue(listUsers.size() > 0);
	}

	@Test
	public void testCount() {
		long totalUsers = userDAO.count();

		assertEquals(7, totalUsers);
	}

	@Test
	public void testCheckLoginSuccess() {
		String email = "nguyentrongtai.ait@gmail.com";
		String password = "trongtai";
		boolean loginResult = userDAO.checkLogin(email, password);
		
		assertTrue(loginResult);
	}
	
	@Test
	public void testCheckLoginFail() {
		String email = "nguyentrongtai.ait@gmail.com";
		String password = "trongtaihehe";
		boolean loginResult = userDAO.checkLogin(email, password);
		
		assertFalse(loginResult);
	}

	@Test
	public void testFindByEmail() {
		String email = "johnbetty@gmail.com";
		Users users = userDAO.findByEmail(email);

		assertNotNull(users);
	}

}
