package controller.panels.home;

import java.awt.BorderLayout;
import java.util.ArrayList;

import model.IModel;
import model.gym.GymCalendar.DaysOfWeek;
import model.gym.ICourse;
import model.gym.IGymCalendar;
import model.gym.Schedule;
import view.PrimaryFrame;
import view.panels.home.CoursesWrapperRenderer.CoursesWrapper;
import view.panels.home.HomePanel;
import view.panels.home.LegendPanel;
import view.panels.home.SetCalendarPanel;

public class HomePanelController implements IHomePanelController {

	private static final String BACKGROUND_PATH = "/background.png";
	private static final String DIALOG_TITLE = "Modifica l'orario della giornata: ";
	private static final int DIALOG_WIDTH = 400;
	private static final int DIALOG_HEIGHT = 300;
	
	private final IModel model;
	private final PrimaryFrame frame;
	private final HomePanel view;
	
	public HomePanelController(final IModel model, final PrimaryFrame frame, final HomePanel view) {
		this.model = model;
		this.frame = frame;
		this.view = view;
		this.view.add(new LegendPanel(this.model.getGym(this.frame.getActiveUser()).getCourses()), BorderLayout.SOUTH);
		this.view.attachObserver(this);
	}
	
	@Override
	public void loadCalendar() {
		this.view.refreshTable();
		final IGymCalendar calendar = this.model.getGym(this.frame.getActiveUser()).getProgram();
		
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
			this.view.addDataRow(new Object[] {"--:--", new CoursesWrapper(new ArrayList<ICourse>(), false), 
					new CoursesWrapper(new ArrayList<ICourse>(), false), new CoursesWrapper(new ArrayList<ICourse>(), false), 
					new CoursesWrapper(new ArrayList<ICourse>(), false), new CoursesWrapper(new ArrayList<ICourse>(), false), 
					new CoursesWrapper(new ArrayList<ICourse>(), false), new CoursesWrapper(new ArrayList<ICourse>(), false)});
		}
		
		else { 	
			for(int i = minHour; i < maxHour; i++) {
				this.view.addDataRow(new Object[] {i +":00", new CoursesWrapper(calendar.getCalendar().get(DaysOfWeek.LUNEDI).getCoursesInHour(i), calendar.getCalendar().get(DaysOfWeek.LUNEDI).isGymOpenedAt(i)),
						new CoursesWrapper(calendar.getCalendar().get(DaysOfWeek.MARTEDI).getCoursesInHour(i), calendar.getCalendar().get(DaysOfWeek.MARTEDI).isGymOpenedAt(i)), 
						new CoursesWrapper(calendar.getCalendar().get(DaysOfWeek.MERCOLEDI).getCoursesInHour(i), calendar.getCalendar().get(DaysOfWeek.MERCOLEDI).isGymOpenedAt(i)), 
						new CoursesWrapper(calendar.getCalendar().get(DaysOfWeek.GIOVEDI).getCoursesInHour(i), calendar.getCalendar().get(DaysOfWeek.GIOVEDI).isGymOpenedAt(i)), 
						new CoursesWrapper(calendar.getCalendar().get(DaysOfWeek.VENERDI).getCoursesInHour(i), calendar.getCalendar().get(DaysOfWeek.VENERDI).isGymOpenedAt(i)), 
						new CoursesWrapper(calendar.getCalendar().get(DaysOfWeek.SABATO).getCoursesInHour(i), calendar.getCalendar().get(DaysOfWeek.SABATO).isGymOpenedAt(i)), 
						new CoursesWrapper(calendar.getCalendar().get(DaysOfWeek.DOMENICA).getCoursesInHour(i), calendar.getCalendar().get(DaysOfWeek.DOMENICA).isGymOpenedAt(i))});
			}
		}
	}
	
	@Override
	public void cmdEditDaySchedule(final String day) {
		final SetCalendarPanel panel = new SetCalendarPanel(BACKGROUND_PATH);
		final ISetCalendarController controller = new SetCalendarController(this.model, this.frame, panel, this, DaysOfWeek.getValueByName(day));
		try {
			controller.loadData();
			this.frame.new DialogWindow(DIALOG_TITLE + day, DIALOG_WIDTH, DIALOG_HEIGHT, this.frame, panel);
		} catch(final IllegalArgumentException e) {
			this.frame.displayError(e.getMessage());
		}
	}

}
