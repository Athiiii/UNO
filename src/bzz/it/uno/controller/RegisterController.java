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
import bzz.it.uno.service.RegisterService;

/**
 * Create a new User Profile
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class RegisterController extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoginController frame;
	private int xAxis, yAxis;
	private JPasswordField passwordField;
	private JPasswordField repeatPasswordField;
	private TextField usernameField;

	/**
	 * This is called to register a new user. 
	 * @param frame
	 */
	public RegisterController(LoginController frame) {
		this.frame = frame;
		frame.setVisible(false);

		contentPane = new JPanel();

		ViewSettings.setupFrame(this);
		ViewSettings.setupPanel(contentPane);

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

		setContentPane(contentPane);

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

		JButton registerBtn = ViewSettings.createButton(243, 382, 169, 39, new Color(66, 164, 245), "Registrieren");
		registerBtn.addActionListener(this);
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
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(243, 74, 349, 57);
		contentPane.add(titleLabel);

		Label usernameLabel = new Label("Username");
		usernameLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		usernameLabel.setForeground(Color.WHITE);
		usernameLabel.setBounds(243, 195, 107, 39);
		contentPane.add(usernameLabel);

		Label passwordLabel = new Label("Password");
		passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		passwordLabel.setBounds(243, 256, 107, 39);
		passwordLabel.setForeground(Color.WHITE);
		contentPane.add(passwordLabel);

		// switch to login view
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
		alreadyAccount.setForeground(new Color(55, 145, 221));
		contentPane.add(alreadyAccount);
		contentPane.add(ViewSettings.createCloseButton(ViewSettings.WHITE));

		JLabel repeatPasswordLabel = new JLabel("<html>Passwort <br/> wiederholen</html>");
		repeatPasswordLabel.setForeground(Color.WHITE);
		repeatPasswordLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		repeatPasswordLabel.setBounds(243, 321, 131, 39);
		contentPane.add(repeatPasswordLabel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (new String(repeatPasswordField.getPassword()).equals(new String(passwordField.getPassword()))) {
		
			if (RegisterService.createUser(new String(passwordField.getPassword()), usernameField.getText())){
				// forward to NavigationController
				this.setVisible(false);
				new NavigationController(UserDao.getInstance().selectByUsername(usernameField.getText()), frame);
			} else {
				new UNODialog(this, "Register failed", "Register failed. Entered username is already given.",
						UNODialog.ERROR, UNODialog.OK_BUTTON);
				usernameField.setText("");
				passwordField.setText("");
				repeatPasswordField.setText("");
			}

		} else {
			new UNODialog(this, "Register failed", "Register failed. Entered passwords are different.", UNODialog.ERROR,
					UNODialog.OK_BUTTON);
			passwordField.setText("");
			repeatPasswordField.setText("");
		}
	}
}
