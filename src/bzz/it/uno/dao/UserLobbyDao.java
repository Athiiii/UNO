package bzz.it.uno.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bzz.it.uno.model.User_Lobby;

/**
 * Handling CRUD operations for table "User_Lobby"
 * 
 * @author Severin Hersche, Athavan Theivakulasingham
 */
public class UserLobbyDao {
	private static UserLobbyDao userLobbyDao;

	private UserLobbyDao() {

	}

	public static UserLobbyDao getInstance() {
		if (userLobbyDao == null) {
			userLobbyDao = new UserLobbyDao();
		}
		return userLobbyDao;
	}

	/**
	 * select specific user_lobby by userid
	 * 
	 * @param userId
	 * @return list of user_lobby
	 */
	public List<User_Lobby> selectByUser(int userId) {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("from User_Lobby where user_id=:user");
		query.setParameter("user", userId);
		@SuppressWarnings("unchecked")
		List<User_Lobby> userLobbys = query.getResultList();
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
		return userLobbys;
	}

	/**
	 * get whole data of the table
	 * 
	 * @return list of user_lobby
	 */
	public List<User_Lobby> getAllUserLobbies() {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<User_Lobby> userLobbies = entityManager.createQuery("from User_Lobby").getResultList();
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
		return userLobbies;
	}

	/**
	 * add new user_lobby
	 * 
	 * @param userLobby
	 */
	public void addUserLobby(User_Lobby userLobby) {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(userLobby);
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
	}

	/**
	 * update the points of a specific user_lobby
	 * 
	 * @param points
	 * @param user_Lobby_id
	 */
	public void updatePointsUserLobby(int points, int user_Lobby_id) {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		User_Lobby foundLobby = entityManager.find(User_Lobby.class, user_Lobby_id);
		foundLobby.setPoints(points);
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
	}

}
