package bzz.it.uno.backend;

import java.util.List;

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

	/**
	 * Checks all UNO Rules if setting the specified cards is valid Excluded is
	 * PLUSFOUR and PLUSTWO Cards
	 * 
	 * @param playingDeck
	 * @param settingCards
	 * @param user
	 * @return if playing card is valid
	 */
	public boolean checkRules(CardDeck playingDeck, List<Card> settingCards, GameUser user) {
		boolean result = true;
		// used to check the rules
		Card ruleCard = settingCards.get(0);
		// card on the top of the playedCards
		Card lastCard = playingDeck.getCards().get(playingDeck.getCards().size() - 1);
		if (settingCards.size() > 1) {
			// use filename because it should be the same for all played card
			String filename = settingCards.get(0).getFilename();
			for (int i = 0; i < settingCards.size(); ++i) {
				if (!filename.equals(settingCards.get(i).getFilename())) {
					result = false;
				}
			}
		} else {
			if (!(isSameColor(lastCard, ruleCard) || isSameNumber(lastCard, ruleCard))) {
				result = false;
			}
		}
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
		if (comingCard.getValue() == currentCard.getValue() && currentCard.getCardType() == comingCard.getCardType()
				&& currentCard.getCardType() == CardType.COMMON) {
			result = true;
		} else if (currentCard.getCardType() == comingCard.getCardType() && comingCard.getCardType() != CardType.COMMON) {
			result = true;
		} else if (comingCard.getCardType() == CardType.PLUSFOUR || comingCard.getCardType() == CardType.CHANGECOLOR) {
			result = true;
		}
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
		if (comingCard.getColor().equals(currentCard.getColor()))
			result = true;

		return result;
	}

}
