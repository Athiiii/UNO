package bzz.it.uno.controller;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import bzz.it.uno.model.Lobby;
import bzz.it.uno.model.User;
import bzz.it.uno.network.GameActionListener;

public class OnlineGameController implements GameActionListener {

	public OnlineGameController(User user, NavigationController navigationController, Lobby lobby) {

	}

	public void messageReceived(MqttMessage mqttMessage) {
		System.out.println(mqttMessage.getPayload());
	}

}
