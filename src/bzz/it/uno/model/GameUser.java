package bzz.it.uno.model;

public class GameUser {
	private User user;
	private CardDeck userDeck;

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
}
