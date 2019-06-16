package bzz.it.uno.model;

public class Card {
	private int wert;
	private String farbe;
	private SpecialCards specialCards;

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

	public SpecialCards getSpecialCards() {
		return specialCards;
	}

	public void setSpecialCards(SpecialCards specialCards) {
		this.specialCards = specialCards;
	}
}
