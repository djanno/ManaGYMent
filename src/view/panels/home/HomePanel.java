package view.panels.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import model.gym.GymCalendar.DaysOfWeek;
import model.gym.ICourse;
import view.panels.GenericTable;
import controller.panels.home.IHomePanelController;

public class HomePanel extends GenericTable implements IHomePanel, ActionListener {

	private static final long serialVersionUID = 7596892298271249519L;
	
	private static final String[] HEADERS = {"", DaysOfWeek.LUNEDI.getName(), DaysOfWeek.MARTEDI.getName(), 
		DaysOfWeek.MERCOLEDI.getName(), DaysOfWeek.GIOVEDI.getName(), DaysOfWeek.VENERDI.getName(), DaysOfWeek.SABATO.getName(),
		DaysOfWeek.DOMENICA.getName()};
	private static final int HEADER_HEIGHT = 40;
	private static final int ROW_HEIGHT = 20;
	
	private IHomePanelController observer;
	
	//private final String[] columnHeaders = new String[DaysOfWeek.values().length + 1];
	//private Object[][] data;
	//private final JTable table;
	
	public HomePanel(final String path) {
		super(HEADERS, path);
		this.setLayout(new BorderLayout());
		
		//columnHeaders[0] = "";
		//for(int i = 1; i < columnHeaders.length; i++) {
		//	columnHeaders[i] = DaysOfWeek.values()[i - 1].getName();
		//}
		//this.columnHeaders = (String[]) DaysOfWeek.namesOfDayVector().stream().toArray();
		//this.data = new Object[][]{};
		
		//this.table = new JTable(new CalendarioTableModel(this.data, this.columnHeaders));
		this.table.setTableHeader(new JTableHeader(this.table.getColumnModel()) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				final Dimension d = super.getPreferredSize();
				d.height = HEADER_HEIGHT;
				return d;
			}
		});
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.setRowSelectionAllowed(true);
		this.table.setColumnSelectionAllowed(false);
		this.table.setDefaultRenderer(CoursesWrapper.class, new CoursesWrapperRenderer());

		for(int i = 1; i < this.table.getColumnCount(); i++) { 
			final TableColumn tc = this.table.getColumnModel().getColumn(i);
			tc.setHeaderRenderer(new ButtonHeader(this.table, this.table.getColumnName(i), this));
		}
		
		this.table.setRowHeight(ROW_HEIGHT);
		
		final JScrollPane scroll = new JScrollPane(this.table);
		this.add(scroll, BorderLayout.CENTER);
		//this.loadTableData();	va lanciato dal controller
	}
	
	@Override
	public void attachObserver(final IHomePanelController observer) {
		this.observer = observer;
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		//questa parte di codice va in un else se ci sono altri bottoni nel pannello.
		final String command = e.getActionCommand();
		this.observer.cmdEditDaySchedule(command);
	}
	
	public static class CoursesWrapper {
		
		private final List<ICourse> courses;
		private final boolean opened;
		
		public CoursesWrapper(final List<ICourse> courses, final boolean opened) {
			this.courses = new ArrayList<ICourse>(courses);
			this.opened = opened;
		}
		
		public List<ICourse> getCourses() {
			return new ArrayList<ICourse>(this.courses);
		}
		
		public boolean isOpened() {
			return this.opened;
		}
	}
	
	class CoursesWrapperRenderer extends JPanel implements TableCellRenderer {

		private static final long serialVersionUID = 7575007678292078570L;
		
		private static final int MAX_COLORS_PER_ROW = 3;
		
		public CoursesWrapperRenderer() {
			super();
			this.setLayout(new GridBagLayout());
			this.setOpaque(true);
		}
		
		@Override
		public Component getTableCellRendererComponent(final JTable table, final Object object,
				final boolean isSelected, final boolean hasFocus, final int row, final int col) {
			
			final CoursesWrapper courses = (CoursesWrapper) object;
			
			this.addCoursesToTable(courses.getCourses(), courses.isOpened());
			
			
			if(isSelected) {
				this.setBorder(BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground()));
			}
			else {
				this.setBorder(BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground()));
			}
			
			return this;
		}
		
		private void addCoursesToTable(final List<ICourse> courses, final boolean opened) {
			
			this.removeAll();
			
			int gridx = 0;
			int gridy = 0;
			
			JLabel label = null;
			
			final double weightx = (double) CoursesWrapperRenderer.MAX_COLORS_PER_ROW / 100;
			final double weighty = ((double) courses.size() / CoursesWrapperRenderer.MAX_COLORS_PER_ROW) / 100;
			
			if(courses.isEmpty()) { //se non ci sono corsi in quell'ora, la palestra è chiusa in quell'ora -> lbl nero.

				if(opened) {
					label = this.createLabel(Color.WHITE);
				}
				
				else {
					label = this.createLabel(Color.BLACK);
				}
				
				this.add(label, new GridBagConstraints(gridx, gridy, 1, 1, weightx, weighty, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
			}
			
			for(final ICourse course : courses) {

				label = this.createLabel(course.getCourseColor());
				
				this.add(label, new GridBagConstraints(gridx, gridy, 1, 1, weightx, weighty, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
				
				gridx++;
				
				if(gridx == CoursesWrapperRenderer.MAX_COLORS_PER_ROW) {
					gridx = 0;
					gridy++;
				}
				
			}
		}
		
		private JLabel createLabel(final Color color) {
			final JLabel label = new JLabel();
			label.setPreferredSize(new Dimension(5, 5));
			label.setBackground(color);
			label.setOpaque(true);
			
			return label;
		}

	}

	class ButtonHeader extends JButton implements TableCellRenderer, MouseListener {
		
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
	
}
