package bzz.it.uno.service;

import bzz.it.uno.dao.UserDao;
import bzz.it.uno.model.User;

public class RegisterService {
	
	/**
	 * Trys to create a new User if the username isn't used already.
	 * 
	 * @param password
	 * @param username
	 * @return true if username is unique
	 */
	public static boolean createUser(String password, String username) {

		UserDao dao = UserDao.getInstance();
		User user = dao.selectByUsername(username);
		if (user == null) {
			user = new User();
			user.setComputer(false);
			user.setPassword(password);
			user.setUsername(username);
			dao.addUser(user);
			return true;
		}
		return false;
	}
}
