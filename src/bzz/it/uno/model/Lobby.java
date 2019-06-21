package bzz.it.uno.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Lobby {
	private int id;
	private List<User_Lobby> userLobby;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@OneToMany(mappedBy = "user_Lobby", orphanRemoval = true)
	public List<User_Lobby> getUserLobby() {
		return userLobby;
	}
	public void setUserLobby(List<User_Lobby> userLobby) {
		this.userLobby = userLobby;
	}
}
