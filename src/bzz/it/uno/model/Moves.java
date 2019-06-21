package bzz.it.uno.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "moves")
public class Moves {
	private int id;
	private User_Lobby user_Lobby;
	private String playedCard;
}
