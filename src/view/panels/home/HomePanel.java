package view.panels.home;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import model.gym.GymCalendar.DaysOfWeek;
import view.panels.GenericTable;
import view.panels.home.CoursesWrapperRenderer.CoursesWrapper;
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
			tc.setHeaderRenderer(new ButtonHeader(this.table.getColumnName(i), this));
		}
		
		this.table.setRowHeight(ROW_HEIGHT);
		
		final JScrollPane scroll = new JScrollPane(this.table);
		this.add(scroll, BorderLayout.CENTER);
		//this.loadTableData();	va lanciato dal controller
	}
	
	/*@Override
	public void loadTableData(final IGymCalendar calendar) {
		this.refreshTable();
		
		int minHour = 24;
		int maxHour = 1;
		boolean empty = true;
		
		Schedule schedule = null;
		
		for(final DaysOfWeek day : calendar.getCalendar().keySet()) {
			schedule = calendar.getCalendar().get(day);
			if(schedule.getOpeningHour().isPresent() && schedule.getOpeningHour().get() < minHour) {
				empty = false;
				minHour = schedule.getOpeningHour().get();
			}
			
			if(schedule.getClosingHour().isPresent() && schedule.getClosingHour().get() > maxHour) {
				empty = false;
				maxHour = schedule.getClosingHour().get();
			}
		}
		
		//final ManagymentTableModel tableModel = (ManagymentTableModel) this.table.getModel();
		
		if(empty) {
			this.addDataRow(new Object[] {"--:--", new CoursesWrapper(new ArrayList<ICourse>(), false), 
					new CoursesWrapper(new ArrayList<ICourse>(), false), new CoursesWrapper(new ArrayList<ICourse>(), false), 
					new CoursesWrapper(new ArrayList<ICourse>(), false), new CoursesWrapper(new ArrayList<ICourse>(), false), 
					new CoursesWrapper(new ArrayList<ICourse>(), false), new CoursesWrapper(new ArrayList<ICourse>(), false)});
		}
		
		else { 	
			for(int i = minHour; i < maxHour; i++) {
				this.addDataRow(new Object[] {i +":00", new CoursesWrapper(calendar.getCalendar().get(DaysOfWeek.LUNEDI).getCoursesInHour(i), calendar.getCalendar().get(DaysOfWeek.LUNEDI).isGymOpenedAt(i)),
						new CoursesWrapper(calendar.getCalendar().get(DaysOfWeek.MARTEDI).getCoursesInHour(i), calendar.getCalendar().get(DaysOfWeek.MARTEDI).isGymOpenedAt(i)), 
						new CoursesWrapper(calendar.getCalendar().get(DaysOfWeek.MERCOLEDI).getCoursesInHour(i), calendar.getCalendar().get(DaysOfWeek.MERCOLEDI).isGymOpenedAt(i)), 
						new CoursesWrapper(calendar.getCalendar().get(DaysOfWeek.GIOVEDI).getCoursesInHour(i), calendar.getCalendar().get(DaysOfWeek.GIOVEDI).isGymOpenedAt(i)), 
						new CoursesWrapper(calendar.getCalendar().get(DaysOfWeek.VENERDI).getCoursesInHour(i), calendar.getCalendar().get(DaysOfWeek.VENERDI).isGymOpenedAt(i)), 
						new CoursesWrapper(calendar.getCalendar().get(DaysOfWeek.SABATO).getCoursesInHour(i), calendar.getCalendar().get(DaysOfWeek.SABATO).isGymOpenedAt(i)), 
						new CoursesWrapper(calendar.getCalendar().get(DaysOfWeek.DOMENICA).getCoursesInHour(i), calendar.getCalendar().get(DaysOfWeek.DOMENICA).isGymOpenedAt(i))});
			}
		}
		
	}*/
	
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

	/*public class CalendarioTableModel extends DefaultTableModel {

		private static final long serialVersionUID = 3117390914181449175L;

		public CalendarioTableModel(final Object[][] data, final String[] headers) {
			super(data, headers);
		}
		
		@Override
		public Class<?> getColumnClass(final int columnIndex) {
			if(this.getColumnCount() > 0) {
				return this.getValueAt(0, columnIndex).getClass();
			}
			
			else return Object.class;
		}
		
		@Override
		public boolean isCellEditable(final int row, final int column) {
			return false;
		}
		
	}*/
	
}
