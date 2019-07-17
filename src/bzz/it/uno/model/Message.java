package bzz.it.uno.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * DB Table "Message" representation
 * 
 * @author Severin Hersche
 *
 */
@Entity
@Table(name = "message")
public class Message {
	private int id;
	private String text;
	private User_Lobby userLobby;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Lob 
	@Column(name="text", length=512)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@ManyToOne(targetEntity = User_Lobby.class)
	public User_Lobby getUserLobby() {
		return userLobby;
	}
	public void setUserLobby(User_Lobby userLobby) {
		this.userLobby = userLobby;
	}
	
}
