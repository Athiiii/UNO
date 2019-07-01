package bzz.it.uno.frontend;

import javax.swing.ImageIcon;

public class RankModel implements Comparable<RankModel> {
	private String name;
	private Double points;
	private ImageIcon liga;

	public RankModel() {
		super();
	}

	public RankModel(String name, Double points, ImageIcon liga) {
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

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
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