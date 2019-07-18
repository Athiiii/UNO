package bzz.it.uno.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bzz.it.uno.model.User;

/**
 * Handling CRUD operations for table "User"
 * 
 * @author Athavan Theivakulasingham, Severin Hersche
 */
public class UserDao {
	private static UserDao userDao;

	private UserDao() {

	}

	public static UserDao getInstance() {
		if (userDao == null) {
			userDao = new UserDao();
		}
		return userDao;
	}

	/**
	 * Get the whole data from the table
	 * 
	 * @return list of users
	 */
	public List<User> getAllUsers() {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<User> users = entityManager.createQuery("from User").getResultList();
		if (users.size() > 0) {
			users.get(0).getFriendList().size();
			users.get(0).getUserLobby().size();
			users.get(0).getFriendList().size();
		}
		entityManager.getTransaction().commit();

		HandleConnectionToDB.closeEntityManager();
		return users;

	}

	/**
	 * Add new user
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
	}
	
	public void removeUser(User user) {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		User u = entityManager.find(User.class, user.getId());
		entityManager.getTransaction().begin();
		entityManager.remove(u);
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
	}

	/**
	 * update a specific user
	 * 
	 * @param user
	 */
	public void updateUser(User user) {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		user = entityManager.merge(user);
		entityManager.persist(user);
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
	}

	/**
	 * update a specific user by pk (id)
	 * 
	 * @param id
	 * @param user
	 */
	public void updateUser(int id, User user) {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		User userFind = entityManager.find(User.class, id);

		entityManager.getTransaction().begin();
		userFind.setPicture(user.getPicture());
		userFind.setPassword(user.getPassword());
		userFind.setUsername(user.getUsername());
		entityManager.getTransaction().commit();
	}

	/**
	 * select user by username
	 * 
	 * @param username
	 * @return user
	 */
	public User selectByUsername(String username) {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("from User where username=:user");
		query.setParameter("user", username);
		@SuppressWarnings("unchecked")
		List<User> users = query.getResultList();
		User user = null;
		if (users.size() > 0) {
			user = users.get(0);
			user.getFriendList().size();
			user.getUserLobby().size();
			user.getFriendList().size();
		}
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
		return user;
	}
}
