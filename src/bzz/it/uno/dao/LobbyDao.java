package bzz.it.uno.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bzz.it.uno.model.Lobby;

/**
 * Handling CRUD operations for table "Lobby"
 * 
 * @author Severin Hersche, Athavan Theivakulasingham
 */
public class LobbyDao {

	private static LobbyDao lobbyDao;

	private LobbyDao() {

	}

	public static LobbyDao getInstance() {
		if (lobbyDao == null) {
			lobbyDao = new LobbyDao();
		}
		return lobbyDao;
	}

	/**
	 * Get all data from the table
	 * 
	 * @return List of lobbies
	 */
	public List<Lobby> getAllLobbys() {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Lobby> lobbys = entityManager.createQuery("from Lobby").getResultList();
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
		return lobbys;
	}

	/**
	 * Get specific lobby by the lobbyname
	 * 
	 * @param lobbyName
	 * @return lobby
	 */
	public Lobby selectLobbyByName(String lobbyName) {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("from Lobby where name=:name");
		query.setParameter("name", lobbyName);
		@SuppressWarnings("unchecked")
		List<Lobby> lobbies = query.getResultList();
		Lobby lobby = null;
		if (lobbies.size() > 0) {
			lobby = lobbies.get(0);
		}
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
		return lobby;
	}

	/**
	 * Add a lobby
	 * 
	 * @param lobby
	 */
	public void addLobby(Lobby lobby) {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(lobby);
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
	}
}
