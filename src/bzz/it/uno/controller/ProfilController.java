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

import bzz.it.uno.dao.HistoryDao;
import bzz.it.uno.dao.UserLobbyDao;
import bzz.it.uno.frontend.Rank;
import bzz.it.uno.frontend.RankModel;
import bzz.it.uno.frontend.TableHeaderRenderer;
import bzz.it.uno.model.History;
import bzz.it.uno.model.User;
import bzz.it.uno.model.User_Lobby;

public class ProfilController extends JFrame {

	private User user;
	private JPanel contentPane;
	private int xy, xx;
	private NavigationController navigationFrame;
	private JTable table;
	private JLabel name;
	private JLabel rank;
	private JLabel liga;
	private JLabel profileImage;
	private DefaultTableModel tableModel;
	private int selectedColumn, selectedRow = -1;

	public ProfilController(User user, NavigationController navigationFrame) {
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

		JButton btnDelete = new JButton("L\u00F6schen");
		btnDelete.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		btnDelete.setBackground(new Color(244, 67, 54));
		btnDelete.setBounds(557, 446, 120, 40);
		contentPane.add(btnDelete);

		JButton friends = new JButton("Freunde");
		friends.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		friends.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		friends.setBackground(new Color(166, 166, 166));
		friends.setBounds(23, 449, 136, 40);
		contentPane.add(friends);

		JButton btnEdit = new JButton("Bearbeiten");
		btnEdit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		btnEdit.setBackground(new Color(41, 204, 22));
		btnEdit.setBounds(393, 446, 154, 40);
		contentPane.add(btnEdit);

		Label titleLabel = new Label("Profil");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(281, 41, 136, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getVerticalScrollBar().setBackground(Color.DARK_GRAY.darker());
		scrollPane.setBounds(0, 234, 700, 189);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPane);

		tableModel = new DefaultTableModel(new Object[][] {},
				new String[] { "Gespielt", "Spieler", "Punkte", "Rank" }) {
			Class[] columnTypes = new Class[] { String.class, Integer.class, Integer.class, Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};
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
		table.setShowGrid(false);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setForeground(Color.white);
		table.setOpaque(false);
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBackground(new Color(0, 0, 0, 0.6f));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowSelectionAllowed(true);
		table.setFocusable(false);
		TableCellRenderer baseRenderer = table.getTableHeader().getDefaultRenderer();
		table.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(baseRenderer));
		table.setFont(new Font(table.getFont().getName(), table.getFont().getStyle(), 25));

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
				selectedColumn = table.getSelectedColumn();

				// Repaints JTable
				table.repaint();
			}
		});
		scrollPane.setViewportView(table);

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
		List<User_Lobby> userGames = new UserLobbyDao().selectByUser(user.getId());
		double points = 0;
		for (int i = 0; i < userGames.size(); ++i) {
			points += userGames.get(i).getPoints();
		}
		return points;
	}

	private int getRankOfUser() {
		int rank = -1;
		List<User_Lobby> userGames = new UserLobbyDao().getAllUserLobbies();
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
			if (ranks.get(i).getName().equals(user.getUsername()))
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
		name.setText(user.getUsername());
	}

	private void setTableData() {
		HistoryDao historyDao = new HistoryDao();
		List<History> histories = historyDao.selectByUser(user);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (histories.size() > 0) {
			for (History history : histories) {
				model.addRow(new Object[] { String.valueOf(history.getDate()), Integer.valueOf(history.getSpielerAnz()),
						Integer.valueOf(history.getPunkte()), Integer.valueOf(history.getRank()) });
			}
		}
	}
}
