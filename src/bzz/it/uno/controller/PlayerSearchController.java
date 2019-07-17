package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import bzz.it.uno.dao.UserLobbyDao;
import bzz.it.uno.frontend.Rank;
import bzz.it.uno.frontend.RankModel;
import bzz.it.uno.frontend.TableHeaderRenderer;
import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.User;
import bzz.it.uno.model.User_Lobby;

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
	private DefaultTableModel tableModel;
	private JTable table;
	private int selectedRow;
	private List<User_Lobby> allUserLobbies;
	private List<User> allUser;
	private List<RankModel> actuallListOfUser;

	/**
	 * Create list of players to search for
	 * 
	 * @param user
	 * @param navigationController
	 * @param allUsers             a List of all users in the application
	 */
	public PlayerSearchController(User user, NavigationController navigationController, List<User> allUsers) {
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
		contentPane.add(ViewSettings.createReturnButton(this, navigationController));
		allUser = allUsers;

		// remove the user itself from the list
		allUser.removeIf(u -> u.getId() == user.getId());

		createTitle();
		allUserLobbies = UserLobbyDao.getInstance().getAllUserLobbies();

		// remove all user lobbies where the user itself played
		allUserLobbies.removeIf(ul -> ul.getUser().getId() == user.getId());

		JTextField jTextField = new JTextField();
		jTextField.setBounds(110, 150, 350, 34);
		jTextField.getDocument().addDocumentListener(new DocumentAdapter() {
			@Override
			public void onChange() {
				for (User user : allUser) {
					System.out.println(user.getUsername());
					if (jTextField.getText().isEmpty()) {
						addAllUsers(null);
					} else if (user.getUsername().toLowerCase()
							.matches(".*" + jTextField.getText().toLowerCase() + ".*")) {
						addAllUsers(user.getUsername());
					}
				}

			}

		});
		contentPane.add(jTextField);

		JButton search = ViewSettings.createButton(480, 150, 100, 34, Color.GRAY, "Suche");
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (jTextField.getText().isEmpty()) {

				} else if (user.getUsername().toLowerCase().matches(".*" + jTextField.getText().toLowerCase() + ".*")) {
					addAllUsers(user.getUsername());
				}
			}
		});
		contentPane.add(search);
		String[] columnNames = { "Platz", "Liga", "User", "Punkte" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			// define column datatypes
			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return Integer.class;
				case 1:
					return ImageIcon.class;
				default:
					return String.class;
				}
			}
		};
		addAllUsers(null);
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

		// setting up table
		table.getColumnModel().getColumn(0).setPreferredWidth(75);
		table.getColumnModel().getColumn(1).setPreferredWidth(125);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

		ViewSettings.setupTableDesign(table);

		table.setBounds(73, 145, 548, 500);
		table.setRowHeight(65);

		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		TableCellRenderer baseRenderer = table.getTableHeader().getDefaultRenderer();
		table.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRenderer));

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// get the clicked cell's row and column
				selectedRow = table.getSelectedRow();
				if (arg0.getClickCount() > 1) {
					dispose();
					for (User u : allUser) {
						if (u.getUsername().matches(actuallListOfUser.get(selectedRow).getName())) {
							new ProfileController(user, navigationController, u, false);
						}
					}
				}

				// Repaints JTable
				table.repaint();
			}
		});

		contentPane.add(ViewSettings.createDefaultScrollPane(table, 400, 700, 200));
	}

	private int checkIfUserAlreadyInList(User user, List<RankModel> ranks) {
		for (int i = 0; i < ranks.size(); ++i) {
			if (ranks.get(i).getName().equals(user.getUsername()))
				return i;
		}
		return -1;
	}

	private void createTitle() {
		JLabel titleLabel = new JLabel("Spieler suchen");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(135, 54, 480, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);
	}

	private void addAllUsers(String username) {
		List<RankModel> ranks = new ArrayList<RankModel>();
		for (int i = 0; i < allUserLobbies.size(); ++i) {
			User_Lobby lobbyGame = allUserLobbies.get(i);
			if (username == null || username.equals(allUserLobbies.get(i).getUser().getUsername())) {
				int result = checkIfUserAlreadyInList(allUserLobbies.get(i).getUser(), ranks);
				if (result == -1) {
					RankModel model = new RankModel(lobbyGame.getUser().getUsername(), lobbyGame.getPoints(), null);
					ranks.add(model);
				} else {
					ranks.get(result).setPoints(ranks.get(result).getPoints() + lobbyGame.getPoints());
				}
			}

		}

		for (User user : allUser) {
			if (username == null || username.equals(user.getUsername())) {
				if (checkIfUserAlreadyInList(user, ranks) == -1) {
					RankModel rankModel = new RankModel();
					rankModel.setPoints(0);
					rankModel.setName(user.getUsername());
					ranks.add(rankModel);
				}
			}
		}

		// Set Liga image
		for (int i = 0; i < ranks.size(); ++i) {
			ImageIcon icon = new ImageIcon(RankingController.class
					.getResource("/images/" + Rank.getRankImgByPoints(ranks.get(i).getPoints())));
			float height = 60;
			float width = ((height / icon.getIconHeight()) * icon.getIconWidth());
			icon = new ImageIcon(icon.getImage().getScaledInstance((int) width, (int) height, Image.SCALE_SMOOTH));
			ranks.get(i).setLiga(icon);
		}

		int rowCount = tableModel.getRowCount();
		// Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}

		// sort by points
		Collections.sort(ranks, Collections.reverseOrder());

		// create table row
		for (int i = 0; i < ranks.size(); ++i) {
			double points = ranks.get(i).getPoints();
			String userName = ranks.get(i).getName();
			ImageIcon liga = ranks.get(i).getLiga();
			Object[] data = { i + 1, liga, userName, (int) points };
			tableModel.addRow(data);
		}
		actuallListOfUser = ranks;
	}

	class DocumentAdapter implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			onChange();

		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			onChange();

		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			onChange();
		}

		public void onChange() {

		}

	}

}
