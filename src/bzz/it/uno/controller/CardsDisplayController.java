package bzz.it.uno.controller;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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

/**
 * Main controller for offline UNO Version Display current Card
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class CardsDisplayController extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int xx, xy;
	private ImageCanvas imgCanvas;
	private OfflineGameController[] playersController;
	private int currentPlayer = 0;

	// needed for retour card
	private int direction = 1;

	// Manage UNO Rules
	private UNOBasicLogic unoLogic;

	// Take 1 Card is default
	private int takeCards = 1;

	public CardsDisplayController(int players) {
		unoLogic = new UNOBasicLogic();

		contentPane = new JPanel();
		ViewSettings.setupPanel(contentPane);
		ViewSettings.setupFrame(this);
		setBounds(100, 100, 150, 202);

		// define first card
		Card card = unoLogic.getCardsFromStack(1).get(0);
		unoLogic.playCards(null, Arrays.asList(card));

		// display first card
		imgCanvas = new ImageCanvas();
		displayCurrentCard();
		imgCanvas.setBounds(10, 10, 130, 182);
		contentPane.add(imgCanvas, BorderLayout.CENTER);

		// create players
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

	/**
	 * Used to update View of the current card
	 */
	private void displayCurrentCard() {
		imgCanvas.putImage(unoLogic.getLastPlayedCard().getFilename());
		imgCanvas.repaint();
	}

	/**
	 * gives a card from stack to user
	 * 
	 * @param offlineGameController
	 * @return boolean if players wants to continuee
	 */
	public boolean giveCard(OfflineGameController offlineGameController) {
		boolean response = false;
		if (offlineGameController == playersController[currentPlayer]) {
			if (takeCards == 0)
				takeCards = 1;
			offlineGameController.addCards(unoLogic.getCardsFromStack(takeCards));
			if (takeCards == 1) {
				if (!new UNODialog(this, "Weitergeben?", "Wills du noch eine Karte setzen", UNODialog.QUESTION,
						UNODialog.YES_NO_BUTTON).getReponse()) {
					nextPlayer();
				} else {
					response = true;
				}
			} else {
				// if player had to take more than 1 card he has not the possibility to continue
				nextPlayer();
			}
			takeCards = 0;
		} else {
			new UNODialog(this, "Ungültig", "Sie sind nicht an der Reihe. Bitte warten.", UNODialog.WARNING,
					UNODialog.OK_BUTTON);
		}
		return response;
	}

	/**
	 * Plays a card. Also checks all UNO Rules and arrange action for special cards
	 * 
	 * @param offlineGameController
	 * @param cards
	 * @return boolean - if successful
	 */
	public boolean playCards(OfflineGameController offlineGameController, List<Card> cards) {
		if (offlineGameController == playersController[currentPlayer]) {
			if (unoLogic.playedCorrect(null, cards)) {
				unoLogic.playCards(null, cards);
				Card lastCard = cards.get(0);

				// Feature of special cards
				if (lastCard.getCardType() == CardType.BACK) {
					// switch direction
					direction *= -1;
				} else if (lastCard.getCardType() == CardType.SKIP) {
					// affects that players(s) will be skiped
					for (int i = 0; i < cards.size(); ++i)
						nextPlayer();
				} else if (lastCard.getCardType() == CardType.CHANGECOLOR) {
					// displays view to choose color
					lastCard.setColor(new SelectColorDialog(this).getColor());
				} else if (lastCard.getCardType() == CardType.PLUSFOUR) {
					// displays view to choose color
					lastCard.setColor(new SelectColorDialog(this).getColor());

					// define how many cards the next player has to take
					for (int i = 0; i < cards.size(); ++i)
						takeCards += 4;
				} else if (lastCard.getCardType() == CardType.PLUSTWO) {
					// define how many cards the next player has to take
					for (int i = 0; i < cards.size(); ++i)
						takeCards += 2;
				}

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

	/**
	 * goes to next player. The direction will be considered
	 */
	public void nextPlayer() {
		if (playersController.length == currentPlayer + direction)
			currentPlayer = 0;
		else if (currentPlayer + direction == -1)
			currentPlayer = playersController.length - 1;
		else
			currentPlayer += direction;
	}
}
