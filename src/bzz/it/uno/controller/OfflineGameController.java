package bzz.it.uno.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bzz.it.uno.frontend.CardButton;
import bzz.it.uno.frontend.UNODialog;
import bzz.it.uno.frontend.ViewSettings;
import bzz.it.uno.frontend.WrapLayout;
import bzz.it.uno.model.Card;

/**
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class OfflineGameController extends JFrame {
	private static final long serialVersionUID = 1L;
	private int xy, xx;
	private JPanel contentPane;
	private List<CardButton> cardBtns;
	private List<Card> cards;
	private JPanel cardPanel;
	private CardsDisplayController parent;
	private boolean onlyPlayingCards = false;
	private boolean sayedUNO = false;

	private Component displayCardComponent;

	private JLabel offlineIcon;
	private JLabel pointsLabel;

	private int points = 0;
	private String username;

	public OfflineGameController(String username, CardsDisplayController parent) {
		this.parent = parent;
		this.username = username;

		cardPanel = new JPanel();
		cardPanel.setBackground(Color.DARK_GRAY);
		cardPanel.setBounds(0, 110, 450, 290);
		cardPanel.setLayout(new WrapLayout(FlowLayout.CENTER, 5, 5));

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

		JLabel titleLabel = new JLabel(username);

		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(10, 10, 680, 69);
		titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 50));
		contentPane.add(titleLabel);

		JButton btnSetCard = ViewSettings.createButton(280, 10, 150, 40, new Color(76, 175, 80), "Karte(n) setzten");
		btnSetCard.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		btnSetCard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Card> cards = new ArrayList<Card>();
				for (int i = 0; i < cardBtns.size(); ++i) {
					if (cardBtns.get(i).isSelected())
						cards.add(cardBtns.get(i).getCard());
				}

				if (cards.size() != 0) {
					if (parent.playCards(OfflineGameController.this, cards)) {
						OfflineGameController.this.cards.removeAll(cards);
						onlyPlayingCards = false;
						updateView();
						sayedUNO = false;
					}
				} else {
					new UNODialog(parent, "Keine Auswahl", "Sie müssen min. 1 Karte auswählen", UNODialog.WARNING,
							UNODialog.OK_BUTTON);
				}
			}
		});
		contentPane.add(btnSetCard);

		JButton btnPullCard = ViewSettings.createButton(280, 60, 150, 40, new Color(255, 152, 0), "Karte ziehen");
		btnPullCard.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		btnPullCard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!onlyPlayingCards) {
					onlyPlayingCards = parent.giveCard(OfflineGameController.this);
				} else {
					if (new UNODialog(parent, "Unerlaubt", "Nicht erlaubt. Weitergeben?", UNODialog.QUESTION,
							UNODialog.YES_NO_BUTTON).getReponse()) {
						parent.nextPlayer();
						sayedUNO = false;
					}
				}
			}

		});
		contentPane.add(btnPullCard);

		offlineIcon = new JLabel(
				new ImageIcon(new ImageIcon(OfflineGameController.class.getResource("/images/green-dot.png")).getImage()
						.getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
		offlineIcon.setBounds(5, 5, 10, 10);
		offlineIcon.setVisible(false);
		contentPane.add(offlineIcon);

		pointsLabel = new JLabel(Integer.toString(points));
		pointsLabel.setForeground(Color.WHITE);
		pointsLabel.setBounds(15, 60, 680, 69);
		pointsLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
		contentPane.add(pointsLabel);

		JButton sayUno = ViewSettings.createButton(60, 80, 150, 30, new Color(33, 150, 243), "UNO Sagen");
		sayUno.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		sayUno.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sayedUNO = true;
			}

		});
		contentPane.add(sayUno);
	}

	public void setStatus(boolean online) {
		offlineIcon.setVisible(online);
	}

	public void addCards(List<Card> cards) {
		this.cards.addAll(0, cards);
		updateView();
	}

	private void updateView() {
		cardBtns = new ArrayList<CardButton>();
		cardPanel.removeAll();
		for (int i = 0; i < cards.size(); i++) {
			CardButton cardBtn = new CardButton(cards.get(i).getFilename(), cards.get(i));
			cardBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						if (parent.playCards(OfflineGameController.this, Arrays.asList(cardBtn.getCard()))) {
							OfflineGameController.this.cards.removeAll(Arrays.asList(cardBtn.getCard()));
							updateView();
							onlyPlayingCards = false;
						}
					}
				}
			});
			cardBtns.add(cardBtn);
			cardPanel.add(cardBtn);
		}
		if (displayCardComponent != null)
			contentPane.remove(displayCardComponent);
		displayCardComponent = ViewSettings.createDefaultScrollPane(cardPanel, 290, 450, 110);
		contentPane.add(displayCardComponent);
		contentPane.updateUI();
	}

	public void addPoints(int points) {
		this.points += points;
		updatePointsLabel();
	}

	public boolean wonByPoints() {
		return this.points >= 500;
	}

	public List<Card> getCards() {
		return this.cards;
	}

	public void resetCards() {
		this.cards = new ArrayList<Card>();
	}

	public int getPoints() {
		return this.points;
	}

	public String getUsername() {
		return this.username;
	}

	public void updatePointsLabel() {
		pointsLabel.setText(Integer.toString(points));
	}
	
	public void setOnlyPlayingCards(boolean onlyPlayingCards) {
		this.onlyPlayingCards = onlyPlayingCards;
	}

	public boolean isSayedUNO() {
		return sayedUNO;
	}
}
