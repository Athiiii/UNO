package bzz.it.uno.controller;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bzz.it.uno.backend.UNOBasicLogic;
import bzz.it.uno.dao.HandleConnectionToDB;
import bzz.it.uno.frontend.ImageCanvas;
import bzz.it.uno.frontend.SelectColorDialog;
import bzz.it.uno.frontend.UNODialog;
import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.model.Card;
import bzz.it.uno.model.CardType;
import bzz.it.uno.model.Lobby;
import bzz.it.uno.model.User;

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
	private User user;
	private NavigationController navigationFrame;

	// needed for retour card
	private int direction = 1;

	// Manage UNO Rules
	private UNOBasicLogic unoLogic;

	// Take 1 Card is default
	private int takeCards = 1;

	public CardsDisplayController(User user, NavigationController navigationFrame, int players) {
		this.user = user;
		this.navigationFrame = navigationFrame;

		unoLogic = new UNOBasicLogic();

		contentPane = new JPanel();
		ViewSettings.setupPanel(contentPane);
		ViewSettings.setupFrame(this);
		setBounds(100, 100, 150, 202);

		// define first card
		Card card;
		do {
			card = unoLogic.getCardsFromStack(1).get(0);
			unoLogic.playCards(null, Arrays.asList(card));
		} while (card.getCardType() != CardType.COMMON);

		// display first card
		imgCanvas = new ImageCanvas();
		displayCurrentCard();
		imgCanvas.setBounds(10, 10, 130, 182);
		contentPane.add(imgCanvas, BorderLayout.CENTER);

		// create players
		playersController = new OfflineGameController[players];
		for (int i = 0; i < players; ++i) {
			playersController[i] = new OfflineGameController("Player " + (i + 1), this);
			playersController[i].addCards(unoLogic.getCardsFromStack(1));
			if (i == currentPlayer)
				playersController[i].setStatus(true);
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
	 * @return boolean if players wants to continue
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
					offlineGameController.setOnlyPlayingCards(false);
					response = false;
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
		boolean response = false;
		if (offlineGameController == playersController[currentPlayer]) {
			if (unoLogic.playedCorrect(null, cards)) {
				if (takeCards > 1
						&& cards.get(cards.size() - 1).getCardType() != unoLogic.getLastPlayedCard().getCardType()) {
					new UNODialog(this, "Ungültige Karte", "Sie dürfen diese Karte nicht setzen", UNODialog.WARNING,
							UNODialog.OK_BUTTON);
				} else {
					if (offlineGameController.getCards().size() != 1
							|| offlineGameController.isSayedUNO() && offlineGameController.isSayedUNOConfirm()) {
						unoLogic.playCards(null, cards);

						Card lastCard = cards.get(0);

						// Feature of special cards
						if (lastCard.getCardType() == CardType.BACK) {
							// switch direction
							direction *= -1;
						} else if (lastCard.getCardType() == CardType.SKIP) {
							// affects that players(s) will be skipped
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

						// confirm if player before has 1 card and sayed UNO
						int playerBefore = currentPlayer + (direction * -1);
						if (playerBefore == -1)
							playerBefore = playersController.length - 1;
						else if (playerBefore == playersController.length)
							playerBefore = 0;
						if (playersController[playerBefore].getCards().size() == 1
								&& playersController[playerBefore].isSayedUNO())
							playersController[playerBefore].setSayedUNOConfirm(true);
					}
					displayCurrentCard();
					if (offlineGameController.getCards().size() == 1 && offlineGameController.isSayedUNO()
							&& offlineGameController.isSayedUNOConfirm()) {
						playerWon();
					} else if (offlineGameController.isSayedUNO() && offlineGameController.getCards().size() == 1) {
						offlineGameController.addCards(unoLogic.getCardsFromStack(2));
						new UNODialog(this, "Ungültig", "Sie haben ungültig UNO gesagt. 2 Karten",
								UNODialog.INFORMATION, UNODialog.OK_BUTTON);
					} else if (!offlineGameController.isSayedUNO() && offlineGameController.getCards().size() == 1) {
						offlineGameController.addCards(unoLogic.getCardsFromStack(2));
						new UNODialog(this, "Ungültig", "Sie haben vergessen UNO zu sagen. 2 Karten",
								UNODialog.INFORMATION, UNODialog.OK_BUTTON);
					} else if (!offlineGameController.isSayedUNOConfirm()
							&& offlineGameController.getCards().size() == 1 && offlineGameController.isSayedUNO()) {
						offlineGameController.addCards(unoLogic.getCardsFromStack(2));
						new UNODialog(this, "Ungültig", "Sie haben zu spät UNO gesagt. 2 Karten", UNODialog.INFORMATION,
								UNODialog.OK_BUTTON);
					}
					nextPlayer();
					response = true;
				}
			} else {
				new UNODialog(this, "Ungültig", "Diese Eingabe ist nicht gültig. Bitte neu versuchen", UNODialog.ERROR,
						UNODialog.OK_BUTTON);
			}
		} else {
			new UNODialog(this, "Ungültig", "Sie sind nicht an der Reihe. Bitte warten.", UNODialog.WARNING,
					UNODialog.OK_BUTTON);
		}
		return response;
	}

	/**
	 * goes to next player. The direction will be considered
	 */
	public void nextPlayer() {
		if (playersController.length == currentPlayer + direction)
			currentPlayer = 0;
		else if (currentPlayer + direction <= -1)
			currentPlayer = playersController.length - 1;
		else
			currentPlayer += direction;

		for (int i = 0; i < playersController.length; ++i) {
			boolean status = false;
			if (currentPlayer == i)
				status = true;
			playersController[i].setStatus(status);
		}
	}

	/**
	 * Assign points to winner of the round and prepare next round
	 */
	public void playerWon() {
		// get points for winner
		int points = 0;
		for (int i = 0; i < playersController.length; ++i) {
			if (i != currentPlayer) {
				for (int j = 0; j < playersController[i].getCards().size(); ++j) {
					points += playersController[i].getCards().get(j).getValue();
				}
			}
		}
		playersController[currentPlayer].addPoints(points);

		if (playersController[currentPlayer].getPoints() >= 500) {
			new OfflineGameEnd(user, navigationFrame, playersController);
			dispose();
		} else {

			// preparation of next round
			unoLogic.reshuffleCards();
			for (int i = 0; i < playersController.length; ++i) {
				playersController[i].resetCards();
				playersController[i].addCards(unoLogic.getCardsFromStack(7));
			}

			// define first card
			Card card;
			do {
				card = unoLogic.getCardsFromStack(1).get(0);
				unoLogic.playCards(null, Arrays.asList(card));
			} while (card.getCardType() != CardType.COMMON);

			// display first card
			displayCurrentCard();

			// reset taking card to default
			takeCards = 1;
		}
	}
}
