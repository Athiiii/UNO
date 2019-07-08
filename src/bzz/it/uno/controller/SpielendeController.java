package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.User;

/**
 * Display after game is finished
 * @author Athavan Theivakulasingham
 *
 */
public class SpielendeController extends JFrame {

	private User user;
	private JPanel contentPane;
	private int xy, xx;

	public SpielendeController(User user) {
		this.user = user;
		contentPane = new JPanel();

		ViewSettings.setupFrame(this);
		ViewSettings.setupPanel(contentPane);
		
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				SpielendeController.this.setLocation(x - xx, y - xy);
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
		contentPane.add(ViewSettings.createCloseButton(ViewSettings.WHITE));
	}
}
