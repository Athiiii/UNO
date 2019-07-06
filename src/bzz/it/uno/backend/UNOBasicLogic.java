package bzz.it.uno.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import bzz.it.uno.model.Card;
import bzz.it.uno.model.CardDeck;
import bzz.it.uno.model.CardType;
import bzz.it.uno.model.GameUser;

/**
 * Interface Logic between RuleMgmt and GameView
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class UNOBasicLogic {
	private RuleManagement ruleManagement;

	// Cards deck of played cards
	private CardDeck playedCards;

	// Card deck for taking cards in the play
	private CardDeck cardStacks;

	public UNOBasicLogic() {
		ruleManagement = new RuleManagement();
		playedCards = new CardDeck(generateAllUnoCards());
		cardStacks = new CardDeck(new ArrayList<Card>());
	}

	/**
	 * <p>
	 * Checks if either someone won the current game or the whole game.<br>
	 * If includePointCheck if true it will check if someone reached <b>500
	 * points</b>.
	 * </p>
	 * 
	 * @param players
	 * @param includePointCheck
	 * @return
	 */
	public GameUser CheckIfSomeoneWon(List<GameUser> players, boolean includePointCheck) {
		GameUser user = null;

		for (GameUser gu : players) {
			if (gu.getUserDeck().getCards().size() == 0) {
				user = gu;
				if (includePointCheck && gu.getPunkte() >= 500) {
					user = gu;
					break;
				} else
					user = null;
			}
		}

		return user;
	}

	/**
	 * Uses the UNO Rules to check if playing card is valid
	 * 
	 * @param user
	 * @param card
	 * @return if playing card is valid
	 */
	public boolean playedCorrect(GameUser user, Card card) {
		return ruleManagement.checkRules(playedCards, card, user);
	}

	/**
	 * Takes a card from CardStack and gives it to the user
	 * 
	 * @param user
	 * @return Card from cardStack
	 */
	public Card takeCard(GameUser user) {
		Card card = cardStacks.getCards().get(cardStacks.getCards().size() - 1);
		user.getUserDeck().addCard(card);
		cardStacks.removeCard(card);
		return card;
	}

	/**
	 * Selects random User from list
	 * 
	 * @param users of the game
	 * @return random User from list
	 */
	public GameUser triggerUser(List<GameUser> users) {
		Random rand = new Random();
		return users.get(rand.nextInt(users.size()));
	}

	/**
	 * assign card to user
	 * 
	 * @param user
	 * @param card
	 */
	public void playCard(GameUser user, Card card) {
		if (user != null) {
			user.getUserDeck().removeCard(card);
		}
		playedCards.addCard(card);
	}

	/**
	 * 
	 * @return List of all shuffled Cards
	 */
	private List<Card> generateAllUnoCards() {
		List<Card> cards = new ArrayList<Card>();
		boolean firstTime = true;
		String[] colors = { "red", "green", "blue", "yellow", };
		for (int i = 0; i < 8; ++i) {
			// Common
			int color = i;
			if (firstTime) {
				// ZERO CARD
				cards.add(new Card(0, colors[i], CardType.COMMON));
				// PLUSFOUR
				cards.add(new Card(50, "", CardType.PLUSFOUR));
			} else {
				// COLORCHANGE
				cards.add(new Card(50, "", CardType.SKIP));
				color = i - 4;
			}
			// NUMBERS
			cards.add(new Card(1, colors[color], CardType.COMMON));
			cards.add(new Card(2, colors[color], CardType.COMMON));
			cards.add(new Card(3, colors[color], CardType.COMMON));
			cards.add(new Card(4, colors[color], CardType.COMMON));
			cards.add(new Card(5, colors[color], CardType.COMMON));
			cards.add(new Card(6, colors[color], CardType.COMMON));
			cards.add(new Card(7, colors[color], CardType.COMMON));
			cards.add(new Card(8, colors[color], CardType.COMMON));
			cards.add(new Card(9, colors[color], CardType.COMMON));

			// STOP
			cards.add(new Card(20, colors[color], CardType.SKIP));

			// RETOUR
			cards.add(new Card(20, colors[color], CardType.BACK));

			// PLUSTWO
			cards.add(new Card(20, colors[color], CardType.PLUSTWO));

			if (i == 3)
				firstTime = false;
		}

		// shuffle cards 3 times
		Collections.shuffle(cards);
		Collections.shuffle(cards);
		Collections.shuffle(cards);

		return cards;
	}

	/**
	 * Reshuffle card stack with played cards
	 */
	public void reshuffleCards() {
		Card currentCard = playedCards.getCards().get(0);
		cardStacks.getCards().addAll(playedCards.getCards());
		playedCards.setCards(Arrays.asList(currentCard));
	}

	/**
	 * Gives an amount of cards to users
	 * 
	 * @param users
	 */
	public void distributeCardsToAllPlayers(List<GameUser> users, int count) {
		for (int i = 0; i < count; ++i) {
			for (int j = 0; j < users.size(); ++j) {
				if (cardStacks.getCards().size() == 0)
					reshuffleCards();
				Card card = cardStacks.getCards().get(0);
				users.get(j).getUserDeck().addCard(card);
				cardStacks.removeCard(card);
			}
		}
	}

	/**
	 * Returns an amount of cards from the stack. It will be automatically removed
	 * from the stack.
	 * 
	 * @param count
	 * @return cards
	 */
	public List<Card> getCardsFromStack(int count) {
		List<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < count; ++i) {
			if (cardStacks.getCards().size() == 0)
				reshuffleCards();
			Card card = cardStacks.getCards().get(0);
			cards.add(card);
			cardStacks.removeCard(card);
		}
		return cards;
	}
}
