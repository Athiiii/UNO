package bzz.it.uno.dao;

import java.util.List;

import javax.persistence.EntityManager;

import bzz.it.uno.model.Lobby;

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
	
	public List<Lobby> getAllLobbys() {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		List<Lobby> lobbys = entityManager.createQuery("from Lobby").getResultList();
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
		return lobbys;

	}
}
