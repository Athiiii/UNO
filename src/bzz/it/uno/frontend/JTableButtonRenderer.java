package bzz.it.uno.frontend;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class JTableButtonRenderer implements TableCellRenderer {

	
	 @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         JButton button = (JButton)value;
         button.setBackground(new Color(232, 14, 2));
         button.setBorder(
                 BorderFactory.createLineBorder(Color.DARK_GRAY, 10));
         button.setOpaque(true);
         return button;  
     }
}
