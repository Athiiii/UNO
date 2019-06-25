package bzz.it.uno.model;

/**
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class Card {
	private int wert;
	private String farbe;
	private CardType cardType;
	
	public Card() {
		
	}

	public Card(int wert, String farbe, CardType cardType) {
		this.wert = wert;
		this.farbe = farbe;
		this.cardType = cardType;
	}

	public int getWert() {
		return wert;
	}

	public void setWert(int wert) {
		this.wert = wert;
	}

	public String getFarbe() {
		return farbe;
	}

	public void setFarbe(String farbe) {
		this.farbe = farbe;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}	
}
