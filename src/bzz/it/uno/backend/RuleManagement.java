package bzz.it.uno.backend;

import bzz.it.uno.model.Card;
import bzz.it.uno.model.CardDeck;
import bzz.it.uno.model.CardType;
import bzz.it.uno.model.GameUser;

/**
 * Controls the different Card Rules
 * 
 * @author Athavan Theivakulasingham
 *
 */
class RuleManagement {

	public boolean checkRules(CardDeck playingDeck, Card settingCard, GameUser user) {
		boolean result = true;

		// Sayed UNO?

		return result;
	}

	/**
	 * Is checking the numbers of the cards The cardType will be checked if it is
	 * common
	 * 
	 * @param currentCard
	 * @param comingCard
	 * @return if both cards have the same number
	 */
	private boolean isSameNumber(Card currentCard, Card comingCard) {
		boolean result = false;
		if (currentCard.getCardType() == comingCard.getCardType() && currentCard.getCardType() == CardType.COMMON
				&& comingCard.getValue() == currentCard.getValue())
			result = true;
		return result;
	}

	/**
	 * Is checking the color of the cards The cardType will be checked if it is
	 * common
	 * 
	 * @param currentCard
	 * @param comingCard
	 * @return if both cards have the same number
	 */
	private boolean isSameColor(Card currentCard, Card comingCard) {
		boolean result = false;
		if (currentCard.getCardType() == comingCard.getCardType() && currentCard.getCardType() == CardType.COMMON
				&& comingCard.getColor().equals(currentCard.getColor()))
			result = true;
		return result;
	}

}
