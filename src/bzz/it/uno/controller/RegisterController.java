package bzz.it.uno.controller;

import java.awt.Color;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import bzz.it.uno.dao.UserDao;
import bzz.it.uno.model.User;

/**
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class RegisterController extends JFrame implements ActionListener {

	private JPanel contentPane;
	private LoginController frame;
	private int xAxis, yAxis;
	private JPasswordField passwordField;
	private JPasswordField repeatPasswordField;
	private TextField usernameField;

	/**
	 * Create the frame.
	 */
	public RegisterController(LoginController frame) {
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

		setIconImage(new ImageIcon(new ImageIcon(LoginController.class.getResource("/images/uno_logo.png"))
				.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)).getImage());
		
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

		JButton registerBtn = new JButton("Registrieren");
		registerBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		registerBtn.setBackground(new Color(0, 153, 204));
		registerBtn.addActionListener(this);
		registerBtn.setBorderPainted(false);
		registerBtn.setFocusPainted(false);
		registerBtn.setBounds(243, 382, 169, 39);
		registerBtn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				registerBtn.setBackground(registerBtn.getBackground().brighter());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				registerBtn.setBackground(new Color(0, 153, 204));
			}
		});
		contentPane.add(registerBtn);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 27));
		passwordField.setBounds(398, 256, 273, 39);
		contentPane.add(passwordField);

		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setFont(new Font("Dialog", Font.PLAIN, 27));
		repeatPasswordField.setBounds(398, 321, 273, 39);
		contentPane.add(repeatPasswordField);

		usernameField = new TextField();
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

		JButton closeWindow = new JButton("");
		closeWindow.setBounds(692, 0, 50, 50);
		closeWindow.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		closeWindow.setBackground(Color.WHITE);
		closeWindow.setIcon(new ImageIcon(new ImageIcon(LoginController.class.getResource("/images/closeDark.png"))
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
				closeWindow.setBackground(closeWindow.getBackground().darker());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				closeWindow.setBackground(Color.WHITE);
			}
		});
		contentPane.add(closeWindow);

		JLabel repeatPasswordLabel = new JLabel("<html>Passwort <br/> wiederholen</html>");
		repeatPasswordLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		repeatPasswordLabel.setBounds(243, 321, 131, 39);
		contentPane.add(repeatPasswordLabel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (repeatPasswordField.getText().equals(passwordField.getText())) {
			UserDao dao = UserDao.getInstance();
			User user = dao.selectByUsername(usernameField.getText());
			if (user == null) {
				user = new User();
				user.setComputer(false);
				user.setPassword(passwordField.getText());
				user.setUsername(usernameField.getText());
				dao.addUser(user);

				// forward to NavigationController
				this.setVisible(false);
				new NavigationController(user, frame);
			} else {
				JOptionPane.showMessageDialog(this, "Register failed. Entered username is already given",
						"Register failed", 0);
				usernameField.setText("");
				passwordField.setText("");
				repeatPasswordField.setText("");
			}

		} else {
			JOptionPane.showMessageDialog(this, "Register failed. Entered passwords are different.", "Register failed",
					0);
			passwordField.setText("");
			repeatPasswordField.setText("");
		}
		/*
		 * - check if this user already exists - add user to db - forward to navigation
		 */
	}
}
