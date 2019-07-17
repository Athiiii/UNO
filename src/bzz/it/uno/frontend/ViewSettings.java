package bzz.it.uno.frontend;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Setting default settings from Swing Components
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class ViewSettings {
	public static final int WHITE = 1, BLACK = 2;
	private static Color color = null;

	/**
	 * <p>
	 * Setting up default settings for main JPanel
	 * </p>
	 * 
	 * @param contentPane
	 */
	public static void setupPanel(JPanel contentPane) {
		contentPane.setLayout(null);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(11, 300, 11, 300));
	}

	/**
	 * <p>
	 * Setting up default settings for JFrame
	 * </p>
	 * 
	 * @param frame
	 */
	public static void setupFrame(JFrame frame) {
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 700, 500);
		frame.setVisible(true);
		frame.setIconImage(new ImageIcon(new ImageIcon(ViewSettings.class.getResource("/images/uno_logo.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)).getImage());
	}

	/**
	 * <p>
	 * Creates a Closebutton<br>
	 * <li><b>ViewSettings.BLACK</b> as Parameter gives a black Button</li>
	 * <li><b>ViewSettings.WHITE</b> as Parameter gives a white Button</li> If
	 * parameter is invalid a black Button would be provided
	 * </p>
	 * 
	 * @param color
	 * @return a close Button
	 */
	public static JButton createCloseButton(int colorId) {
		String imgPath = "";
		if (colorId == 1) {
			imgPath = "/images/closeWhite.png";
			color = Color.DARK_GRAY;
		} else {
			imgPath = "/images/closeDark.png";
			color = Color.WHITE;
		}
		JButton closeWindow = new JButton("");
		closeWindow.setBounds(653, 0, 50, 50);
		closeWindow.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		closeWindow.setBackground(color);
		closeWindow.setIcon(new ImageIcon(new ImageIcon(ViewSettings.class.getResource(imgPath)).getImage()
				.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		closeWindow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Exit Program
				System.exit(0);
			}
		});
		closeWindow.setBorderPainted(false);
		closeWindow.setFocusPainted(false);
		closeWindow.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				// light background
				closeWindow.setBackground(closeWindow.getBackground().brighter());
			}

			public void mouseExited(MouseEvent evt) {
				// set back the background
				closeWindow.setBackground(color);
			}
		});
		return closeWindow;
	}

	/**
	 * creates a return Button with some styling
	 * 
	 * @param from
	 *            Frame
	 * @param to
	 *            Frame
	 * @return return Button
	 */
	public static JButton createReturnButton(JFrame from, JFrame to) {
		JButton backBtn = new JButton(" Zur\u00FCck");
		backBtn.setForeground(Color.WHITE);
		backBtn.setBounds(0, 0, 127, 50);
		backBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		backBtn.setBackground(Color.DARK_GRAY);
		backBtn.setIcon(new ImageIcon(new ImageIcon(ViewSettings.class.getResource("/images/back.png")).getImage()
				.getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				from.dispose();
				to.setVisible(true);
			}
		});
		backBtn.setBorderPainted(false);
		backBtn.setFocusPainted(false);
		backBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				// light background
				backBtn.setBackground(backBtn.getBackground().brighter());
			}

			public void mouseExited(MouseEvent evt) {
				// set back the background
				backBtn.setBackground(Color.DARK_GRAY);
			}
		});
		return backBtn;
	}

	/**
	 * Creating a default Scrollpane with styling
	 * 
	 * @param view
	 *            which should be in it
	 * @return designed Scrollpane
	 */
	public static JScrollPane createDefaultScrollPane(Component view, int height, int width, int yCord) {
		JScrollPane scrollPane = new JScrollPane(view);
		scrollPane.getVerticalScrollBar().setBackground(Color.DARK_GRAY.brighter());
		scrollPane.setBounds(0, yCord, width, height);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Color.BLACK;
			}

			@Override
			protected JButton createDecreaseButton(int orientation) {
				return createEmptyButton();
			}

			@Override
			protected JButton createIncreaseButton(int orientation) {
				return createEmptyButton();
			}

			private JButton createEmptyButton() {
				JButton arrowBtn = new JButton();
				arrowBtn.setPreferredSize(new Dimension(0, 0));
				arrowBtn.setMinimumSize(new Dimension(0, 0));
				arrowBtn.setMaximumSize(new Dimension(0, 0));
				return arrowBtn;
			}
		});
		return scrollPane;
	}

	/**
	 * define some default tabel styling
	 * 
	 * @param table
	 * @return designed JTabel
	 */
	public static JTable setupTableDesign(JTable table) {
		table.setShowGrid(false);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setForeground(Color.white);
		table.setOpaque(false);
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBackground(new Color(0, 0, 0, 0.6f));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowSelectionAllowed(true);
		table.setFocusable(false);
		table.setFont(new Font(table.getFont().getName(), table.getFont().getStyle(), 25));
		return table;
	}

	public static JButton createButton(int x, int y, int width, int height, Color color, String text) {
		JButton btn = new JButton(text);
		btn.setBounds(x, y, width, height);
		btn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		btn.setBackground(color);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btn.setBackground(btn.getBackground().brighter());
			}

			public void mouseExited(MouseEvent evt) {
				btn.setBackground(color);
			}
		});
		return btn;
	}
}
