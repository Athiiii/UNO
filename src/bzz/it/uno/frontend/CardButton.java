package bzz.it.uno.frontend;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import bzz.it.uno.model.Card;

/**
 * Displays a card with some styling
 * 
 * @author Athavan Theivakulasingham
 *
 */
public class CardButton extends JButton {
	private static final long serialVersionUID = 1L;
	private Card card;

	/**
	 * If card is clicked or not.
	 */
	private boolean selected;

	/**
	 * Style Card display
	 * 
	 * @param filename how the card is saved in the library
	 * @param card
	 */
	public CardButton(String filename, Card card) {
		//define Image of Card as Button Content
		super(new ImageIcon(new ImageIcon(CardButton.class.getResource("/images/cards/small/" + filename)).getImage()
				.getScaledInstance(97, 136, Image.SCALE_SMOOTH)));
		setCard(card);

		setSize(97, 136);
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getBorder() instanceof TitledBorder) {
					setSelected(false);
					// remove border
					setBorder(BorderFactory.createEmptyBorder());
				} else {
					setSelected(true);
					// show border
					setBorder(BorderFactory.createTitledBorder(""));
				}
			}
		});
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
