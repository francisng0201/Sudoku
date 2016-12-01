import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void test() {
		User user = new User("abc", "def");
		assertEquals("username incorrect!", user.getName(), "abc");
		assertEquals("password incorrect!", user.getPassword(), "def");
		
		user.setPassword("oh");
		assertEquals("password incorrect!", user.getPassword(), "oh");
		user.setName("abck");
		assertEquals("username incorrect!", user.getName(), "abck");

		
		User user2 = new User("gg", "ff", 2, 3);
		assertEquals("username incorrect!", user2.getName(), "gg");
		assertEquals("password incorrect!", user2.getPassword(), "ff");
		assertEquals("score incorrect!", user2.getScore(), 2);
		assertEquals("hints incorrect!", user2.getHints(), 3);
		
		user2.setHints(30);
		user2.setScore(40);
		assertEquals("score incorrect!", user2.getScore(), 40);
		assertEquals("hints incorrect!", user2.getHints(), 30);
	}

}
