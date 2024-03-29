package bzz.it.uno.frontend;

/**
 * To find out in which liga a user were with how much points
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class Rank {
	static final String NOVIZE = "Novize.png";
	static final String BRONZE = "Bronze.png";
	static final String SILBER = "Silber.png";
	static final String GOLD = "Gold.png";
	static final String PLATIN = "Platin.png";
	static final String DIAMANT = "Diamant.png";
	static final String MASTER = "Master.png";
	static final String CHALLANGER = "Challanger.png";

	/**
	 * Getting the filename of the liga depending the points
	 * 
	 * @param points
	 * @return the filename of the liga Image
	 */
	public static String getRankImgByPoints(double points) {
		if (points >= 3000)
			return CHALLANGER;
		else if (points >= 2500)
			return MASTER;
		else if (points >= 2000)
			return DIAMANT;
		else if (points >= 1500)
			return PLATIN;
		else if (points >= 1000)
			return GOLD;
		else if (points >= 600)
			return SILBER;
		else if (points >= 300)
			return BRONZE;
		else
			return NOVIZE;
	}
}
