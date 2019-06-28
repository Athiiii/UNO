
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

import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class GameAction implements MqttCallback {

	public void publish(String message, String topic) {
		String clientId = "sslTestClient" + ThreadLocalRandom.current().nextInt(0, 5);
		MqttClient client = null;
		try {
			client = new MqttClient("tcp://104.207.133.76:1883", clientId, new MemoryPersistence());
			System.out.println("connecting...");
			client.connect();
			client.publish("UNO/" + topic, message.getBytes(), 2, true);
			client.disconnect();
			System.out.println("finished");
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public void subscribe(String topic) {
		String clientId = "sslTestClient" + ThreadLocalRandom.current().nextInt(0, 5);
		MqttClient client = null;
		try {
			client = new MqttClient("tcp://104.207.133.76:1883", clientId, new MemoryPersistence());

			client.setCallback(this);

			client.connect();
			client.subscribe("UNO/" + topic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable arg0) {
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
	}

	@Override
	public void messageArrived(String arg0, MqttMessage mqttMessage) throws Exception {
		System.out.println(new String(mqttMessage.getPayload()));
	}
}
