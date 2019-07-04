package bzz.it.uno.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Severin Hersche
 *
 */
@Entity
@Table(name = "history")
public class History {
	private int id;
	private LocalDate date;
	private int spielerAnz;
	private int punkte;
	private int rank;
	private User user;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the date
	 */
	@Column(name = "date")
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * @return the spielerAnz
	 */
	@Column(name = "spieler")
	public int getSpielerAnz() {
		return spielerAnz;
	}

	/**
	 * @param spielerAnz the spielerAnz to set
	 */
	public void setSpielerAnz(int spielerAnz) {
		this.spielerAnz = spielerAnz;
	}

	/**
	 * @return the punkte
	 */
	@Column(name = "punkte")
	public int getPunkte() {
		return punkte;
	}

	/**
	 * @param punkte the punkte to set
	 */
	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}

	/**
	 * @return the rank
	 */
	@Column(name = "rank")
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	@ManyToOne(targetEntity = User.class)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
