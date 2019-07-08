package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bzz.it.uno.dao.LobbyDao;
import bzz.it.uno.dao.UserLobbyDao;
import bzz.it.uno.frontend.UNODialog;
import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.Lobby;
import bzz.it.uno.model.User;
import bzz.it.uno.model.User_Lobby;

/**
 * Creation of the game
 * User can decide Lobby name (doesn't affect offline version)
 * and possibility beween online/offline
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
		
		contentPane = new JPanel();
		
		ViewSettings.setupFrame(this);
		ViewSettings.setupPanel(contentPane);
		setBounds(100, 100, 700, 385);

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
		setContentPane(contentPane);
		contentPane.add(ViewSettings.createCloseButton(ViewSettings.WHITE));
		contentPane.add(ViewSettings.createReturnButton(this, navigationFrame));

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

		JButton startBtn = ViewSettings.createButton(507, 320, 142, 39, new Color(0, 153, 204), "Start");
		startBtn.addActionListener(this);
		contentPane.add(startBtn);

		//checkbox to choose between online and offline mode
		onlineMode = new JCheckBox("online");
		onlineMode.setBackground(Color.DARK_GRAY);
		onlineMode.setBounds(90, 312, 137, 39);
		onlineMode.setForeground(Color.WHITE);
		onlineMode.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		onlineMode.setSelected(true);
		contentPane.add(onlineMode);
	}
	
	/**
	 * the whole validation of the user inputs.
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			int maxPlayers = Integer.parseInt(numberPlayers.getText());
			if (onlineMode.isSelected() && maxPlayers > 30) {
				numberPlayers.setText("30");
				new UNODialog(this, "Zu viele Max. Players", "Max. Spieler wurde auf 30 gesetzt",
						UNODialog.INFORMATION, UNODialog.OK_BUTTON);
			} else if (!onlineMode.isSelected() && maxPlayers > 5) {
				numberPlayers.setText("5");
				new UNODialog(this, "Zu viele Max. Players", "Max. Spieler wurde auf 5 gesetzt", UNODialog.INFORMATION, UNODialog.OK_BUTTON);
				if (lobbyName.getText().equals("")) {
					new UNODialog(this, "Lobbyname", "Lobbyname kann darf nicht leer sein", UNODialog.INFORMATION, UNODialog.OK_BUTTON);
				}
			} else {
				Lobby lobbyExist = LobbyDao.getInstance().selectLobbyByName(lobbyName.getText());

				if (lobbyExist == null) {
					if (onlineMode.isSelected()) {
						// ONLINE MODE

						LobbyDao lobbyDao = LobbyDao.getInstance();
						UserLobbyDao lobbyUser = UserLobbyDao.getInstance();
						Lobby lobby = new Lobby(true, lobbyName.getText(), LocalDate.now());
						lobbyDao.addLobby(lobby);
						lobby = lobbyDao.selectLobbyByName(lobby.getName());
						User_Lobby userLobby = new User_Lobby();
						userLobby.setLobby(lobby);
						userLobby.setUser(user);
						userLobby.setPoints(0);

						lobbyUser.addUserLobby(userLobby);

						setVisible(false);
						new LobbyWaitController(user, navigationFrame, lobby);
					} else {
						// OFFLINE MODE
						new CardsDisplayController(maxPlayers);
						dispose();
						navigationFrame.setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(this, "Ein Lobby mit diesem Namen gibt es schon", "Lobbyname", 1);
				}
			}
		} catch (Exception ex) {
			numberPlayers.setText("");
			JOptionPane.showMessageDialog(this, "Ungültige Zahl bei Max. Spieler", "Ungültige Max. Spieler", 0);
		}

	}
}
