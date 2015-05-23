package controller.panels.gym;

import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import model.IModel;
import model.gym.ICourse;
import model.gym.Pair;
import model.gym.Schedule;
import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;
import view.PrimaryFrame;
import view.panels.gym.AddCoursePanel;
import view.panels.gym.EditCoursePanel;
import view.panels.gym.GymPanel;

public class GymPanelController implements IGymPanelController {

	private static final String DIALOG_TITLE = "Aggiungi corso: ";
	private static final int DIALOG_WIDTH_ADD = 350;
	private static final int DIALOG_HEIGHT_ADD = 245;
	private static final int DIALOG_WIDTH_EDIT = 501;
        private static final int DIALOG_HEIGHT_EDIT = 405;
	
	private final IModel model;
	private final PrimaryFrame frame;
	private final GymPanel view;
	
	public GymPanelController(final IModel model, final PrimaryFrame frame, final GymPanel view) {
		this.model = model;
		this.frame = frame;
		this.view = view;
		this.view.setHeader(model.getGym(this.frame.getActiveUser()).getGymName());
		this.view.attachObserver(this);
	}
	
	@Override
	public void loadIncomeTable(){
		this.view.getIncomePanel().refreshTable();
		final Map<String, Double> map = this.model.getUser(this.frame.getActiveUser()).getGym().getIncome();
		
		for(final String s : map.keySet()){
			final Object[] row = new Object[]{s, map.get(s)};
			this.view.getIncomePanel().addDataRow(row);
		}
	}
	
	@Override
	public void loadCoursesTable() {
		this.view.refreshTable();
		for(final ICourse course : this.model.getGym(this.frame.getActiveUser()).getCourses()) {
			this.view.addDataRow(new Object[] {course.getCourseName(), course.getCourseColor(), course.getCoursePrice()});
		}
	}

	@Override
	public void cmdAddCourse() {
		final AddCoursePanel panel = new AddCoursePanel(this.view.getBackgroundPath());
		new AddCourseController(this.frame, this.model, panel, this);
		this.frame.new DialogWindow(DIALOG_TITLE, DIALOG_WIDTH_ADD, DIALOG_HEIGHT_ADD, this.frame, panel);
	}

	@Override
	public void cmdEditCourse(final int index) {
		final EditCoursePanel panel = new EditCoursePanel(this.view.getBackgroundPath());
		final IEditCourseController observer = new EditCourseController(this.frame, this.model, panel, this, this.model.getGym(this.frame.getActiveUser()).getCourses().get(index));
		this.frame.new DialogWindow(DIALOG_TITLE, DIALOG_WIDTH_EDIT, DIALOG_HEIGHT_EDIT, this.frame, panel);
		observer.loadData();
	}

	@Override
	public void cmdDeleteCourse(final int index) {
		final ICourse courseToDelete = this.model.getGym(this.frame.getActiveUser()).getCourses().get(index);
		final int n = JOptionPane.showConfirmDialog(this.frame, "Vuoi veramente cancellare il corso?", "Cancellare " + courseToDelete.getCourseName() + "?", JOptionPane.OK_CANCEL_OPTION);
		if(n == JOptionPane.OK_OPTION) { 
			final Thread agent = new Thread() {
				
				@Override
				public void run() {		
					for(final Schedule schedule : model.getGym(frame.getActiveUser()).getProgram().getCalendar().values()) {
						courseToDelete.getCoaches().forEach(coach->schedule.deletePair(new Pair<ICourse, IEmployee>(courseToDelete, coach)));
					}
					
					for(final ISubscriber sub : courseToDelete.getCurrentMembers()) {
						final long daysLeft = TimeUnit.MILLISECONDS.toDays(sub.getExpirationDate().getTimeInMillis() - Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome")).getTimeInMillis());
						model.getGym(frame.getActiveUser()).setIncome(-(courseToDelete.getCoursePrice() * daysLeft), sub.getSubscriptionDate());
					}
					
					model.getGym(frame.getActiveUser()).removeCourse(index);
					loadCoursesTable();
				}
			};
			agent.start();
		}
	}
	
}
