package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

import bzz.it.uno.dao.UserLobbyDao;
import bzz.it.uno.frontend.Rank;
import bzz.it.uno.model.User;
import bzz.it.uno.model.User_Lobby;

public class RankingController extends JFrame {

	private User user;
	private JPanel contentPane;
	private int xy, xx;
	private NavigationController navigationFrame;
	private JTable table;
	private int selectedRow, selectedColumn = -1;
	private Object[] row;
	private DefaultTableModel tableModel;

	public RankingController(User user, NavigationController navigationFrame) {
		this.navigationFrame = navigationFrame;
		this.user = user;
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
		contentPane.setLayout(null);
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

		Label titleLabel = new Label("Ranking");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(258, 38, 240, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);

		String[] columnNames = { "Liga", "User", "Points" };
		tableModel = new DefaultTableModel(columnNames, 0);
		setRankingList();
		table = new JTable(tableModel) {
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
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(350);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.setBounds(73, 145, 548, 333);
		table.setShowGrid(false);

		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.setForeground(Color.white);
		table.setOpaque(false);
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBackground(new Color(0, 0, 0, 0.6f));
		table.setRowSelectionAllowed(true);
		table.setFocusable(false);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				// get the clicked cell's row and column
				selectedRow = table.getSelectedRow();
				selectedColumn = table.getSelectedColumn();

				// Repaints JTable
				table.repaint();
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 128, 700, 372);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		contentPane.add(scrollPane);
	}

	public void setRankingList() {
		UserLobbyDao userLobbyDao = new UserLobbyDao();
		List<User_Lobby> allUserLobbies = userLobbyDao.getAllUserLobbies();
		
		List<RankModel> ranks = new ArrayList<RankModel>();
		for (int i = 0; i < allUserLobbies.size(); ++i) {
			User_Lobby lobbyGame = allUserLobbies.get(i);
			int result = checkIfAlreadyInThere(allUserLobbies.get(i).getUser(), ranks);
			if (result == -1) {
				RankModel model = new RankModel(lobbyGame.getUser().getUsername(), lobbyGame.getPoints(),
						Rank.getRankImgByPoints(lobbyGame.getPoints()));
				ranks.add(model);
			} else {
				ranks.get(result).setPoints(ranks.get(result).getPoints() + lobbyGame.getPoints());
			}
		}
		for(int i = 0; i < ranks.size(); ++i) {
			double points = ranks.get(i).getPoints();
			String userName = ranks.get(i).getName();
			String liga = ranks.get(i).getLiga();
			Object[] data = {points, userName, liga};
			tableModel.addRow(data);	
		}
	}

	public int checkIfAlreadyInThere(User user, List<RankModel> ranks) {
		for (int i = 0; i < ranks.size(); ++i) {
			if (ranks.get(i).getName().equals(user.getUsername()))
				return i;
		}
		return -1;
	}
	

	public class RankModel {
		private String name;
		private double points;
		private String liga;

		public RankModel() {
			super();
		}

		public RankModel(String name, double points, String liga) {
			super();
			this.name = name;
			this.points = points;
			this.liga = liga;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getPoints() {
			return points;
		}

		public void setPoints(double points) {
			this.points = points;
		}

		public String getLiga() {
			return liga;
		}

		public void setLiga(String liga) {
			this.liga = liga;
		}
	}
}
