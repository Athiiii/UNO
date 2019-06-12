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
	
public class StartController extends JFrame {

	private JPanel contentPane;
	private int xx, xy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartController frame = new StartController();
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
	public StartController() {		
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();

		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				StartController.this.setLocation(x - xx, y - xy);
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

		JLabel label_3 = new JLabel("");
		label_3.setVerticalAlignment(SwingConstants.TOP);
		label_3.setIcon(new ImageIcon(new ImageIcon(StartController.class.getResource("/images/login.png")).getImage()
				.getScaledInstance(195, 500, java.awt.Image.SCALE_SMOOTH)));
		panel.add(label_3);

		Button button = new Button("Login");
		button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		button.setBackground(new Color(0, 153, 204));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setBounds(243, 382, 142, 39);
		contentPane.add(button);

		TextField textField = new TextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 27));
		textField.setBounds(381, 301, 273, 39);
		contentPane.add(textField);

		TextField textField_1 = new TextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 27));
		textField_1.setBounds(381, 195, 273, 39);
		contentPane.add(textField_1);

		Label label = new Label("UNO Login");
		label.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		label.setBounds(243, 74, 306, 57);
		contentPane.add(label);

		Label label_1 = new Label("Username");
		label_1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		label_1.setBounds(243, 195, 107, 39);
		contentPane.add(label_1);

		Label label_2 = new Label("Password");
		label_2.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		label_2.setBounds(243, 301, 107, 39);
		contentPane.add(label_2);

		JLabel lblNewLabel = new JLabel("Noch kein Konto?");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new RegisterController(StartController.this);
			}
		});
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel.setBounds(501, 382, 153, 39);
		lblNewLabel.setForeground(Color.BLUE.darker());
		contentPane.add(lblNewLabel);

		JLabel lblX = new JLabel(" x");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		lblX.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblX.setBounds(632, 0, 52, 57);
		contentPane.add(lblX);
	}
}
