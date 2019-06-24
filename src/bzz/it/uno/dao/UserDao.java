package bzz.it.uno.dao;

import java.util.List;

import javax.persistence.EntityManager;

import bzz.it.uno.model.User;

public class UserDao {
	public List<User> getAllUsers(){
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		List<User> users = entityManager.createQuery("from User").getResultList();
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
		return users;
		
	}
	
	public void addUser(User user) {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
	}
}
