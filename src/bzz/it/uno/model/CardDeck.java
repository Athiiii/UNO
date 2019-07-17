package bzz.it.uno.model;

import java.util.List;

/**
 * Stack of Cards
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class CardDeck {
	List<Card> cards;

	public CardDeck(List<Card> cards) {
		this.cards = cards;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> card) {
		this.cards = card;
	}

	public void addCard(Card card) {
		this.cards.add(card);
	}

	public boolean removeCard(Card card) {
		return this.cards.remove(card);
	}
}
