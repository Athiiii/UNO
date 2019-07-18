package bzz.it.uno.controller;

import java.awt.EventQueue;

import bzz.it.uno.dao.HandleConnectionToDB;

/**
 * Start application
 * 
 * @author Severin Hersche
 */
public class StartApp {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HandleConnectionToDB.openDbFactory();
					
					//show login as starting page
					new LoginController();
					
					Runtime.getRuntime().addShutdownHook(new Thread() {
						// call before application will be closed
						@Override
						public void run() {
							HandleConnectionToDB.closeDbFactory();
						}
					});
				} catch (Exception e) {
					HandleConnectionToDB.closeDbFactory();
				}
			}
		});
	}
}
