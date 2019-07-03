package bzz.it.uno.backend;

import bzz.it.uno.model.Card;
import bzz.it.uno.model.CardDeck;
import bzz.it.uno.model.CardType;
import bzz.it.uno.model.GameUser;

/**
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class RuleManagement {

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
				&& comingCard.getWert() == currentCard.getWert())
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
	private boolean isSameColorr(Card currentCard, Card comingCard) {
		boolean result = false;
		if (currentCard.getCardType() == comingCard.getCardType() && currentCard.getCardType() == CardType.COMMON
				&& comingCard.getFarbe().equals(currentCard.getFarbe()))
			result = true;
		return result;
	}

}
