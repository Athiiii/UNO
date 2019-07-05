package bzz.it.uno.controller;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.prism.paint.ImagePattern;

import bzz.it.uno.backend.UNOBasicLogic;
import bzz.it.uno.frontend.ImageCanvas;
import bzz.it.uno.frontend.ViewSettings;

public class CardsDisplayController extends JFrame {

	private JPanel contentPane;
	private int xx, xy;
	private ImageCanvas imgCanvas;
	private OfflineGameController[] playersController;
	private UNOBasicLogic unoLogic;
	
	public CardsDisplayController(int players) {
		unoLogic = new UNOBasicLogic();
		contentPane = new JPanel();
		ViewSettings.setupPanel(contentPane);
		ViewSettings.setupFrame(this);
		setBounds(100, 100, 150, 202);
		
		imgCanvas = new ImageCanvas();
		imgCanvas.putImage("card_back_alt.png");
		imgCanvas.setBounds(10, 10, 130, 182);
		contentPane.add(imgCanvas, BorderLayout.CENTER);
		
		playersController = new OfflineGameController[players];
		for(int i = 0; i < players; ++i) {
			playersController[i] = new OfflineGameController("Player " + (i + 1));
		}
		
		imgCanvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				CardsDisplayController.this.setLocation(x - xx, y - xy);
			}
		});
		imgCanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});	
		
		setContentPane(contentPane);
	}
	
	public void SetImage(String name) {
		imgCanvas.putImage(name);
	}

}
