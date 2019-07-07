package bzz.it.uno.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.lang.reflect.Field;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Template for all Dialog Messages
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class UNODialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private int xx, xy;
	public static final String ERROR = "error", WARNING = "warning", INFORMATION = "info";

	/**
	 * Display a predefined dialog <br>
	 * messageType can be either:
	 * <li>ERROR</li>
	 * <li>WARNING</li>
	 * <li>INFORMATION</li> <br>
	 * Per default it is INFORMATION <br>
	 * <br>
	 * 
	 * @param parent
	 * @param message
	 * @param content
	 * @param messageType
	 */
	public UNODialog(JFrame parent, String message, String content, String messageType) {
		super(parent, ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(parent);
		setUndecorated(true);
		setLocation(100, 100);
		setIconImage(new ImageIcon(new ImageIcon(ViewSettings.class.getResource("/images/uno_logo.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH)).getImage());
		setBounds(parent.getX() + parent.getWidth() / 2 - 350 / 2, parent.getY() + parent.getHeight() / 2 - 200 / 2,
				350, 200);
		ViewSettings.setupPanel(contentPanel);

		contentPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				UNODialog.this.setLocation(x - xx, y - xy);
			}
		});
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});

		JButton closeBtn = ViewSettings.createCloseButton(ViewSettings.WHITE);
		closeBtn.setBounds(320, 0, 30, 30);
		closeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPanel.add(closeBtn);

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 50, 350, 100);
		panel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(panel);

		JLabel icon = new JLabel("");
		icon.setBounds(10, 60, 90, 90);
		icon.setIcon(new ImageIcon(new ImageIcon(UNODialog.class.getResource("/images/" + messageType + ".png"))
				.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH)));
		panel.add(icon, BorderLayout.WEST);

		JLabel topMessage = new JLabel(message);
		topMessage.setForeground(Color.WHITE);
		topMessage.setBounds(10, -10, 276, 45);
		topMessage.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 13));
		contentPanel.add(topMessage);

		getContentPane().add(contentPanel);

		JTextArea contentLabel = new JTextArea(content);
		contentLabel.setWrapStyleWord(true);
		contentLabel.setLineWrap(true);
		contentLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
		contentLabel.setEditable(false);
		contentLabel.setBackground(Color.DARK_GRAY);
		contentLabel.setHighlighter(null);
		contentLabel.setForeground(Color.WHITE);
		panel.add(contentLabel, BorderLayout.CENTER);

		JButton btnOk = new JButton("Ok");
		btnOk.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		btnOk.setBackground(new Color(32, 152, 209));
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setBorderPainted(false);
		btnOk.setFocusPainted(false);
		btnOk.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btnOk.setBackground(btnOk.getBackground().brighter());
			}

			public void mouseExited(MouseEvent evt) {
				btnOk.setBackground(new Color(32, 152, 209));
			}
		});
		btnOk.setBounds(241, 156, 90, 30);
		contentPanel.add(btnOk);

		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
