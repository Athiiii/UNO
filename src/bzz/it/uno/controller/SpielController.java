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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import bzz.it.uno.dao.LobbyDao;
import bzz.it.uno.model.Lobby;
import bzz.it.uno.model.User;
import javax.swing.JCheckBox;

/**
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class SpielController extends JFrame implements ActionListener {
	private User user;
	private JPanel contentPane;
	private int xy, xx;
	private NavigationController navigationFrame;
	private JTextField lobbyName;
	private JTextField numberPlayers;
	private JCheckBox onlineMode;

	public SpielController(User user, NavigationController navigationFrame) {
		this.navigationFrame = navigationFrame;
		this.user = user;
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 385);
		setVisible(true);
		contentPane = new JPanel();

		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				SpielController.this.setLocation(x - xx, y - xy);
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
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(11, 300, 11, 300));
		setContentPane(contentPane);
		
		setIconImage(new ImageIcon(new ImageIcon(LoginController.class.getResource("/images/uno_logo.png"))
				.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)).getImage());

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

		JButton backBtn = new JButton(" Zur\u00FCck");
		backBtn.setForeground(Color.WHITE);
		backBtn.setBounds(0, 0, 127, 50);
		backBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		backBtn.setBackground(Color.DARK_GRAY);
		backBtn.setIcon(new ImageIcon(new ImageIcon(LoginController.class.getResource("/images/back.png")).getImage()
				.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				navigationFrame.setVisible(true);
			}
		});
		backBtn.setBorderPainted(false);
		backBtn.setFocusPainted(false);
		backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				backBtn.setBackground(backBtn.getBackground().brighter());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				backBtn.setBackground(Color.DARK_GRAY);
			}
		});
		contentPane.add(backBtn);

		JLabel titleLabel = new JLabel("Spiel erstellen");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(160, 36, 438, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);

		lobbyName = new JTextField();
		lobbyName.setBounds(233, 180, 273, 39);
		lobbyName.setFont(new Font("Dialog", Font.PLAIN, 27));
		contentPane.add(lobbyName);

		Label usernameLabel = new Label("Lobbyname: ");
		usernameLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		usernameLabel.setBounds(90, 180, 137, 39);
		usernameLabel.setForeground(Color.WHITE);
		contentPane.add(usernameLabel);

		numberPlayers = new JTextField();
		numberPlayers.setFont(new Font("Dialog", Font.PLAIN, 27));
		numberPlayers.setBounds(233, 253, 137, 39);
		contentPane.add(numberPlayers);

		Label maxPlayer = new Label("Max Spieler:");
		maxPlayer.setForeground(Color.WHITE);
		maxPlayer.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		maxPlayer.setBounds(90, 253, 137, 39);
		contentPane.add(maxPlayer);

		JButton startBtn = new JButton("Start");
		startBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		startBtn.setBackground(new Color(0, 153, 204));
		startBtn.addActionListener(this);
		startBtn.setBounds(507, 320, 142, 39);
		startBtn.setBorderPainted(false);
		startBtn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				startBtn.setBackground(startBtn.getBackground().brighter());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				startBtn.setBackground(new Color(92, 184, 92));
			}
		});
		contentPane.add(startBtn);

		onlineMode = new JCheckBox("online");
		onlineMode.setBackground(Color.DARK_GRAY);
		onlineMode.setBounds(90, 312, 137, 39);
		onlineMode.setForeground(Color.WHITE);
		onlineMode.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		contentPane.add(onlineMode);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			int maxPlayers = Integer.parseInt(numberPlayers.getText());
			if (onlineMode.isEnabled() && maxPlayers >= 30) {
				numberPlayers.setText("30");
				JOptionPane.showMessageDialog(this, "Max. Spieler wurde auf 30 gesetzt", "Zu viele Max. Players", 1);
			} else if (!onlineMode.isEnabled() && maxPlayers >= 5) {
				numberPlayers.setText("5");
				JOptionPane.showMessageDialog(this, "Max. Spieler wurde auf 5 gesetzt", "Zu viele Max. Players", 1);
				if (lobbyName.getText().equals("")) {
					JOptionPane.showMessageDialog(this, "Lobbyname kann darf nicht leer sein", "Lobbyname", 1);
				}
			} else {
				Lobby lobbyExist = LobbyDao.getInstance().selectLobbyByName(lobbyName.getText());
				Lobby lobby = new Lobby(true, lobbyName.getText());
			
				if (lobbyExist == null) {
					if (onlineMode.isEnabled()) {
						// ONLINE MODE
						setVisible(false);
						new LobbyWaitController(user, navigationFrame, lobby);
					} else {
						// OFFLINE MODE
						new OfflineGameController(user, navigationFrame, lobby);
					}
				}else {
					JOptionPane.showMessageDialog(this, "Ein Lobby mit diesem Namen gibt es schon", "Lobbyname", 1);
				}
			}
		} catch (Exception ex) {
			numberPlayers.setText("");
			JOptionPane.showMessageDialog(this, "Ungültige Zahl bei Max. Spieler", "Ungültige Max. Spieler", 0);
		}

	}
}
