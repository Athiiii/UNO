package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.glass.ui.View;

import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.User;

public class PlayerSearchController extends JFrame {

	private JPanel contentPane;
	private int xx, xy;
	
	public PlayerSearchController(User user, NavigationController naviationFrame) {
		contentPane = new JPanel();
		
		ViewSettings.setupFrame(this);
		ViewSettings.setupPanel(contentPane);
		
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				PlayerSearchController.this.setLocation(x - xx, y - xy);
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
		contentPane.add(ViewSettings.createReturnButton(this, naviationFrame));

		JLabel titleLabel = new JLabel("Spieler suchen");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(10, 54, 680, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);
	}
	
}
