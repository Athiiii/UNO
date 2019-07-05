package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import bzz.it.uno.dao.UserDao;
import bzz.it.uno.dao.UserLobbyDao;
import bzz.it.uno.frontend.Rank;
import bzz.it.uno.frontend.RankModel;
import bzz.it.uno.frontend.TableHeaderRenderer;
import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.User;
import bzz.it.uno.model.User_Lobby;

/**
 * See profile data, your liga and your playing history.
 * You can also delete and edit your profile in this view
 * 
 * @author Severin Hersche
 *
 */
public class ProfilController extends JFrame {

	private User showedUser;
	private JPanel contentPane;

	private int xy, xx;
	private int selectedRow = -1;

	private JTable table;
	private JLabel name;
	private JLabel rank;
	private JLabel liga;
	private JLabel profileImage;
	private DefaultTableModel tableModel;

	public ProfilController(User user, NavigationController navigationFrame, User otherUser) {
		if (otherUser != null) {
			this.showedUser = otherUser;
		} else {
			this.showedUser = user;
		}

		ViewSettings.setupFrame(this);
		contentPane = new JPanel();

		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				ProfilController.this.setLocation(x - xx, y - xy);
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

		contentPane.add(ViewSettings.createCloseButton(ViewSettings.WHITE));
		contentPane.add(ViewSettings.createReturnButton(this, navigationFrame));

		// title
		Label titleLabel = new Label("Profil");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(281, 41, 136, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);

		if (otherUser != null) {
			// Button for adding a new Friend
			JButton friends = new JButton("Freund Hinzuf\u00fcgen");
			friends.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					user.getFriendList().add(showedUser);
					UserDao.getInstance().updateUser(user);
				}
			});
			friends.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
			friends.setBackground(new Color(166, 166, 166));
			friends.setBounds(23, 449, 230, 40);
			contentPane.add(friends);
		} else {
			// Button to edit the profile
			JButton btnEdit = new JButton("Bearbeiten");
			btnEdit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
			btnEdit.setBackground(new Color(41, 204, 22));
			btnEdit.setBounds(393, 446, 154, 40);
			contentPane.add(btnEdit);

			// button to delete the profile
			JButton btnDelete = new JButton("L\u00F6schen");
			btnDelete.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
			btnDelete.setBackground(new Color(244, 67, 54));
			btnDelete.setBounds(557, 446, 120, 40);

			contentPane.add(btnDelete);
		}

		JScrollPane scrollPane = ViewSettings.createDefaultScrollPane(table, 120, 40 );
		contentPane.add(scrollPane);

		tableModel = new DefaultTableModel(new Object[][] {},
				new String[] { "Gespielt", "Spieler", "Punkte", "Rank" }) {
			private static final long serialVersionUID = 1L;
			//Define column datatype
			Class<?>[] columnTypes = new Class[] { String.class, Integer.class, Integer.class, Integer.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};
		table = new JTable(tableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				Color color = Color.DARK_GRAY;

				if (selectedRow == row)
					color = color.brighter();
				c.setBackground(color);
				c.setForeground(Color.white);
				return c;
			}
		};
		ViewSettings.setupTableDesign(table);
		TableCellRenderer baseRenderer = table.getTableHeader().getDefaultRenderer();
		table.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRenderer));

		table.getColumnModel().getColumn(0).setPreferredWidth(250);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);

		table.setRowHeight(50);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				// get the clicked cell's row and column
				selectedRow = table.getSelectedRow();

				// Repaints JTable
				table.repaint();
			}
		});

		name = new JLabel();
		name.setBounds(160, 112, 154, 55);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		contentPane.add(name);

		rank = new JLabel("Platz: " + Integer.toString(getRankOfUser()));
		rank.setBounds(160, 194, 96, 20);
		rank.setForeground(Color.WHITE);
		rank.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		contentPane.add(rank);

		liga = new JLabel("");
		ImageIcon icon = new ImageIcon(
				ProfilController.class.getResource("/images/" + Rank.getRankImgByPoints(getPointsByUser())));
		float height = 140;
		float width = ((height / icon.getIconHeight()) * icon.getIconWidth());
		icon = new ImageIcon(icon.getImage().getScaledInstance((int) width, (int) height, Image.SCALE_SMOOTH));
		liga.setIcon(icon);
		liga.setBounds(480, 80, 150, 150);
		contentPane.add(liga);

		if (user.getPicture() != null) {
			profileImage = new JLabel(new ImageIcon(getPictureFromUser(user)));
			profileImage.setBounds(23, 61, 127, 131);
			contentPane.add(profileImage);
		}

		setTableData();
		setProfileData();
	}

	private double getPointsByUser() {
		List<User_Lobby> userGames = UserLobbyDao.getInstance().selectByUser(showedUser.getId());
		double points = 0;
		for (int i = 0; i < userGames.size(); ++i) {
			points += userGames.get(i).getPoints();
		}
		return points;
	}

	private int getRankOfUser() {
		int rank = -1;
		List<User_Lobby> userGames = UserLobbyDao.getInstance().getAllUserLobbies();
		List<RankModel> ranks = new ArrayList<RankModel>();

		for (int i = 0; i < userGames.size(); ++i) {
			User_Lobby lobbyGame = userGames.get(i);
			int result = checkIfUserAlreadyInList(lobbyGame.getUser(), ranks);
			if (result == -1) {
				RankModel model = new RankModel();
				model.setName(lobbyGame.getUser().getUsername());
				model.setPoints(lobbyGame.getPoints());
				ranks.add(model);
			} else {
				ranks.get(result).setPoints(ranks.get(result).getPoints() + lobbyGame.getPoints());
			}
		}
		Collections.sort(ranks, Collections.reverseOrder());

		for (int i = 0; i < ranks.size(); ++i) {
			if (ranks.get(i).getName().equals(showedUser.getUsername()))
				;
			rank = i + 1;
		}
		return rank;
	}

	public int checkIfUserAlreadyInList(User user, List<RankModel> ranks) {
		for (int i = 0; i < ranks.size(); ++i) {
			if (ranks.get(i).getName().equals(user.getUsername()))
				return i;
		}
		return -1;
	}

	private BufferedImage getPictureFromUser(User user) {
		ByteArrayInputStream bis = new ByteArrayInputStream(user.getPicture());
		BufferedImage image = null;
		try {
			image = ImageIO.read(bis);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return image;
	}

	private void setProfileData() {
		name.setText(showedUser.getUsername());
	}

	private void setTableData() {
		List<User_Lobby> userLobbies = UserDao.getInstance().selectByUsername(showedUser.getUsername()).getUserLobby();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (userLobbies.size() > 0) {
			for (User_Lobby userLobby : userLobbies) {
				int countedPlayers = countPlayer(userLobby);
				model.addRow(
						new Object[] { String.valueOf(userLobby.getLobby().getDate()), Integer.valueOf(countedPlayers),
								Integer.valueOf(userLobby.getPoints()), Integer.valueOf(userLobby.getRank()) });
			}
		}
	}

	private int countPlayer(User_Lobby userLobby) {
		int counter = 0;
		List<User_Lobby> allUserLobbies = UserLobbyDao.getInstance().getAllUserLobbies();
		for (User_Lobby user_Lobby : allUserLobbies) {
			if (user_Lobby.getLobby().getId() == userLobby.getLobby().getId()) {
				counter += 1;
			}
		}
		return counter;
	}
}
