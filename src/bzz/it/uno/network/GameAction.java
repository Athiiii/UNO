package bzz.it.uno.network;

import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Maked MQTT Calls for online version of the UNO <br>
 * Functions are:
 * <li>public</li>
 * <li>subscribe</li>
 * <li>unsubscribe</li> <br>
 * MQTT is a lightweight data transfer protocol
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class GameAction implements MqttCallback {	

	GameActionListener onlineGameController;

	public GameAction(GameActionListener onlineGameController) {
		this.onlineGameController = onlineGameController;
	}

	/**
	 * public message to a specific topic
	 * 
	 * @param message
	 * @param topic
	 */
	public void publish(String message, String topic) {
		String clientId = "sslTestClient" + ThreadLocalRandom.current().nextInt(0, 5);
		MqttClient client = null;
		try {
			client = new MqttClient("tcp://104.207.133.76:1883", clientId, new MemoryPersistence());
			client.connect();
			client.publish("UNO/" + topic, message.getBytes(), 2, true);
			client.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * subscribe to a specific topic <br>
	 * predefined prefix is: <b>UNO/</b> <br>
	 * That means that only messaged can be send over the UNO Topic
	 * 
	 * @param topic
	 */
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
	
	/**
	 * Unsubscribe a specific topic <br>
	 * predefined prefix is: <b>UNO/</b>
	 * 
	 * @param topic
	 * @param clientId
	 */
	public void unsubscribe(String topic, String clientId) {
		MqttClient client = null;
		try {
			client = new MqttClient("tcp://104.207.133.76:1883", clientId, new MemoryPersistence());

			client.setCallback(this);

			client.connect();
			client.unsubscribe("UNO/" + topic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable arg) {
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg) {
	}

	@Override
	public void messageArrived(String arg, MqttMessage mqttMessage) throws Exception {
		onlineGameController.messageReceived(mqttMessage);
	}
}
