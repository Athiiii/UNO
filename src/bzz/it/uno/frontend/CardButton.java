package bzz.it.uno.frontend;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import bzz.it.uno.controller.OfflineGameController;
import bzz.it.uno.model.Card;

public class CardButton extends JButton {

	Card card;
	
	public CardButton(String filename, Card card) {
		super(new ImageIcon(new ImageIcon(
				CardButton.class.getResource("/images/cards/small/" + filename))
				.getImage().getScaledInstance(97, 136, Image.SCALE_SMOOTH)));
		this.card = card;
		
		setSize(97, 136);
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getBorder() instanceof TitledBorder) {
					setBorder(BorderFactory.createEmptyBorder());
				}else {
					setBorder(BorderFactory.createTitledBorder(""));
				}
			}
		});
	}
}
