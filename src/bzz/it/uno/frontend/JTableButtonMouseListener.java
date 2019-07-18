package bzz.it.uno.frontend;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTable;

/**
 * Listener when Button in the table is clicked
 * 
 * @author Severin Hersche
 */
public class JTableButtonMouseListener extends MouseAdapter {
	private final JTable table;

	/**
	 * define using table
	 * 
	 * @param table
	 */
	public JTableButtonMouseListener(JTable table) {
		this.table = table;
	}

	/**
	 * Check if a click on the table was on a button and execute the click event of
	 * this specific button
	 */
	public void mouseClicked(MouseEvent event) {
		// get the column of the button
		int column = table.getColumnModel().getColumnIndexAtX(event.getX());
		// get the row of the button
		int row = event.getY() / table.getRowHeight();

		// Checking the row or column is valid or not
		if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
			Object value = table.getValueAt(row, column);
			if (value instanceof JButton) {
				// perform a click event
				((JButton) value).doClick();
			}
		}
	}

}