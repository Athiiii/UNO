package bzz.it.uno.controller;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bzz.it.uno.backend.UNOBasicLogic;
import bzz.it.uno.frontend.ImageCanvas;
import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.Card;

public class CardsDisplayController extends JFrame {

	private JPanel contentPane;
	private int xx, xy;
	private ImageCanvas imgCanvas;
	private OfflineGameController[] playersController;
	private UNOBasicLogic unoLogic;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new CardsDisplayController(2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public CardsDisplayController(int players) {
		unoLogic = new UNOBasicLogic();
		contentPane = new JPanel();
		ViewSettings.setupPanel(contentPane);
		ViewSettings.setupFrame(this);
		setBounds(100, 100, 150, 202);
		
		Card card = unoLogic.getCardsFromStack(1).get(0);
		unoLogic.playCard(null, card);
		
		imgCanvas = new ImageCanvas();
		imgCanvas.putImage(card.getFilename());
		imgCanvas.setBounds(10, 10, 130, 182);
		contentPane.add(imgCanvas, BorderLayout.CENTER);
		
		playersController = new OfflineGameController[players];
		for(int i = 0; i < players; ++i) {
			playersController[i] = new OfflineGameController("Player " + (i + 1), this);
			playersController[i].addCards(unoLogic.getCardsFromStack(7));
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
	
	public void giveCard(OfflineGameController offlineGameController) {
		offlineGameController.addCards(unoLogic.getCardsFromStack(1));
	}

}
