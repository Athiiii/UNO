package bzz.it.uno.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class Message {
	private int id;
	private String text;
	private User_Lobby userLobby;
}
