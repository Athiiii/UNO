package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import bzz.it.uno.frontend.CardButton;
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
	private List<CardButton> cardBtns;
	private List<Card> cards;
	private JPanel cardPanel;

	public OfflineGameController(String userName) {
		cardBtns = new ArrayList<CardButton>();
		cards = new ArrayList<Card>();
		contentPane = new JPanel();
		// ViewSettings.setupFrame(this);
		JFrame frame = this;
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 400);
		frame.setVisible(true);
		frame.setIconImage(new ImageIcon(new ImageIcon(ViewSettings.class.getResource("/images/uno_logo.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)).getImage());
		// ViewSettings.setupPanel(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(11, 300, 11, 300));

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
		
		JButton btnKartenSetzten = new JButton("Karte(n) setzten");
		btnKartenSetzten.setBounds(280, 10, 150, 40);
		contentPane.add(btnKartenSetzten);
		
		JButton btnKarteZiehen = new JButton("Karte ziehen");
		btnKarteZiehen.setBounds(280, 60, 150, 40);
		contentPane.add(btnKarteZiehen);
	}

	public void addCards(List<Card> cards) {
		this.cards.addAll(cards);
		updateView();
	}

	private void updateView() {
		cardPanel = new JPanel();
		cardPanel.setBackground(Color.DARK_GRAY);
		cardPanel.setBounds(0, 100, 450, 300);
		cardPanel.setLayout(new WrapLayout(FlowLayout.CENTER, 5, 5));
		cardBtns = new ArrayList<CardButton>();

		for (int i = 0; i < cards.size(); i++) {
			CardButton cardBtn = new CardButton(cards.get(i).getFilename(), cards.get(i));
			cardBtns.add(cardBtn);
			cardPanel.add(cardBtn);
		}
		contentPane.add(ViewSettings.createDefaultScrollPane(cardPanel, 300, 450, 100));
	}
}
