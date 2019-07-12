package bzz.it.uno.network;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Interface between MQTT Client and MQTT Broker
 * 
 * @author Athavan Theivakulasingham
 *
 */
public interface GameActionListener {

	/**
	 * Call if client gets a new subscribed message
	 * 
	 * @param mqttMessage
	 */
	void messageReceived(MqttMessage mqttMessage);
}
