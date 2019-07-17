package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import bzz.it.uno.dao.UserDao;
import bzz.it.uno.frontend.JTableButtonMouseListener;
import bzz.it.uno.frontend.JTableButtonRenderer;
import bzz.it.uno.frontend.TableHeaderRenderer;
import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.User;

/**
 * <li>View all friends</li>
 * <li>remove friends</li>
 * <li>add new friends</li> <br>
 * 
 * @author Severin Hersche
 *
 */
public class FriendsController extends JFrame {
	private static final long serialVersionUID = 1L;
	private User user;
	private JPanel contentPane;
	private int xy, xx;
	private DefaultTableModel tableModel;
	private JTable table;
	private int selectedRow = -1;
	private JButton friendSearch;
	private List<User> actualListOfFriends;

	/**
	 * This is started when you want so see your friends
	 * 
	 * @param user
	 *            or = owner
	 * @param navigationFrame
	 */
	public FriendsController(User user, NavigationController navigationFrame) {
		this.user = user;

		contentPane = new JPanel();
		ViewSettings.setupPanel(contentPane);
		ViewSettings.setupFrame(this);

		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// set x & y cords when drag mouse on the panel
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();

				// relocate view
				FriendsController.this.setLocation(x - xx, y - xy);
			}
		});
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// update x & y cords on every mouse press
				xx = e.getX();
				xy = e.getY();
			}
		});
		setContentPane(contentPane);

		String[] columnNames = { "Username", "Action" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			// set Column Datatype
			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 1:
					return JButton.class;
				default:
					return String.class;
				}
			}
		};
		;
		table = new JTable(tableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				Color color = Color.DARK_GRAY;

				// to highlight which row is selected
				if (selectedRow == row)
					color = color.brighter();
				if (column != 1) {
					c.setBackground(color);
					c.setForeground(Color.white);
				}
				return c;
			}
		};

		TableCellRenderer baseRenderer = table.getTableHeader().getDefaultRenderer();
		table.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRenderer));

		// column width
		table.getColumnModel().getColumn(0).setPreferredWidth(550);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);

		table.setRowHeight(50);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(new JTableButtonRenderer());

		table.addMouseListener(new JTableButtonMouseListener(table));
		List<User> allUsers = UserDao.getInstance().getAllUsers();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				// get the clicked cell's row
				selectedRow = table.getSelectedRow();
				if (arg0.getClickCount() > 1) {
					for (User u : allUsers) {
						if (u.getUsername().matches(actualListOfFriends.get(selectedRow).getUsername())) {
							dispose();
							new ProfileController(user, navigationFrame, u, true);
						}
					}
				}

				// Repaints JTable
				table.repaint();
			}
		});

		ViewSettings.setupTableDesign(table);
		friendSearch = ViewSettings.createButton(450, 60, 200, 40, new Color(41, 204, 22), "Spieler Suche");
		friendSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new PlayerSearchController(user, navigationFrame, allUsers);
				dispose();
			}
		});
		contentPane.add(friendSearch);
		contentPane.add(ViewSettings.createCloseButton(ViewSettings.WHITE));
		contentPane.add(ViewSettings.createReturnButton(this, navigationFrame));
		contentPane.add(ViewSettings.createDefaultScrollPane(table, 300, 700, 200));
		setTableData();
	}

	private void setTableData() {
		List<User> friends = user.getFriendList();
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		if (friends != null) {
			for (User friend : friends) {
				JButton removeFriendBtn = new JButton("Entfernen");
				// the username will be defined here, so that the friend can be removed
				removeFriendBtn.setName(friend.getUsername());

				model.addRow(new Object[] { friend.getUsername(), removeFriendBtn });
				removeFriendBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						removeFriend(removeFriendBtn);
					}

					private void removeFriend(JButton removeFriendBtn) {
						if (user.getFriendList() != null) {
							int counter = 0;
							User removeFriend = null;
							for (User friends : user.getFriendList()) {
								if (friends.getUsername().equals(removeFriendBtn.getName())) {

									model.removeRow(counter);
									removeFriend = friend;

								}
								counter++;
							}
							if (removeFriend != null) {
								user.getFriendList().remove(removeFriend);
								UserDao.getInstance().updateUser(user);
							}
						}
					}
				});
			}
			actualListOfFriends = friends;
		}
	}
}
