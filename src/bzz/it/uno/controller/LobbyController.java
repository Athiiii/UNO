package bzz.it.uno.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.User;

/**
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class LobbyController extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int xy, xx;

	/**
	 * This is Called, when you start a new Lobby
	 * 
	 * @param user
	 *            or = owner
	 * @param navigationFrame
	 */
	public LobbyController(User user, NavigationController navigationFrame) {
		contentPane = new JPanel();

		ViewSettings.setupPanel(contentPane);
		ViewSettings.setupFrame(this);

		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				LobbyController.this.setLocation(x - xx, y - xy);
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

		contentPane.add(ViewSettings.createCloseButton(ViewSettings.BLACK));
		contentPane.add(ViewSettings.createReturnButton(this, navigationFrame));
	}
}
