
package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.Lobby;
import bzz.it.uno.model.User;
import bzz.it.uno.network.GameAction;
import bzz.it.uno.network.GameActionListener;

/**
 * Wait for other users. Used for online version
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class LobbyWaitController extends JFrame implements GameActionListener {
	private static final long serialVersionUID = 1L;
	private int xy, xx;
	private JPanel contentPane;
	private GameAction mqttController;
	private int players;
	private JLabel playerLabel;

	/**
	 * Wait for the other online players
	 * 
	 * @param user
	 * @param navigationFrame
	 * @param lobby
	 * @param maxPlayer
	 */
	public LobbyWaitController(User user, NavigationController navigationFrame, Lobby lobby, int maxPlayer) {
		this.players = 1;
		this.mqttController = new GameAction(this);

		// subscribe to game (mqqt)
		mqttController.subscribe(Integer.toString(lobby.getId()));

		contentPane = new JPanel();
		ViewSettings.setupFrame(this);
		ViewSettings.setupPanel(contentPane);

		setBounds(100, 100, 700, 385);

		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				LobbyWaitController.this.setLocation(x - xx, y - xy);
			}
		});
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});
		setContentPane(contentPane);

		contentPane.add(ViewSettings.createCloseButton(ViewSettings.WHITE));
		contentPane.add(ViewSettings.createReturnButton(this, navigationFrame));
		contentPane.setLayout(null);

		JLabel titleLabel = new JLabel("Warterraum");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(190, 30, 325, 59);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);

		JLabel maxPlayerLabel = new JLabel("/ " + maxPlayer);
		maxPlayerLabel.setForeground(Color.WHITE);
		maxPlayerLabel.setBounds(360, 147, 46, 42);
		maxPlayerLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 35));
		contentPane.add(maxPlayerLabel);

		JLabel playerLabel = new JLabel(Integer.toString(players));
		playerLabel.setForeground(Color.WHITE);
		playerLabel.setBounds(330, 147, 46, 42);
		playerLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 35));
		contentPane.add(playerLabel);
	}

	@Override
	public void messageReceived(MqttMessage mqttMessage) {
		String message = new String(mqttMessage.getPayload());
		System.out.println(message);
		++players;
		playerLabel.setText(Integer.toString(players));
	}
}
