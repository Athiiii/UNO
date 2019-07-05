package bzz.it.uno.frontend;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class TableHeaderRenderer implements TableCellRenderer {

	private final TableCellRenderer baseRenderer;

	public TableHeaderRenderer(TableCellRenderer baseRenderer) {
		this.baseRenderer = baseRenderer;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JComponent c = (JComponent) baseRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
				column);
		c.setBorder(new EmptyBorder(2, 2, 2, 2));
		return c;
	}
}