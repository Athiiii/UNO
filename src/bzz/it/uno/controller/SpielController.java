package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bzz.it.uno.model.User;
import javax.swing.JTextField;

public class SpielController extends JFrame {
	private User user;
	private JPanel contentPane;
	private int xy, xx;
	private NavigationController navigationFrame;

	public SpielController(User user, NavigationController navigationFrame) {
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
				SpielController.this.setLocation(x - xx, y - xy);
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
		backBtn.setIcon(new ImageIcon(new ImageIcon(LoginController.class.getResource("/images/back.png"))
				.getImage().getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
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
		
		Label titleLabel = new Label("Spiel erstellen");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(233, 16, 234, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);
	}
}