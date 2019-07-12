package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bzz.it.uno.dao.UserDao;
import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.User;

/**
 * Search for User to be friends
 * 
 * @author Severin Hersche
 *
 */
public class PlayerSearchController extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int xx, xy;

	public PlayerSearchController(User user, FriendsController friendsController) {

		contentPane = new JPanel();

		ViewSettings.setupFrame(this);
		ViewSettings.setupPanel(contentPane);

		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				PlayerSearchController.this.setLocation(x - xx, y - xy);
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
		contentPane.add(ViewSettings.createReturnButton(this, friendsController));
		List<User> allUsers = UserDao.getInstance().getAllUsers();
		JLabel titleLabel = new JLabel("Spieler suchen");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(10, 54, 680, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);

		JTextField jTextField = new JTextField();
		jTextField.setBounds(110, 150, 350, 34);
		contentPane.add(jTextField);

		JButton search = ViewSettings.createButton(480, 150, 100, 34, Color.GRAY, "Suche");
		contentPane.add(search);
		friendsController.dispose();
	}

}
