package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import bzz.it.uno.dao.UserDao;
import bzz.it.uno.frontend.UNODialog;
import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.User;
import bzz.it.uno.service.LoginService;

/**
 * Login with user Credentials
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class LoginController extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int xx, xy;

	// user input fields
	private TextField usernameInput;
	private JPasswordField passwordField;

	/**
	 * Starting point. <br>
	 * Create view for login
	 */
	public LoginController() {
		contentPane = new JPanel();
		ViewSettings.setupFrame(this);
		ViewSettings.setupPanel(contentPane);

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
		setContentPane(contentPane);

		// panel for image
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 140, 233, 500);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel loginView = new JLabel("");
		loginView.setVerticalAlignment(SwingConstants.TOP);
		loginView.setIcon(new ImageIcon(new ImageIcon(LoginController.class.getResource("/images/uno_logo.png"))
				.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH)));
		panel.add(loginView);
		// set up login Button
		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		loginButton.setBackground(new Color(0, 153, 204));
		loginButton.addActionListener(this);
		loginButton.setBounds(243, 382, 142, 39);
		loginButton.setBorderPainted(false);
		loginButton.setFocusPainted(false);
		loginButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				loginButton.setBackground(loginButton.getBackground().brighter());
			}

			public void mouseExited(MouseEvent evt) {
				loginButton.setBackground(new Color(0, 153, 204));
			}
		});
		contentPane.add(loginButton);

		// set up fields
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 27));
		passwordField.setBounds(381, 301, 273, 39);
		contentPane.add(passwordField);

		usernameInput = new TextField();
		usernameInput.setFont(new Font("Dialog", Font.PLAIN, 27));
		usernameInput.setBounds(381, 195, 273, 39);
		contentPane.add(usernameInput);

		// set up labels
		Label titleLabel = new Label("UNO Login");
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		titleLabel.setBounds(243, 74, 306, 57);
		titleLabel.setForeground(Color.WHITE);
		contentPane.add(titleLabel);

		Label usernameLabel = new Label("Username");
		usernameLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		usernameLabel.setBounds(243, 195, 107, 39);
		usernameLabel.setForeground(Color.WHITE);
		contentPane.add(usernameLabel);

		Label passwordLabel = new Label("Password");
		passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		passwordLabel.setBounds(243, 301, 107, 39);
		passwordLabel.setForeground(Color.WHITE);
		contentPane.add(passwordLabel);

		// link for register
		JLabel missingAccount = new JLabel("Noch kein Konto?");
		missingAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// open register frame
				new RegisterController(LoginController.this);
			}
		});
		missingAccount.setFont(new Font("Tahoma", Font.PLAIN, 19));
		missingAccount.setBounds(501, 382, 153, 39);
		missingAccount.setForeground(new Color(55, 145, 221));
		contentPane.add(missingAccount);

		contentPane.add(ViewSettings.createCloseButton(ViewSettings.WHITE));
	}

	/**
	 * Login Process
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// validation
		User user = LoginService.login(usernameInput.getText(), new String(passwordField.getPassword()));
		if (user != null) {
			// forward to NavigationController
			this.setVisible(false);
			new NavigationController(user, this);
		} else {
			new UNODialog(this, "Login failed", "Login is failed. Invalid username or password", UNODialog.ERROR,
					UNODialog.OK_BUTTON);
			passwordField.setText("");
			usernameInput.setText("");
		}
	}

	/**
	 * define username input
	 * 
	 * @param username
	 */
	public void setUserName(String username) {
		this.usernameInput.setText(username);
		this.passwordField.setText("");
	}
}
