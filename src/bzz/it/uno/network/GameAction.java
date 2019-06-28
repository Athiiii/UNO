  
/* 
 * Copyright (c) 2009, 2012 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Dave Locke - initial API and implementation and/or initial documentation
 */

package bzz.it.uno.network;

import org.eclipse.paho.client.mqttv3.MqttClient;

public class GameAction {

	public static void main(String[] args) {
		GameAction g = new GameAction();
		try {
			g.publish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void publish() throws Exception {
		MqttClient client = new MqttClient("tcp://104.207.133.76", "monty");
		client.connect();
	}
}
