package bzz.it.uno.frontend;

import javax.swing.ImageIcon;

/**
 * Model for the Ranking table list
 * 
 * @author Athavan Theivakulasingham
 */
public class RankModel implements Comparable<RankModel> {
	private String name;
	private Integer points;
	private ImageIcon liga;

	public RankModel() {
		super();
	}

	public RankModel(String name, Integer points, ImageIcon liga) {
		super();
		this.name = name;
		this.points = points;
		this.liga = liga;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public ImageIcon getLiga() {
		return liga;
	}

	public void setLiga(ImageIcon liga) {
		this.liga = liga;
	}

	@Override
	public int compareTo(RankModel o) {
		return this.getPoints().compareTo(o.getPoints());
	}
}