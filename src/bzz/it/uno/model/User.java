package bzz.it.uno.model;

import java.util.List;

public class User {
	private List<Card> cards;
	private int cardAmount;
	private int points;
	private boolean isPlayed;

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public int getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(int cardAmount) {
		this.cardAmount = cardAmount;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isPlayed() {
		return isPlayed;
	}

	public void setPlayed(boolean isPlayed) {
		this.isPlayed = isPlayed;
	}
}
