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
		JButton newGame = new JButton("Neues Spiel");
		newGame.setBounds(187, 101, 344, 40);
		newGame.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		newGame.setBackground(new Color(244, 67, 54));
		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//forward
				setVisible(false);
				new SpielController(user, NavigationController.this);
			}
		});
		newGame.setBorderPainted(false);
		newGame.setFocusPainted(false);
		newGame.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				newGame.setBackground(newGame.getBackground().brighter());
			}

			public void mouseExited(MouseEvent evt) {
				newGame.setBackground(new Color(244, 67, 54));
			}
		});
		contentPane.add(newGame);

		//profile Button
		JButton btnProfil = new JButton("Profil");
		btnProfil.setBounds(187, 399, 344, 40);
		btnProfil.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		btnProfil.setBackground(new Color(103, 58, 183));
		btnProfil.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//forward
				setVisible(false);
				new ProfilController(user, NavigationController.this, null);
			}
		});
		btnProfil.setBorderPainted(false);
		btnProfil.setFocusPainted(false);
		btnProfil.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btnProfil.setBackground(btnProfil.getBackground().brighter());
			}

			public void mouseExited(MouseEvent evt) {
				btnProfil.setBackground(new Color(103, 58, 183));
			}
		});
		contentPane.add(btnProfil);
		
		//ranking view Button
		JButton btnRanking = new JButton("Ranking");
		btnRanking.setBounds(187, 174, 344, 40);
		btnRanking.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		btnRanking.setBackground(new Color(0, 150, 136));
		btnRanking.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//forward
				setVisible(false);
				new RankingController(user, NavigationController.this);
			}
		});
		btnRanking.setBorderPainted(false);
		btnRanking.setFocusPainted(false);
		btnRanking.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btnRanking.setBackground(btnRanking.getBackground().brighter());
			}

			public void mouseExited(MouseEvent evt) {
				btnRanking.setBackground(new Color(0, 150, 136));
			}
		});
		contentPane.add(btnRanking);

		//friend view Button
		JButton btnFriends = new JButton("Freunde");
		btnFriends.setBounds(187, 248, 344, 40);
		btnFriends.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		btnFriends.setBackground(new Color(255, 152, 0));
		btnFriends.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//forward
				setVisible(false);
				new FriendsController(user, NavigationController.this);
			}
		});
		btnFriends.setBorderPainted(false);
		btnFriends.setFocusPainted(false);
		btnFriends.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btnFriends.setBackground(btnFriends.getBackground().brighter());
			}

			public void mouseExited(MouseEvent evt) {
				btnFriends.setBackground(new Color(255, 152, 0));
			}
		});
		contentPane.add(btnFriends);

		
		//open Lobbies Button
		JButton btnOpenLobbies = new JButton("Offene Lobbies");
		btnOpenLobbies.setBounds(187, 321, 344, 40);
		btnOpenLobbies.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		btnOpenLobbies.setBackground(new Color(96, 125, 139));
		btnOpenLobbies.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//forward
				setVisible(false);
				new LobbyController(user, NavigationController.this);
			}
		});
		btnOpenLobbies.setBorderPainted(false);
		btnOpenLobbies.setFocusPainted(false);
		btnOpenLobbies.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btnOpenLobbies.setBackground(btnOpenLobbies.getBackground().brighter());
			}

			public void mouseExited(MouseEvent evt) {
				btnOpenLobbies.setBackground(new Color(96, 125, 139));
			}
		});
		contentPane.add(btnOpenLobbies);
		
		
		//create Logout Button
		JButton btnLogout = new JButton("");
		btnLogout.setBounds(603, 0, 50, 50);
		btnLogout.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		btnLogout.setBackground(Color.DARK_GRAY);
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
		btnLogout.setBorderPainted(false);
		btnLogout.setFocusPainted(false);
		btnLogout.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btnLogout.setBackground(btnLogout.getBackground().brighter());
			}

			public void mouseExited(MouseEvent evt) {
				btnLogout.setBackground(Color.DARK_GRAY);
			}
		});
		contentPane.add(btnLogout);
	}
}
