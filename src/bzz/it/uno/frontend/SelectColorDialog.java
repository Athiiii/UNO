package bzz.it.uno.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Dialog choose a color. [red, yellow, green, blue]
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class SelectColorDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private int xx, xy;
	private String color;

	public SelectColorDialog(JFrame parent) {
		super(parent, ModalityType.APPLICATION_MODAL);
		int size = 190;
		setUndecorated(true);
		setLocationRelativeTo(null);
		setLocation(100, 100);
		setIconImage(new ImageIcon(new ImageIcon(ViewSettings.class.getResource("/images/uno_logo.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH)).getImage());
		setSize(size, size);
		Dimension resoltion = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (resoltion.getWidth() / 2 - size / 2), (int) (resoltion.getHeight() / 2 - size / 2));
		ViewSettings.setupPanel(contentPanel);

		contentPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				SelectColorDialog.this.setLocation(x - xx, y - xy);
			}
		});
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});
		int gap = 4;
		int boxSize = ((size - (gap * 2)) / 2) - 2;
		JButton red = ViewSettings.createButton(gap, gap, boxSize, boxSize, new Color(245, 100, 98), "");
		red.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				color = "red";
				dispose();
			}
		});
		contentPanel.add(red);

		JButton blue = ViewSettings.createButton(size / 2, gap, boxSize, boxSize, new Color(0, 195, 229), "");
		blue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				color = "blue";
				dispose();
			}
		});
		contentPanel.add(blue);

		JButton green = ViewSettings.createButton(gap, size / 2, boxSize, boxSize, new Color(47, 226, 155), "");
		green.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				color = "green";
				dispose();
			}
		});
		contentPanel.add(green);

		JButton yellow = ViewSettings.createButton(size / 2, size / 2, boxSize, boxSize, new Color(247, 227, 89), "");
		yellow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				color = "yellow";
				dispose();
			}
		});
		contentPanel.add(yellow);

		getContentPane().add(contentPanel);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public String getColor() {
		return color;
	}
}
