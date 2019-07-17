package bzz.it.uno.model;

/**
 * Representation of 1 UNO Card
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class Card {
	private int value;
	private String color;
	private CardType cardType;

	public Card() {

	}

	public Card(int value, String color, CardType cardType) {
		this.value = value;
		this.color = color;
		this.cardType = cardType;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	/**
	 * Generate the filename depending on the <b>color</b>, <b>number</b> and
	 * <b>type</b> of the card
	 * 
	 * @return the filename
	 */
	public String getFilename() {
		String name = "";
		if (getCardType() == CardType.COMMON) {
			name = getColor() + "_" + getValue() + ".png";
		} else if (getCardType() == CardType.PLUSFOUR) {
			name = "wild_pick_four.png";
		} else if (getCardType() == CardType.CHANGECOLOR) {
			name = "wild_color_changer.png";
		} else if (getCardType() == CardType.PLUSTWO) {
			name = getColor() + "_picker.png";
		} else if (getCardType() == CardType.BACK) {
			name = getColor() + "_reverse.png";
		} else if (getCardType() == CardType.SKIP) {
			name = getColor() + "_skip.png";
		}
		return name;
	}
}
