package bzz.it.uno.test;

import bzz.it.uno.dao.HandleConnectionToDB;

public class TestHelper {
	public static void run() {
		try {
			HandleConnectionToDB.openDbFactory();
			Runtime.getRuntime().addShutdownHook(new Thread() {
				// call before application will be closed
				@Override
				public void run() {
					HandleConnectionToDB.closeDbFactory();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			HandleConnectionToDB.closeDbFactory();
		}
	}
}
