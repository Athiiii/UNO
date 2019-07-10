package bzz.it.uno.frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import bzz.it.uno.controller.GameController;

public class ChangePasswordDialog extends JDialog {
	private String password;
	private final JPanel contentPanel = new JPanel();
	private int xx, xy;

	public static void main(String[] args) {
		new ChangePasswordDialog(new GameController(null, null));
	}
	
	public ChangePasswordDialog(JFrame parent) {
		super(parent, ModalityType.APPLICATION_MODAL);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon(new ImageIcon(ViewSettings.class.getResource("/images/uno_logo.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH)).getImage());
		setBounds(parent.getX() + parent.getWidth() / 2 - 400 / 2, parent.getY() + parent.getHeight() / 2 - 270 / 2,
				450, 250);

		ViewSettings.setupPanel(contentPanel);

		JButton closeBtn = ViewSettings.createCloseButton(ViewSettings.WHITE);
		closeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		closeBtn.setBounds(420, 0, 30, 30);
		contentPanel.add(closeBtn);

		contentPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				ChangePasswordDialog.this.setLocation(x - xx, y - xy);
			}
		});
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});
		contentPanel.setLayout(null);

		JLabel passwordLabel = new JLabel("Neues Passwort eingeben:");
		passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setBounds(10, 71, 250, 56);
		contentPanel.add(passwordLabel);

		JLabel repeatPasswordLabel = new JLabel("Neues Passwort wiederholen:");
		repeatPasswordLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		repeatPasswordLabel.setForeground(Color.WHITE);
		repeatPasswordLabel.setBounds(10, 136, 250, 56);
		contentPanel.add(repeatPasswordLabel);

		// set up labels
		Label titleLabel = new Label("Neues Passwort");
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 40));
		titleLabel.setBounds(10, 20, 401, 56);
		titleLabel.setForeground(Color.WHITE);
		contentPanel.add(titleLabel);

		JPasswordField passwordField = new JPasswordField(10);
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 27));
		passwordField.setBounds(243, 80, 190, 39);
		contentPanel.add(passwordField);
		
		JPasswordField passwordFieldSecond = new JPasswordField(10);
		passwordFieldSecond.setFont(new Font("Dialog", Font.PLAIN, 27));
		passwordFieldSecond.setBounds(243, 145, 190, 39);
		contentPanel.add(passwordFieldSecond);
		
		JButton okBtn = ViewSettings.createButton(240, 210, 70, 30, new Color(204, 0, 0), "OK");
		okBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (passwordField.getPassword().equals("")) {
					new UNODialog(parent, "Fehler", "Passwort darf nicht leer sein", UNODialog.WARNING, UNODialog.OK_BUTTON);
				} else if (new String(passwordField.getPassword())
						.equals(new String(passwordFieldSecond.getPassword()))) {
					setPassword(new String(passwordField.getPassword()));
					dispose();
				} else {
					new UNODialog(parent, "Fehler", "Bitte gib 2 mal das gleiche Passwort ein!", UNODialog.WARNING,
							UNODialog.OK_BUTTON);
					passwordField.setText("");
					passwordFieldSecond.setText("");
				}
			}
		});
		contentPanel.add(okBtn);

		JButton cancelBtn = ViewSettings.createButton(320, 210, 110, 30, new Color(136, 136, 136), "Cancel");
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setPassword("");
				dispose();
			}
		});
		contentPanel.add(cancelBtn);

		if (new String(passwordField.getPassword()).equals(new String(passwordFieldSecond.getPassword()))) {
			setPassword(new String(passwordField.getPassword()));
		} else {
			new UNODialog(parent, "Error", "", "Bitte gib 2 mal das gleiche Passwort ein!", UNODialog.OK_BUTTON);
		}

		getContentPane().add(contentPanel);

		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
