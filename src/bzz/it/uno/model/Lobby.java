package bzz.it.uno.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "lobby")
public class Lobby {
	private int id;
	private List<User_Lobby> userLobby;
	private boolean status;
	private String name;
	
	public Lobby(boolean status, String name) {
		this.status = status;
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "status")
	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "userLobby", orphanRemoval = true)
	public List<User_Lobby> getUserLobby() {
		return userLobby;
	}

	public void setUserLobby(List<User_Lobby> userLobby) {
		this.userLobby = userLobby;
	}
}
