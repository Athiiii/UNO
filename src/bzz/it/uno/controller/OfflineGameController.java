package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.frontend.WrapLayout;

/**
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class OfflineGameController extends JFrame {
	private int xy, xx;
	private JPanel contentPane;

	public OfflineGameController(String userName) {
		contentPane = new JPanel();
		// ViewSettings.setupFrame(this);
		JFrame frame = this;
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 700, 500);
		frame.setVisible(true);
		frame.setIconImage(new ImageIcon(new ImageIcon(ViewSettings.class.getResource("/images/uno_logo.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)).getImage());
		// ViewSettings.setupPanel(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(11, 300, 11, 300));

		setBounds(100, 100, 400, 300);
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				OfflineGameController.this.setLocation(x - xx, y - xy);
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

		JLabel titleLabel = new JLabel(userName);

		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(10, 10, 680, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 92, 400, 175);
		panel.setLayout(new WrapLayout(FlowLayout.CENTER, 5, 5));

		for (int i = 0; i < 9; ++i) {
			JButton btnNewButton = new JButton(new ImageIcon(
					new ImageIcon(OfflineGameController.class.getResource("/images/cards/small/red_2.png")).getImage()
							.getScaledInstance(97, 136, Image.SCALE_SMOOTH)));
			btnNewButton.setSize(97, 136);
			btnNewButton.setBorder(BorderFactory.createEmptyBorder());
			btnNewButton.setContentAreaFilled(false);
			panel.add(btnNewButton);
		}		
		contentPane.add(ViewSettings.createDefaultScrollPane(panel, 175, 400, 92));
	}
}
