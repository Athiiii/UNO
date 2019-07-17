package bzz.it.uno.frontend;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Canvas to display Cards
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class ImageCanvas extends Canvas {

	private static final long serialVersionUID = 1L;
	private BufferedImage img;

	public void putImage(String filename) {
		try {
			img = ImageIO.read(ViewSettings.class.getResource("/images/cards/small/" + filename));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return img == null ? new Dimension(200, 200) : new Dimension(img.getWidth(), img.getHeight());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (img != null) {
			int x = (getWidth() - img.getWidth()) / 2;
			int y = (getHeight() - img.getHeight()) / 2;
			g.drawImage(img, x, y, this);
		}
	}

}