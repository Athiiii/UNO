package bzz.it.uno.controller;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bzz.it.uno.backend.UNOBasicLogic;
import bzz.it.uno.frontend.ImageCanvas;
import bzz.it.uno.frontend.UNODialog;
import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.Card;

public class CardsDisplayController extends JFrame {

	private JPanel contentPane;
	private int xx, xy;
	private ImageCanvas imgCanvas;
	private OfflineGameController[] playersController;
	private int currentPlayer = 0;
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
		unoLogic.playCards(null, Arrays.asList(card));

		imgCanvas = new ImageCanvas();
		displayCurrentCard();
		imgCanvas.setBounds(10, 10, 130, 182);
		contentPane.add(imgCanvas, BorderLayout.CENTER);

		playersController = new OfflineGameController[players];
		for (int i = 0; i < players; ++i) {
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

	private void displayCurrentCard() {
		imgCanvas.putImage(unoLogic.getLastPlayedCard().getFilename());
		imgCanvas.repaint();
	}

	public void giveCard(OfflineGameController offlineGameController) {
		if (offlineGameController == playersController[currentPlayer]) {
			offlineGameController.addCards(unoLogic.getCardsFromStack(1));
			nextPlayer();
		} else {
			new UNODialog(this, "Ungültig", "Sie sind nicht an der Reihe. Bitte warten.", UNODialog.WARNING);
		}
	}
	
	public void playCards(OfflineGameController offlineGameController, List<Card> cards) {
		if (offlineGameController == playersController[currentPlayer]) {
			unoLogic.playCards(null, cards);
			displayCurrentCard();
			nextPlayer();
		} else {
			new UNODialog(this, "Ungültig", "Sie sind nicht an der Reihe. Bitte warten.", UNODialog.WARNING);
		}
	}

	public void nextPlayer() {
		if (playersController.length == currentPlayer + 1)
			currentPlayer = 0;
		else
			++currentPlayer;
	}

}
