
package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.Lobby;
import bzz.it.uno.model.User;

/**
 * Wait for other users. Used for online version
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class LobbyWaitController extends JFrame {
	private static final long serialVersionUID = 1L;
	private User user;
	private int xy, xx;
	private NavigationController navigationFrame;
	private JPanel contentPane;
	private Lobby lobby;
	
	public LobbyWaitController(User user, NavigationController navigationFrame, Lobby lobby) {
		this.navigationFrame = navigationFrame;
		this.lobby = lobby;
		this.user = user;

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

		JLabel titleLabel = new JLabel("Warterraum");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(160, 36, 438, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);
	}
}
