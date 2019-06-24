package bzz.it.uno.model;

public class GameUser {
	private User user;
	private CardDeck userDeck;
	private int punkte;
	private boolean unoSagen = false;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CardDeck getUserDeck() {
		return userDeck;
	}

	public void setUserDeck(CardDeck userDeck) {
		this.userDeck = userDeck;
	}

	public int getPunkte() {
		return punkte;
	}

	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}

	public boolean isUnoSagen() {
		return unoSagen;
	}

	public void setUnoSagen(boolean unoSagen) {
		this.unoSagen = unoSagen;
	}
}
