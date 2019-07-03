package bzz.it.uno.controller;

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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import bzz.it.uno.dao.HandleConnectionToDB;
import bzz.it.uno.dao.UserDao;
import bzz.it.uno.model.User;

/**
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class LoginController extends JFrame implements ActionListener {

	private JPanel contentPane;
	private int xx, xy;
	private TextField usernameInput;
	private JPasswordField passwordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HandleConnectionToDB.openDbFactory();
					LoginController frame = new LoginController();
					frame.setVisible(true);
					Runtime.getRuntime().addShutdownHook(new Thread() {
						@Override
						public void run() {
							HandleConnectionToDB.closeDbFactory();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					HandleConnectionToDB.closeDbFactory();
				}
			}
		});
	}

	public LoginController() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();

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

		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		loginButton.setBackground(new Color(0, 153, 204));
		loginButton.addActionListener(this);
		loginButton.setBounds(243, 382, 142, 39);
		loginButton.setBorderPainted(false);
		loginButton.setFocusPainted(false);
		loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				loginButton.setBackground(loginButton.getBackground().brighter());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				loginButton.setBackground(new Color(0, 153, 204));
			}
		});
		contentPane.add(loginButton);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 27));
		passwordField.setBounds(381, 301, 273, 39);
		contentPane.add(passwordField);

		usernameInput = new TextField();
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

		JButton closeWindow = new JButton("");
		closeWindow.setBounds(650, 0, 50, 50);
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
	}

	/**
	 * Login Process
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		User currentUser = UserDao.getInstance().selectByUsername(usernameInput.getText());

		if (currentUser != null && currentUser.getPassword().equals(passwordField.getText())) {
			// forward to NavigationController
			this.setVisible(false);
			new NavigationController(currentUser, this);
		} else {
			JOptionPane.showMessageDialog(this, "Login is failed. Invalid username or password", "Login failed", 0);
			passwordField.setText("");
			usernameInput.setText("");
		}
	}

	public void SetUserName(String username) {
		this.usernameInput.setText(username);
		this.passwordField.setText("");
	}
}
