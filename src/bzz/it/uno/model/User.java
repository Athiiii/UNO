package bzz.it.uno.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	private int id;
	private String username;
	private String password;
	private byte[] picture;
	private boolean computer;
	private List<User> friends;
	private List<History> histories;

	private List<User_Lobby> userLobbys;

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
	public boolean isComputer() {
		return computer;
	}

	public void setComputer(boolean computer) {
		this.computer = computer;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "USER_FRIEND", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "FRIEND_ID"))
	public List<User> getFriendList() {
		return friends;
	}

	public void setFriendList(List<User> friends) {
		this.friends = friends;
	}

	@OneToMany(mappedBy = "user", orphanRemoval = true)
	public List<User_Lobby> getUserLobby() {
		return userLobbys;
	}

	public void setUserLobby(List<User_Lobby> userLobbys) {
		this.userLobbys = userLobbys;
	}

	/**
	 * @return the histories
	 */
	@OneToMany(mappedBy = "user", orphanRemoval = true)
	public List<History> getHistories() {
		return histories;
	}

	/**
	 * @param histories the histories to set
	 */
	public void setHistories(List<History> histories) {
		this.histories = histories;
	}

}
