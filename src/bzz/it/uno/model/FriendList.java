package bzz.it.uno.model;

import javax.persistence.OneToOne;

public class FriendList {
	private int id;
	private int friendId;
	private User user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFriendId() {
		return friendId;
	}
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	@OneToOne(mappedBy = "user", optional = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
