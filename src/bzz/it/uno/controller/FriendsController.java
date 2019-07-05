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
 * <li>add new friends</li>
 * <br>
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

	public FriendsController(User user, NavigationController navigationFrame) {
		this.user = user;
		ViewSettings.setupFrame(this);
		contentPane = new JPanel();

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
		ViewSettings.setupPanel(contentPane);
		setContentPane(contentPane);

		String[] columnNames = { "Username", "Punkte", "Action" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 3153162956700695186L;

			// set Column Datatype
			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 1:
					return Integer.class;
				case 2:
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

				if (selectedRow == row)
					color = color.brighter();
				if (column != 2) {
					c.setBackground(color);
					c.setForeground(Color.white);
				}
				return c;
			}
		};

		TableCellRenderer baseRenderer = table.getTableHeader().getDefaultRenderer();
		table.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRenderer));

		// column width
		table.getColumnModel().getColumn(0).setPreferredWidth(300);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);

		table.setRowHeight(50);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(new JTableButtonRenderer());

		table.addMouseListener(new JTableButtonMouseListener(table));

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				// get the clicked cell's row
				selectedRow = table.getSelectedRow();

				// Repaints JTable
				table.repaint();
			}
		});

		ViewSettings.setupTableDesign(table);
		contentPane.add(ViewSettings.createCloseButton(ViewSettings.WHITE));
		contentPane.add(ViewSettings.createReturnButton(this, navigationFrame));
		contentPane.add(ViewSettings.createDefaultScrollPane(table, 500, 700));
		setTableData();
	}

	private void setTableData() {
		List<User> friends = user.getFriendList();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (friends != null) {
			for (User friend : friends) {
				JButton removeFriendBtn = new JButton("Entfernen");
				// Hier wird der username gesetzt damit nacher anhand von ihm dann der Freund
				// entfert werden kann
				removeFriendBtn.setName(friend.getUsername());

				model.addRow(new Object[] { friend.getUsername(), friend.getUserLobby().get(0), removeFriendBtn });
				removeFriendBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						removeFriend(removeFriendBtn);

					}

					private void removeFriend(JButton removeFriendBtn) {
						if (user.getFriendList() != null) {
							for (User user : user.getFriendList()) {
								if (user.getUsername().equals(removeFriendBtn.getName())) {
									user.getFriendList().remove(user);
									UserDao.getInstance().addUser(user);
								}
							}

						}
					}
				});
			}
		}
	}
}
