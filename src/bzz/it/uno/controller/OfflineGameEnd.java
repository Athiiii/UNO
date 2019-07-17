package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import bzz.it.uno.dao.UserLobbyDao;
import bzz.it.uno.frontend.TableHeaderRenderer;
import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.Lobby;
import bzz.it.uno.model.User;
import bzz.it.uno.model.User_Lobby;

/**
 * Display after game is finished (offline mode)
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class OfflineGameEnd extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int xy, xx;
	private JTable table;
	private DefaultTableModel tableModel;
	private int selectedRow;

	public OfflineGameEnd(User user, NavigationController navigationFrame, OfflineGameController[] players,
			Lobby lobby) {
		contentPane = new JPanel();

		ViewSettings.setupFrame(this);
		ViewSettings.setupPanel(contentPane);

		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				OfflineGameEnd.this.setLocation(x - xx, y - xy);
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

		String[] columnNames = { "Platz", "User", "Punkte" };
		tableModel = new DefaultTableModel(columnNames, 0);
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
		table.getColumnModel().getColumn(0).setPreferredWidth(175);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setPreferredWidth(225);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

		ViewSettings.setupTableDesign(table);

		table.setRowHeight(65);

		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		TableCellRenderer baseRenderer = table.getTableHeader().getDefaultRenderer();
		table.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRenderer));

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
		fillData(players);
		contentPane.add(ViewSettings.createDefaultScrollPane(table, 400, 700, 200));

		// title
		Label titleLabel = new Label("Endresultat");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(220, 38, 280, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);

		addDbPoints(user, players, lobby);
	}

	public void addDbPoints(User user, OfflineGameController[] players, Lobby lobby) {
		UserLobbyDao userLobbyDao = UserLobbyDao.getInstance();
		List<User_Lobby> userLobbies = userLobbyDao.selectByUser(user.getId());
		User_Lobby userLobby = null;
		for (int i = 0; i < userLobbies.size(); ++i) {
			if (userLobbies.get(i).getLobby().getId() == lobby.getId()) {
				userLobby = userLobbies.get(i);
				break;
			}
		}
		if (userLobby != null) {
			int points = 0;
			for (int i = 0; i < players.length; ++i) {
				if (players[i].getUsername().equals(user.getUsername())) {
					points = players[i].getPoints();
					break;
				}
			}
			userLobbyDao.updatePointsUserLobby(points, userLobby.getId());
		}
	}

	private void fillData(OfflineGameController[] players) {
		List<OfflinePlayerRank> rankList = new ArrayList<OfflinePlayerRank>();
		for (int i = 0; i < players.length; ++i) {
			rankList.add(new OfflinePlayerRank(players[i].getUsername(), players[i].getPoints()));
		}
		Collections.sort(rankList);
		for (int i = 0; i < rankList.size(); ++i) {
			int points = rankList.get(i).getPoints();
			String username = rankList.get(i).getUser();
			Object[] data = { i + 1, username, points };
			tableModel.addRow(data);
		}
	}
}

class OfflinePlayerRank implements Comparable<OfflinePlayerRank> {
	private String user;
	private Integer points;

	public OfflinePlayerRank() {
	}

	public OfflinePlayerRank(String user, Integer points) {
		this.user = user;
		this.points = points;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	@Override
	public int compareTo(OfflinePlayerRank o) {
		return o.getPoints().compareTo(getPoints());
	}
}
