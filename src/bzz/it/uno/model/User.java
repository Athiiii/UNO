package bzz.it.uno.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	private int id;
	private String username;
	private String password;
	private byte[] picture;
	private boolean coputer;
	private FriendList friendList;
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

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "picture")
	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	@Column(name = "computer")
	public boolean isCoputer() {
		return coputer;
	}

	public void setCoputer(boolean coputer) {
		this.coputer = coputer;
	}

	@OneToOne
	@JoinColumn(name="friend")
	public FriendList getFriendList() {
		return friendList;
	}

	public void setFriendList(FriendList friendList) {
		this.friendList = friendList;
	}

	@OneToMany(mappedBy = "user_Lobby", orphanRemoval = true)
	public List<User_Lobby> getUserLobby() {
		return userLobby;
	}

	public void setUserLobby(List<User_Lobby> userLobby) {
		this.userLobby = userLobby;
	}

}
