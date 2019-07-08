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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
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
 * See profile data, your liga and your playing history. You can also delete and
 * edit your profile in this view
 * 
 * @author Severin Hersche
 *
 */
public class ProfilController extends JFrame {
	private static final long serialVersionUID = 1L;
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

		contentPane = new JPanel();
		ViewSettings.setupFrame(this);
		ViewSettings.setupPanel(contentPane);

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
			JButton addImageBtn = ViewSettings.createButton(393, 446, 154, 40, new Color(41, 204, 22), "Bild hinzufuegen");
			addImageBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						getImageFromFileSystem();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			contentPane.add(addImageBtn);

			// button to delete the profile
			JButton btnDelete = new JButton("L\u00F6schen");
			btnDelete.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
			btnDelete.setBackground(new Color(244, 67, 54));
			btnDelete.setBounds(557, 446, 120, 40);

			contentPane.add(btnDelete);
		}

		tableModel = new DefaultTableModel(new Object[][] {},
				new String[] { "Gespielt", "Spieler", "Punkte", "Rank" }) {
			private static final long serialVersionUID = 1L;
			// Define column datatype
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

		// define table width
		table.getColumnModel().getColumn(0).setPreferredWidth(250);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);

		table.setRowHeight(50);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		// get renderer for every cell in the table
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

		contentPane.add(ViewSettings.createDefaultScrollPane(table, 300, 700, 230));

		name = new JLabel();
		name.setBounds(160, 112, 154, 55);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		contentPane.add(name);

		List<User_Lobby> allUserLobbies = UserLobbyDao.getInstance().getAllUserLobbies();

		rank = new JLabel("Platz: " + Integer.toString(getRankOfUser(allUserLobbies)));
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

		profileImage = new JLabel();
		profileImage.setBounds(23, 61, 127, 131);
		contentPane.add(profileImage);
		if (user.getPicture() != null) {
			try {
				profileImage.setIcon(new ImageIcon(getPictureFromUser(user)));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		setTableData(allUserLobbies);
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

	private int getRankOfUser(List<User_Lobby> userGames) {
		int rank = -1;

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

	private void setProfileData() {
		name.setText(showedUser.getUsername());
	}

	private void setTableData(List<User_Lobby> allUserLobbies) {
		List<User_Lobby> userLobbies = UserDao.getInstance().selectByUsername(showedUser.getUsername()).getUserLobby();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (userLobbies.size() > 0) {
			for (User_Lobby userLobby : userLobbies) {
				int countedPlayers = countPlayer(userLobby, allUserLobbies);
				model.addRow(
						new Object[] { String.valueOf(userLobby.getLobby().getDate()), Integer.valueOf(countedPlayers),
								Integer.valueOf(userLobby.getPoints()), Integer.valueOf(userLobby.getRank()) });
			}
		}
	}

	private int countPlayer(User_Lobby userLobby, List<User_Lobby> allUserLobbies) {
		int counter = 0;

		for (User_Lobby user_Lobby : allUserLobbies) {
			if (user_Lobby.getLobby().getId() == userLobby.getLobby().getId()) {
				counter += 1;
			}
		}
		return counter;
	}

	private Image getPictureFromUser(User user) throws IOException {
		byte[] decode = Base64.getDecoder().decode(user.getPicture());
		ByteArrayInputStream bis = new ByteArrayInputStream(decode);

		Image image = ImageIO.read(bis);
		bis.close();

		Image scaledInstance = scaleImage(image);

		profileImage.setIcon(new ImageIcon(scaledInstance));
		return scaledInstance;
	}

	private void getImageFromFileSystem() throws IOException {
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();

			BufferedImage image = null;

			image = ImageIO.read(file);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			ImageIO.write(image, "png", bos);
			byte[] imageBytes = bos.toByteArray();
			String encodeToString = Base64.getEncoder().encodeToString(imageBytes);
			showedUser.getId();
			showedUser.setPicture(encodeToString);
			UserDao.getInstance().updateUser(showedUser);

			Image scaledInstance = scaleImage(image);

			profileImage.setIcon(new ImageIcon(scaledInstance));
		}
	}

	private Image scaleImage(Image image) {
		return image.getScaledInstance(profileImage.getWidth(), profileImage.getHeight(), Image.SCALE_SMOOTH);
	}
}
