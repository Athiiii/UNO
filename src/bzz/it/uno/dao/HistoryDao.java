package bzz.it.uno.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bzz.it.uno.model.History;
import bzz.it.uno.model.User;

public class HistoryDao {
	private static HistoryDao historyDao;
	
	private HistoryDao() {
		
	}
	
	public static HistoryDao getInstance() {
		if (historyDao == null) {
			historyDao = new HistoryDao();
		}
		return historyDao;
	}
	
	public List<History> selectByUser(User user) {
		EntityManager entityManager = HandleConnectionToDB.getEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("from History where user_id=:user");
		query.setParameter("user", user.getId());
		List<History> histories = query.getResultList();
		entityManager.getTransaction().commit();
		HandleConnectionToDB.closeEntityManager();
		return histories;
	}
}
