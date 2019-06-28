package bzz.it.uno.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
	
	public User selectByUsername(String username) {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("from User where username=:user");
		query.setParameter("user", username);
		List<User> users = query.getResultList();
		User user = null;
		if(users.size() > 0)
			user = users.get(0);
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
		return user;
	}
}
