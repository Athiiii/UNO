package bzz.it.uno.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HandleConnectionToDB {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;

	public static EntityManager getEntityManager() {
		entityManagerFactory = Persistence.createEntityManagerFactory("uno.jpa");
		entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

	public static void closeEntityManager() {
		entityManagerFactory.close();
		entityManager.close();
	}

}
