package bzz.it.uno.network;

import java.util.UUID;

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
	private MqttClient client;
	private GameActionListener onlineGameController;

	// client Id for mqtt verification
	private String clientId;

	// Our personal server
	private final String SERVER_IP = "104.207.133.76";

	public GameAction(GameActionListener onlineGameController) {
		this.onlineGameController = onlineGameController;
		clientId = UUID.randomUUID().toString();
	}

	/**
	 * public message to a specific topic
	 * 
	 * @param message
	 * @param topic
	 */
	public void publish(String message, String topic) {
		try {
			if (client == null || !client.isConnected()) {
				openClientConnection();
			}
			client.publish("UNO/" + topic, message.getBytes(), 2, true);
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
		try {
			if (client == null || !client.isConnected()) {
				openClientConnection();
			}
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
	public void unsubscribe(String topic) {
		try {
			if (client == null || !client.isConnected()) {
				openClientConnection();
			}
			client.unsubscribe("UNO/" + topic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open connection of client Normally will be done by initialize
	 */
	public void openClientConnection() {
		try {
			if (client == null || !client.isConnected()) {
				client = new MqttClient("tcp://" + SERVER_IP + ":1883", clientId, new MemoryPersistence());
				client.setCallback(GameAction.this);
				client.connect();
			}
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable arg) {
		System.out.println("lost");
		System.out.println(arg.toString());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg) {
	}

	@Override
	public void messageArrived(String arg, MqttMessage mqttMessage) throws Exception {
		// forward message to controller
		onlineGameController.messageReceived(mqttMessage);
	}
}
