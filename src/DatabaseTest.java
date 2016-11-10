import static org.junit.Assert.*;

import org.junit.Test;

public class DatabaseTest {

	/**
	 * Test insert and select functions
	 */
	@Test
	public void testInsert() {
		String username = "300000";
		String password = "1234567";
		boolean check = Database.writeDatabase(username, password);
		assertFalse("username exists", check);
	}

	/**
	 * Test update function
	 */
	@Test
	public void testUpdate(){
		Database.updateDatabase(10, 7, "Francis");
		Database.printOne("Francis");
		Database.updateDatabase(10, 20, "a");
		Database.printOne("a");
		Database.updateDatabase(4, 3, "20");
		Database.printOne("20");
	}
	
	/**
	 * Test delete function
	 */
	@Test
	public void testDelete(){
		Database.delete("17");
		Database.delete("18");
		Database.delete("19");
		Database.printAll();
	}
	
	/**
	 * Test finduser function
	 */
	@Test
	public void testFindUser(){
		User user = null;
		user = Database.findUser("???", "1234567");
		assertEquals("user should be null", null, user);
		user = Database.findUser("Francis", "lmao");
		assertEquals("user should be null", null, user);
		user = Database.findUser("Francis", "1234567");
		assertEquals("username incorrect!", "Francis", user.getName());
		assertEquals("password incorrect!", "1234567", user.getPassword());
		assertEquals("score incorrect!", 10, user.getScore());
		assertEquals("number of hints incorrect!", 7, user.getHints());
	}
}
