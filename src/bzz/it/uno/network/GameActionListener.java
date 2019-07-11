package bzz.it.uno.network;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface GameActionListener {
	void messageReceived(MqttMessage mqttMessage);
}
