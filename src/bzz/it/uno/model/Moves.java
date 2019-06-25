package bzz.it.uno.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "moves")
public class Moves {
	private int id;
	private User_Lobby user_Lobby;
	private String playedCard;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ManyToOne(targetEntity = User_Lobby.class)
	public User_Lobby getUser_Lobby() {
		return user_Lobby;
	}
	public void setUser_Lobby(User_Lobby user_Lobby) {
		this.user_Lobby = user_Lobby;
	}
	@Column(name = "playedCard")
	public String getPlayedCard() {
		return playedCard;
	}
	public void setPlayedCard(String playedCard) {
		this.playedCard = playedCard;
	}
	
}
