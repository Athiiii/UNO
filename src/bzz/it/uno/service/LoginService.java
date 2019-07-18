package bzz.it.uno.service;

import bzz.it.uno.dao.UserDao;
import bzz.it.uno.model.User;

/**
 * Service for Login
 * 
 * @author Severin Hersche
 *
 */
public class LoginService {

	/**
	 * Tries to login a user.
	 * 
	 * @param username from user
	 * @param password from user
	 * @return the user or returns null if the password isn't correct or the user is
	 *         not in the database.
	 */
	public static User login(String username, String password) {
		User currentUser = UserDao.getInstance().selectByUsername(username);
		// validation if password is correct and if user exists
		if (currentUser != null && currentUser.getPassword().equals(password)) {
			return currentUser;
		}
		return null;
	}
}
