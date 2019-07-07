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
import bzz.it.uno.frontend.SelectColorDialog;
import bzz.it.uno.frontend.UNODialog;
import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.Card;
import bzz.it.uno.model.CardType;

public class CardsDisplayController extends JFrame {

	private JPanel contentPane;
	private int xx, xy;
	private ImageCanvas imgCanvas;
	private OfflineGameController[] playersController;
	private int currentPlayer = 0;
	private int direction = 1;
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

	public boolean giveCard(OfflineGameController offlineGameController) {
		boolean response = false;
		if (offlineGameController == playersController[currentPlayer]) {
			offlineGameController.addCards(unoLogic.getCardsFromStack(1));
			if (!new UNODialog(this, "Weitergeben?", "Wills du noch eine Karte setzen", UNODialog.QUESTION,
					UNODialog.YES_NO_BUTTON).getReponse()) {
				nextPlayer();
			} else {
				response = true;
			}
		} else {
			new UNODialog(this, "Ungültig", "Sie sind nicht an der Reihe. Bitte warten.", UNODialog.WARNING,
					UNODialog.OK_BUTTON);
		}
		return response;
	}

	public boolean playCards(OfflineGameController offlineGameController, List<Card> cards) {
		if (offlineGameController == playersController[currentPlayer]) {
			if (unoLogic.playedCorrect(null, cards)) {
				unoLogic.playCards(null, cards);
				Card lastCard = cards.get(0);
				if(lastCard.getCardType() == CardType.BACK)
					switchDirection();
				else if(lastCard.getCardType() == CardType.SKIP)
					nextPlayer();
				else if(lastCard.getCardType() == CardType.CHANGECOLOR)
					lastCard.setColor(new SelectColorDialog(this).getColor());
				else if(lastCard.getCardType() == CardType.PLUSFOUR)
					lastCard.setColor(new SelectColorDialog(this).getColor());
				else if(lastCard.getCardType() == CardType.PLUSTWO)
					System.out.println("TODO");
				
				displayCurrentCard();
				nextPlayer();
				return true;
			} else {
				new UNODialog(this, "Ungültig", "Diese Eingabe ist nicht gültig. Bitte neu versuchen", UNODialog.ERROR,
						UNODialog.OK_BUTTON);
				return false;
			}
		} else {
			new UNODialog(this, "Ungültig", "Sie sind nicht an der Reihe. Bitte warten.", UNODialog.WARNING,
					UNODialog.OK_BUTTON);
			return false;
		}
	}

	public void nextPlayer() {
		if (playersController.length == currentPlayer + direction)
			currentPlayer = 0;
		else if(currentPlayer + direction == -1)
			currentPlayer = playersController.length -1;
		else
			currentPlayer += direction;
	}
	
	public void switchDirection() {
		direction *= -1;
	}

}
