package bzz.it.uno.network;

import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class GameAction implements MqttCallback {

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
