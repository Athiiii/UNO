package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import bzz.it.uno.model.History;
import bzz.it.uno.model.User;

public class ProfilController extends JFrame {

	private User user;
	private JPanel contentPane;
	private int xy, xx;
	private NavigationController navigationFrame;
	private JTable table;

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

		JPanel profileImage = new JPanel();
		profileImage.setBounds(23, 61, 127, 128);
		contentPane.add(profileImage);

		JButton btnDelete = new JButton("L\u00F6schen");
		btnDelete.setBackground(Color.BLACK);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnDelete.setBounds(557, 446, 120, 23);
		contentPane.add(btnDelete);

		JButton friends = new JButton("Freunde");
		friends.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		friends.setFont(new Font("Tahoma", Font.PLAIN, 17));
		friends.setBackground(Color.BLACK);
		friends.setBounds(23, 449, 104, 23);
		contentPane.add(friends);

		JButton btnEdit = new JButton("Bearbeiten");
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnEdit.setBackground(Color.BLACK);
		btnEdit.setBounds(429, 446, 118, 23);
		contentPane.add(btnEdit);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 234, 654, 189);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Gespielt", "Spieler", "Punkte", "Rank" }) {
					Class[] columnTypes = new Class[] { String.class, Integer.class, Integer.class, Integer.class };

					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				});
		scrollPane.setViewportView(table);
		setTableData();
	}

	private void setTableData() {
		List<History> histories = user.getHistories();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (History history : histories) {

			model.addRow(new Object[] { String.valueOf(history.getDate()), Integer.valueOf(history.getSpielerAnz()),
					Integer.valueOf(history.getPunkte()), Integer.valueOf(history.getRank()) });
		}

	}
}
