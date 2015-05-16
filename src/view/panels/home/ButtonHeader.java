package view.panels.home;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import model.gym.GymCalendar.DaysOfWeek;

public class ButtonHeader extends JButton implements TableCellRenderer, MouseListener {
	
	private static final long serialVersionUID = -2671561830772836025L;
	
	private final JTable table;
	private boolean click;
	private int column;
	
	public ButtonHeader(final JTable table, final String text, final ActionListener listener) {
		super(text);
		this.table = table;
		this.addActionListener(listener);
		this.click = false;
		
		//DUPLICAZIONE DI QUESTE RIGHE -> RISOLVERE?
		if(this.table != null && this.table.getTableHeader() != null) {
			this.table.getTableHeader().addMouseListener(this);
		}
	}
	

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object object,
			final boolean isSelected, final boolean hasFocus, final int row, final int column) {
		
		if(table != null && table.getTableHeader() != null) {
			this.setForeground(table.getTableHeader().getForeground());
			this.setBackground(table.getTableHeader().getBackground());
		}
		
		this.column = column;
		this.setActionCommand(DaysOfWeek.values()[column - 1].getName()); //column + 1 perchè la prima colonna è vuota.
		this.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		return this;
	}
	
	private void handleClick(final MouseEvent e) {
		if(this.click) { 
			click = false;
			
			final JTableHeader header = (JTableHeader) e.getSource();
			final int columnIndex = header.getTable().getColumnModel().getColumnIndexAtX(e.getX());
			
			if(e.getClickCount() == 1 && columnIndex > 0 && column == columnIndex) { 
				((ButtonHeader) header.getColumnModel().getColumn(columnIndex).getHeaderRenderer()).doClick();
			}
		}
	}
	
	@Override
	public void mouseClicked(final MouseEvent e) {
		this.handleClick(e);
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		this.click = true;
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		
	}

}
