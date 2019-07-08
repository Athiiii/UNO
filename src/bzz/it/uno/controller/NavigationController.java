package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.User;

/**
 * Navigate beween the different views:
 * <ul>
 * <li>New Game</li>
 * <li>Ranking</li>
 * <li>Friends</li>
 * <li>Open Lobbies</li>
 * <li>Profile</li>
 * </ul>
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class NavigationController extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int xy, xx;

	public NavigationController(User user, LoginController frame) {
		contentPane = new JPanel();

		ViewSettings.setupFrame(this);
		ViewSettings.setupPanel(contentPane);

		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				NavigationController.this.setLocation(x - xx, y - xy);
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

		//title label
		Label titleLabel = new Label("Welcome");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(233, 16, 234, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);

		contentPane.add(ViewSettings.createCloseButton(ViewSettings.WHITE));

		//new Game Button
		JButton newGame = ViewSettings.createButton(187, 101, 344, 40, new Color(244, 67, 54), "Neues Spiel");
		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//forward
				setVisible(false);
				new SpielController(user, NavigationController.this);
			}
		});
		contentPane.add(newGame);

		//profile Button
		JButton btnProfil = ViewSettings.createButton(187, 399, 344, 40, new Color(103, 58, 183), "Profil");
		btnProfil.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//forward
				setVisible(false);
				new ProfilController(user, NavigationController.this, null);
			}
		});
		contentPane.add(btnProfil);
		
		//ranking view Button
		JButton btnRanking = ViewSettings.createButton(187, 174, 344, 40, new Color(0, 150, 136), "Ranking");
		btnRanking.setBounds(187, 174, 344, 40);
		btnRanking.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//forward
				setVisible(false);
				new RankingController(user, NavigationController.this);
			}
		});
		contentPane.add(btnRanking);

		//friend view Button
		JButton btnFriends = ViewSettings.createButton(187, 248, 344, 40, new Color(255, 152, 0), "Freunde");
		btnFriends.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//forward
				setVisible(false);
				new FriendsController(user, NavigationController.this);
			}
		});
		contentPane.add(btnFriends);

		
		//open Lobbies Button
		JButton btnOpenLobbies = ViewSettings.createButton(187, 321, 344, 40, new Color(96, 125, 139), "Offene Lobbies");
		btnOpenLobbies.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//forward
				setVisible(false);
				new LobbyController(user, NavigationController.this);
			}
		});
		contentPane.add(btnOpenLobbies);
		
		
		//create Logout Button
		JButton btnLogout = ViewSettings.createButton(603, 0, 50, 50, Color.DARK_GRAY, "");
		btnLogout.setIcon(new ImageIcon(new ImageIcon(LoginController.class.getResource("/images/logout.png"))
				.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		btnLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//forward
				setVisible(false);
				frame.setUserName(user.getUsername());
				frame.setVisible(true);
			}
		});
		contentPane.add(btnLogout);
	}
}
