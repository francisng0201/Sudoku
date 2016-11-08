import static org.junit.Assert.*;

import org.junit.Test;

public class DatabaseTest {

	/**
	 * Test insert and select functions
	 */
	@Test
	public void testInsert() {
		String username = "20";
		String password = "1234567";
		Database.writeDatabase(username, password);
		Database.printAll();
	}

	/**
	 * Test update function
	 */
	@Test
	public void testUpdate(){
		Database.updateDatabase(10, 10, 17);
		Database.printOne("17");
		Database.updateDatabase(10, 20, 18);
		Database.printOne("18");
		Database.updateDatabase(4, 3, 19);
		Database.printOne("19");
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
}
