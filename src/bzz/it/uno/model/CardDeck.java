package bzz.it.uno.model;

import java.util.List;

/**
 * 
 * @author Athavan
 *
 */
public class CardDeck {
	List<Card> cards;
	
	public CardDeck(List<Card> cards) {
		this.cards = cards;
	}

	public List<Card> getCard() {
		return cards;
	}

	public void setCard(List<Card> card) {
		this.cards = card;
	}
	
	public void AddCard(Card card) {
		this.cards.add(card);
	}
}
