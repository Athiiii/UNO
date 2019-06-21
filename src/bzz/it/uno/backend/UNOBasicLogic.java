package bzz.it.uno.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bzz.it.uno.model.Card;
import bzz.it.uno.model.CardDeck;
import bzz.it.uno.model.CardType;
import bzz.it.uno.model.GameUser;
import bzz.it.uno.model.User;

/**
 * 
 * @author Athavan
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

}
