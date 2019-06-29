package bzz.it.uno.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bzz.it.uno.model.User;
import bzz.it.uno.model.User_Lobby;

public class UserLobbyDao {
	public List<User_Lobby> selectByUser(int userId) {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("from User_Lobby where user_id=:user");
		query.setParameter("user", userId);
		List<User_Lobby> userLobbys = query.getResultList();
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
		return userLobbys;
	}
	
	public List<User_Lobby> getAllUserLobbies() {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		List<User_Lobby> userLobbies = entityManager.createQuery("from User_Lobby").getResultList();
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
		return userLobbies;
	}
}
