package bzz.it.uno.service;

import bzz.it.uno.dao.UserDao;
import bzz.it.uno.model.User;

public class RegisterService {
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
