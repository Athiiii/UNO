package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.frontend.WrapLayout;
import bzz.it.uno.model.Card;
import bzz.it.uno.model.CardType;

/**
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class OfflineGameController extends JFrame {
	private int xy, xx;
	private JPanel contentPane;
	private List<JButton> cardBtns;
	private List<Card> cards;
	private JPanel cardPanel;

	public OfflineGameController(String userName) {
		cardBtns = new ArrayList<JButton>();
		cards = new ArrayList<Card>();
		contentPane = new JPanel();
		// ViewSettings.setupFrame(this);
		JFrame frame = this;
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 700, 500);
		frame.setVisible(true);
		frame.setIconImage(new ImageIcon(new ImageIcon(ViewSettings.class.getResource("/images/uno_logo.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)).getImage());
		// ViewSettings.setupPanel(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(11, 300, 11, 300));

		setBounds(100, 100, 400, 300);
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				OfflineGameController.this.setLocation(x - xx, y - xy);
			}
		});
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});
		setContentPane(contentPane);

		JLabel titleLabel = new JLabel(userName);

		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(10, 10, 680, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);
	}

	public void addCards(List<Card> cards) {
		this.cards.addAll(cards);
		updateView();
	}

	private void updateView() {
		cardPanel = new JPanel();
		cardPanel.setBackground(Color.DARK_GRAY);
		cardPanel.setBounds(0, 92, 400, 175);
		cardPanel.setLayout(new WrapLayout(FlowLayout.CENTER, 5, 5));

		for (int i = 0; i < cards.size(); ++i) {
			JButton cardBtn = new JButton(new ImageIcon(new ImageIcon(
					OfflineGameController.class.getResource("/images/cards/small/" + cards.get(i).getFilename()))
							.getImage().getScaledInstance(97, 136, Image.SCALE_SMOOTH)));
			cardBtn.setSize(97, 136);
			cardBtn.setBorder(BorderFactory.createEmptyBorder());
			cardBtn.setContentAreaFilled(false);
			cardBtns.add(cardBtn);
			cardPanel.add(cardBtn);
		}
		contentPane.add(ViewSettings.createDefaultScrollPane(cardPanel, 175, 400, 92));
	}
}
