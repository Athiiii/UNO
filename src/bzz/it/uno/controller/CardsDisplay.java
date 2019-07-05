package bzz.it.uno.controller;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bzz.it.uno.frontend.ViewSettings;
import java.awt.Canvas;

public class CardsDisplay extends JFrame {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardsDisplay frame = new CardsDisplay();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private JPanel contentPane;

	public CardsDisplay() {
		ViewSettings.setupPanel(contentPane);
		ViewSettings.setupFrame(this);
		setBounds(100, 100, 400, 400);
		
		Canvas canvas = new Canvas();
		getContentPane().add(canvas, BorderLayout.CENTER);
	}

}
