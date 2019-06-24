package bzz.it.uno.dao;

import java.util.List;

import javax.persistence.EntityManager;

import bzz.it.uno.model.Lobby;
import bzz.it.uno.model.User;

public class LobbyDao {
	public List<Lobby> getAllLobbys(){
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		List<Lobby> lobbys = entityManager.createQuery("from Lobby").getResultList();
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
		return lobbys;
		
	}
}
