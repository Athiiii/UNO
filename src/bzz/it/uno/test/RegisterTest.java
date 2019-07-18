package bzz.it.uno.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import bzz.it.uno.dao.HandleConnectionToDB;
import bzz.it.uno.dao.UserDao;
import bzz.it.uno.model.User;

public class RegisterTest {

	@Test
	public void testT9() {
		run();
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
	@Test
	public void testT10() {
		
	}
	public void run() {
		try {
			HandleConnectionToDB.openDbFactory();
			Runtime.getRuntime().addShutdownHook(new Thread() {
				// call before application will be closed
				@Override
				public void run() {
					HandleConnectionToDB.closeDbFactory();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			HandleConnectionToDB.closeDbFactory();
		}
	}

}
