package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.border.EmptyBorder;

import bzz.it.uno.model.User;

/**
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class NavigationController extends JFrame {

	private User user;
	private JPanel contentPane;
	private LoginController frame;
	private int xy, xx;

	public NavigationController(User user, LoginController frame) {
		this.user = user;
		this.frame = frame;
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setVisible(true);
		contentPane = new JPanel();

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
		contentPane.setLayout(null);

		Label titleLabel = new Label("Welcome");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(233, 16, 234, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);

		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(11, 300, 11, 300));
		setContentPane(contentPane);

		JButton closeWindow = new JButton("");
		closeWindow.setBounds(653, 0, 50, 50);
		closeWindow.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		closeWindow.setBackground(Color.DARK_GRAY);
		closeWindow.setIcon(new ImageIcon(new ImageIcon(LoginController.class.getResource("/images/closeWhite.png"))
				.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
		closeWindow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		closeWindow.setBorderPainted(false);
		closeWindow.setFocusPainted(false);
		closeWindow.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				closeWindow.setBackground(closeWindow.getBackground().brighter());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				closeWindow.setBackground(Color.DARK_GRAY);
			}
		});
		contentPane.add(closeWindow);

		JButton newGame = new JButton("Neues Spiel");
		newGame.setBounds(187, 101, 344, 40);
		newGame.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		newGame.setBackground(new Color(244, 67, 54));
		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new SpielController(user, NavigationController.this);
			}
		});
		newGame.setBorderPainted(false);
		newGame.setFocusPainted(false);
		newGame.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				newGame.setBackground(newGame.getBackground().brighter());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				newGame.setBackground(new Color(244, 67, 54));
			}
		});
		contentPane.add(newGame);

		JButton btnProfil = new JButton("Profil");
		btnProfil.setBounds(187, 399, 344, 40);
		btnProfil.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		btnProfil.setBackground(new Color(103, 58, 183));
		btnProfil.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new ProfilController(user, NavigationController.this);
			}
		});
		btnProfil.setBorderPainted(false);
		btnProfil.setFocusPainted(false);
		btnProfil.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnProfil.setBackground(btnProfil.getBackground().brighter());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnProfil.setBackground(new Color(103, 58, 183));
			}
		});
		contentPane.add(btnProfil);

		JButton btnRanking = new JButton("Ranking");
		btnRanking.setBounds(187, 174, 344, 40);
		btnRanking.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		btnRanking.setBackground(new Color(0, 150, 136));
		btnRanking.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new RankingController(user, NavigationController.this);
			}
		});
		btnRanking.setBorderPainted(false);
		btnRanking.setFocusPainted(false);
		btnRanking.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnRanking.setBackground(btnRanking.getBackground().brighter());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnRanking.setBackground(new Color(0, 150, 136));
			}
		});
		contentPane.add(btnRanking);

		JButton btnFreunde = new JButton("Freunde");
		btnFreunde.setBounds(187, 248, 344, 40);
		btnFreunde.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		btnFreunde.setBackground(new Color(255, 152, 0));
		btnFreunde.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new FreundesListenController(user, NavigationController.this);
			}
		});
		btnFreunde.setBorderPainted(false);
		btnFreunde.setFocusPainted(false);
		btnFreunde.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnFreunde.setBackground(btnFreunde.getBackground().brighter());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnFreunde.setBackground(new Color(255, 152, 0));
			}
		});
		contentPane.add(btnFreunde);

		JButton btnOffeneLobbies = new JButton("Offene Lobbies");
		btnOffeneLobbies.setBounds(187, 321, 344, 40);
		btnOffeneLobbies.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		btnOffeneLobbies.setBackground(new Color(96, 125, 139));
		btnOffeneLobbies.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new LobbyController(user, NavigationController.this);
			}
		});
		btnOffeneLobbies.setBorderPainted(false);
		btnOffeneLobbies.setFocusPainted(false);
		btnOffeneLobbies.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnOffeneLobbies.setBackground(btnOffeneLobbies.getBackground().brighter());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnOffeneLobbies.setBackground(new Color(96, 125, 139));
			}
		});
		contentPane.add(btnOffeneLobbies);

		JButton btnLogout = new JButton("");
		btnLogout.setBounds(603, 0, 50, 50);
		btnLogout.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		btnLogout.setBackground(Color.DARK_GRAY);
		btnLogout.setIcon(new ImageIcon(new ImageIcon(LoginController.class.getResource("/images/logout.png"))
				.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
		btnLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				frame.SetUserName(user.getUsername());
				frame.setVisible(true);
			}
		});
		btnLogout.setBorderPainted(false);
		btnLogout.setFocusPainted(false);
		btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnLogout.setBackground(btnLogout.getBackground().brighter());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnLogout.setBackground(Color.DARK_GRAY);
			}
		});
		contentPane.add(btnLogout);
	}
}
