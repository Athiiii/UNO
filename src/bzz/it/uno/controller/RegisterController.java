package bzz.it.uno.controller;

import java.awt.BorderLayout;
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
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class RegisterController extends JFrame {

	private JPanel contentPane;
	private StartController frame;
	private int xAxis, yAxis;
	
	/**
	 * Create the frame.
	 */
	public RegisterController(StartController frame) {
		this.frame = frame;
		frame.setVisible(false);
		this.setUndecorated(true);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 742, 500);
		contentPane = new JPanel();

		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				RegisterController.this.setLocation(x - xAxis, y - yAxis);
			}
		});
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xAxis = e.getX();
				yAxis = e.getY();
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
		loginView.setIcon(new ImageIcon(new ImageIcon(StartController.class.getResource("/images/login.png")).getImage()
				.getScaledInstance(195, 500, java.awt.Image.SCALE_SMOOTH)));
		panel.add(loginView);

		Button registerBtn = new Button("Registrieren");
		registerBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		registerBtn.setBackground(new Color(0, 153, 204));
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		registerBtn.setBounds(243, 382, 142, 39);
		contentPane.add(registerBtn);

		TextField passwordField = new TextField();
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 27));
		passwordField.setBounds(398, 256, 273, 39);
		contentPane.add(passwordField);
		
		TextField repeatPasswordField = new TextField();
		repeatPasswordField.setFont(new Font("Dialog", Font.PLAIN, 27));
		repeatPasswordField.setBounds(398, 321, 273, 39);
		contentPane.add(repeatPasswordField);

		TextField usernameField = new TextField();
		usernameField.setFont(new Font("Dialog", Font.PLAIN, 27));
		usernameField.setBounds(398, 195, 273, 39);
		contentPane.add(usernameField);

		Label titleLabel = new Label("UNO Register");
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		titleLabel.setBounds(243, 74, 349, 57);
		contentPane.add(titleLabel);

		Label usernameLabel = new Label("Username");
		usernameLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		usernameLabel.setBounds(243, 195, 107, 39);
		contentPane.add(usernameLabel);

		Label passwordLabel = new Label("Password");
		passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		passwordLabel.setBounds(243, 256, 107, 39);
		contentPane.add(passwordLabel);

		JLabel alreadyAccount = new JLabel("Bereits ein Konto?");
		alreadyAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.setVisible(true);
				RegisterController.this.setVisible(false);
			}
		});
		alreadyAccount.setFont(new Font("Tahoma", Font.PLAIN, 19));
		alreadyAccount.setBounds(501, 382, 153, 39);
		alreadyAccount.setForeground(Color.BLUE.darker());
		contentPane.add(alreadyAccount);

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
		
		JLabel repeatPasswordLabel = new JLabel("<html>Passwort <br/> wiederholen</html>");
		repeatPasswordLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		repeatPasswordLabel.setBounds(243, 321, 131, 39);
		contentPane.add(repeatPasswordLabel);		
	}
}
