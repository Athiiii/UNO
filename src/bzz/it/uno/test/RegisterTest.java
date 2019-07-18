package bzz.it.uno.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import bzz.it.uno.dao.UserDao;
import bzz.it.uno.model.User;
/**
 * Test for registration
 * 
 * @author Severin Hersche
 *
 */
public class RegisterTest {

	@Test
	public void testT9() {
		TestHelper.run();
		String username = "testDATA";
		String password = "123";
		User user = new User();
		UserDao instance = UserDao.getInstance();
		user.setPassword(password);
		user.setUsername(username);
		user.setComputer(false);
		instance.addUser(user);
		List<User> allUsers = instance.getAllUsers();
		boolean testTrue = false;
		for (User user2 : allUsers) {
			if (user2.getUsername().equals(username)) {
				testTrue = true;

			}
		}
		if (testTrue) {
			instance.removeUser(user);
		}
		assertTrue(testTrue);

	}

}
