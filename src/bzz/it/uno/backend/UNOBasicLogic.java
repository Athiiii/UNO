package bzz.it.uno.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bzz.it.uno.model.Card;
import bzz.it.uno.model.CardDeck;
import bzz.it.uno.model.CardType;
import bzz.it.uno.model.GameUser;
import bzz.it.uno.model.User;

/**
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class UNOBasicLogic {
	private RuleManagement ruleManagement;
	private CardDeck playedCards;
	private CardDeck cardStacks;

	public UNOBasicLogic() {
		ruleManagement = new RuleManagement();
		playedCards = new CardDeck(generateAllUnoCards());
		cardStacks = new CardDeck(new ArrayList<Card>());
	}
	

	public GameUser CheckIfSomeoneWon(List<GameUser> players) {
		GameUser user = null;

		for (GameUser gu : players) {
			// TODO
		}

		return user;
	}
	
	public boolean playedCorrect() {
		
		return true;
	}
	
	public Card takeCard(GameUser user) {
		Card card = cardStacks.getCards().get(cardStacks.getCards().size() -1);
		user.getUserDeck().addCard(card);
		cardStacks.removeCard(card);
		return card;
	}
	
	public void playCard(GameUser user, Card card) {
		user.getUserDeck().removeCard(card);
		playedCards.addCard(card);
	}

	/**
	 * 
	 * @return List of all shuffled Cards
	 */
	private List<Card> generateAllUnoCards() {
		List<Card> cards = new ArrayList<Card>();
		boolean firstTime = true;
		String[] farben = { "rot", "orange", "gruen", "blau", };
		for (int i = 0; i < 8; ++i) {
			// Common
			int color = i;
			if (firstTime) {		
				//ZERO CARD
				cards.add(new Card(0, farben[i], CardType.COMMON));				
				//PLUSFOUR
				cards.add(new Card(50, "", CardType.PLUSFOUR));
			}else {
				//COLORCHANGE
				cards.add(new Card(50, "", CardType.EXPOSE));
				color = i - 4;
			}
			cards.add(new Card(1, farben[color], CardType.COMMON));
			cards.add(new Card(2, farben[color], CardType.COMMON));
			cards.add(new Card(3, farben[color], CardType.COMMON));
			cards.add(new Card(4, farben[color], CardType.COMMON));
			cards.add(new Card(5, farben[color], CardType.COMMON));
			cards.add(new Card(6, farben[color], CardType.COMMON));
			cards.add(new Card(7, farben[color], CardType.COMMON));
			cards.add(new Card(8, farben[color], CardType.COMMON));
			cards.add(new Card(9, farben[color], CardType.COMMON));

			//STOP
			cards.add(new Card(20, farben[color], CardType.EXPOSE));
			//RETOUR
			cards.add(new Card(20, farben[color], CardType.BACK));
			//PLUSTWO
			cards.add(new Card(20, farben[color], CardType.PLUSTWO));			
			if (i == 3)
				firstTime = false;
		}
		
		//shuffle 3 times
		Collections.shuffle(cards);
		Collections.shuffle(cards);
		Collections.shuffle(cards);
		
		return cards;
	}
	
	public void reshuffleCards() {
		Card currentCard = playedCards.getCards().get(0);
		cardStacks.getCards().addAll(playedCards.getCards());
		playedCards.setCards(Arrays.asList(currentCard));
	}
	
	public void distributeCardsToAllPlayers(List<GameUser> users) {
		for(int i = 0; i < 7; ++i) {
			for(int j = 0; j < users.size(); ++j) {
				Card card = cardStacks.getCards().get(0);
				users.get(j).getUserDeck().addCard(card);
				cardStacks.removeCard(card);
			}
		}
	}
	
	public void SetFirstCard() {
		Card card = cardStacks.getCards().get(0);
		playedCards.addCard(card);
		cardStacks.removeCard(card);
	}
}
