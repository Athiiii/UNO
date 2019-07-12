package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
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
 * Shows the ranking of all players of all players who have more than 1 point
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class RankingController extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private int xy, xx;
	private int selectedRow = -1;

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;

	public RankingController(User user, NavigationController navigationFrame) {
		
		contentPane = new JPanel();
		ViewSettings.setupFrame(this);
		ViewSettings.setupPanel(contentPane);

		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				RankingController.this.setLocation(x - xx, y - xy);
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

		//title
		Label titleLabel = new Label("Ranking");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(258, 38, 240, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);

		String[] columnNames = { "Platz", "Liga", "User", "Points" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;
			
			//define column datatypes
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
		setRankingList();
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
		
		//setting up table
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
				super.mouseClicked(arg0);
				// get the clicked cell's row and column
				selectedRow = table.getSelectedRow();

				// Repaints JTable
				table.repaint();
			}
		});
		
		contentPane.add(ViewSettings.createDefaultScrollPane(table, 400, 700, 200));
	}

	private void setRankingList() {
		//get all Data From Table: User_Lobby
		List<User_Lobby> allUserLobbies = UserLobbyDao.getInstance().getAllUserLobbies();

		List<RankModel> ranks = new ArrayList<RankModel>();
		
		//create a model for every user with all points
		for (int i = 0; i < allUserLobbies.size(); ++i) {
			User_Lobby lobbyGame = allUserLobbies.get(i);
			int result = checkIfUserAlreadyInList(allUserLobbies.get(i).getUser(), ranks);
			if (result == -1) {
				RankModel model = new RankModel(lobbyGame.getUser().getUsername(), lobbyGame.getPoints(), null);
				ranks.add(model);
			} else {
				ranks.get(result).setPoints(ranks.get(result).getPoints() + lobbyGame.getPoints());
			}
		}
		
		//Set Liga image
		for (int i = 0; i < ranks.size(); ++i) {
			ImageIcon icon = new ImageIcon(RankingController.class
					.getResource("/images/" + Rank.getRankImgByPoints(ranks.get(i).getPoints())));
			float height = 60;
			float width = ((height / icon.getIconHeight()) * icon.getIconWidth());
			icon = new ImageIcon(icon.getImage().getScaledInstance((int) width, (int) height, Image.SCALE_SMOOTH));
			ranks.get(i).setLiga(icon);
		}
		
		//sort by points
		Collections.sort(ranks, Collections.reverseOrder());
		
		//create table row
		for (int i = 0; i < ranks.size(); ++i) {
			double points = ranks.get(i).getPoints();
			String userName = ranks.get(i).getName();
			ImageIcon liga = ranks.get(i).getLiga();
			Object[] data = { i + 1, liga, userName, (int) points };
			tableModel.addRow(data);
		}
	}

	private int checkIfUserAlreadyInList(User user, List<RankModel> ranks) {
		for (int i = 0; i < ranks.size(); ++i) {
			if (ranks.get(i).getName().equals(user.getUsername()))
				return i;
		}
		return -1;
	}
}
