package bzz.it.uno.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class User_Lobby {
	private int id;
	private User user;
	private Lobby lobby;
	private double points;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ManyToOne(targetEntity = User.class)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@ManyToOne(targetEntity = Lobby.class)
	public Lobby getLobby() {
		return lobby;
	}
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}
	@Column(name = "points")
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
}
