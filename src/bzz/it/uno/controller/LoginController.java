package bzz.it.uno.controller;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
	
public class LoginController extends JFrame {

	private JPanel contentPane;
	private int xx, xy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginController frame = new LoginController();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginController() {		


		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				LoginController.this.setLocation(x - xx, y - xy);
			}
		});
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(38, 38, 38));
		panel.setBounds(0, 0, 195, 500);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel loginView = new JLabel("");
		loginView.setVerticalAlignment(SwingConstants.TOP);
		loginView.setIcon(new ImageIcon(new ImageIcon(LoginController.class.getResource("/images/login.png")).getImage()
				.getScaledInstance(195, 500, java.awt.Image.SCALE_SMOOTH)));
		panel.add(loginView);

		Button loginButton = new Button("Login");
		loginButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		loginButton.setBackground(new Color(0, 153, 204));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		loginButton.setBounds(243, 382, 142, 39);
		contentPane.add(loginButton);

		TextField passwordField = new TextField();
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 27));
		passwordField.setBounds(381, 301, 273, 39);
		contentPane.add(passwordField);

		TextField usernameInput = new TextField();
		usernameInput.setFont(new Font("Dialog", Font.PLAIN, 27));
		usernameInput.setBounds(381, 195, 273, 39);
		contentPane.add(usernameInput);

		Label titleLabel = new Label("UNO Login");
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		titleLabel.setBounds(243, 74, 306, 57);
		contentPane.add(titleLabel);

		Label usernameLabel = new Label("Username");
		usernameLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		usernameLabel.setBounds(243, 195, 107, 39);
		contentPane.add(usernameLabel);

		Label passwordLabel = new Label("Password");
		passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		passwordLabel.setBounds(243, 301, 107, 39);
		contentPane.add(passwordLabel);

		JLabel missingAccount = new JLabel("Noch kein Konto?");
		missingAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new RegisterController(LoginController.this);
			}
		});
		missingAccount.setFont(new Font("Tahoma", Font.PLAIN, 19));
		missingAccount.setBounds(501, 382, 153, 39);
		missingAccount.setForeground(Color.BLUE.darker());
		contentPane.add(missingAccount);

		JLabel closeWindow = new JLabel(" x");
		closeWindow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		closeWindow.setFont(new Font("Tahoma", Font.BOLD, 40));
		closeWindow.setBounds(632, 0, 52, 57);
		contentPane.add(closeWindow);
	}
}
