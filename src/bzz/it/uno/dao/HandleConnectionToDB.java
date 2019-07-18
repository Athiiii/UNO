package bzz.it.uno.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Manage Entity Manager and Factory
 * 
 * @author Severin Hersche
 *
 */
public class HandleConnectionToDB {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;

	public static EntityManager getEntityManager() {
		entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

	public static void closeEntityManager() {
		entityManager.close();
	}

	/**
	 * Open Factory to the Database UNO. Should be open once for the whole lifetime
	 * of the application
	 */
	public static void openDbFactory() {
		entityManagerFactory = Persistence.createEntityManagerFactory("uno.jpa");
	}

	/**
	 * Closing Factory to Database UNO. Should be closed at least.<
	 */
	public static void closeDbFactory() {
		entityManagerFactory.close();
	}

}
