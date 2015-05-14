package controller.panels.home;

import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import model.IModel;
import model.gym.GymCalendar.DaysOfWeek;
import model.gym.ICourse;
import model.gym.Pair;
import model.gym.Schedule;
import model.gym.members.IEmployee;
import view.PrimaryFrame;
import view.panels.home.SetCalendarPanel;

public class SetCalendarController implements ISetCalendarController {

	private static final String WRONG_COURSE_HOUR = "L'ora di fine corso deve essere maggiore rispetto a quella di inizio corso.";
	private static final String NOT_VALID_START_HOUR_COURSE = "L'ora di inizio corso non coincide con l'orario di aperura della palestra.\n Selezionare un'ora di inizio corso maggiore oppure modificare l'orario di apertura della palestra.";
	private static final String NOT_VALID_END_HOUR_COURSE = "L'ora di fine corso non coincide con l'orario di chiusura della palestra.\n Selezionare un'ora di fine corso minore oppure modificare l'orario di chiusura della palestra.";
	private static final String HOUR_NOT_COMBINE = "L'ora in cui sono stati introdotti i corsi non coincide con l'apertura della palestra."
			+ " Modificare o l'orario di apertura/chiusura oppure rimuovere i corsi inseriti nelle ore in cui la palestra risulta chiusa.";

	private final IModel model;
	private final PrimaryFrame frame;
	private final SetCalendarPanel view;
	private final IHomePanelController homeController;
	private final DaysOfWeek day;
	private final Schedule temp;

	public SetCalendarController(final IModel model, final PrimaryFrame frame,
			final SetCalendarPanel view, final IHomePanelController homeController, final DaysOfWeek day) {
		super();
		this.model = model;
		this.view = view;
		this.homeController = homeController;
		this.frame = frame;
		this.day = day;
		final Schedule schedule = this.model.getGym(this.frame.getActiveUser()).getProgram().getCalendar().get(this.day);
		this.temp = new Schedule(schedule.isOpened(), schedule.getOpeningHour().orElse(null), schedule.getClosingHour().orElse(null), schedule.getProgram());
		this.view.attachViewObserver(this);
	}

	@Override
	public void loadData() throws IllegalArgumentException {
		final List<ICourse> courseWithCoaches = this.model.getGym(this.frame.getActiveUser()).getCoursesWithCoaches();
		if (courseWithCoaches.isEmpty()) {
//		    io direi di creare un eccezione specifica e non un illegalargument
			throw new IllegalArgumentException("Non esistono corsi con degli insegnanti");
		} else {
			view.loadFields(day,
        			        this.model.getGym(this.frame.getActiveUser()).getProgram().getCalendar().get(this.day),
        			        courseWithCoaches,
        			        courseWithCoaches.get(0).getCoaches());
		}
	}

	public List<IEmployee> loadCoachesByCourseName(final String courseName) {
		return this.model.getGym(this.frame.getActiveUser()).getCourseByName(courseName)
				.getCoaches();
	}

	public void formTable() {
		this.view.refreshTable();
		//final Schedule s = this.model.getGym(this.frame.getActiveUser()).getProgram().getCalendar()
		//		.get(day);
		for (final Integer i : this.temp.getProgram().keySet()) {
			for (final Pair<ICourse, IEmployee> pair : this.temp.getProgram().get(i)) {
				final Object[] row = new Object[] { i, i + 1,
						pair.getX().getCourseName(),
						pair.getY().getName() + " " + pair.getY().getSurname(),
						pair.getY().getFiscalCode() };
				this.view.addDataRow(row);
			}
		}
	}

	public void addPairInHoursCmd(final String courseName, final String employeeDetails,
			final Integer hourFrom, final Integer hourTo, final Integer openingTime,
			final Integer closingTime) {
		try {
			this.checkHours(hourFrom, hourTo, openingTime, closingTime);
			final String fiscalCode = employeeDetails.split(" ")[2];
			final ICourse course = this.model.getGym(this.frame.getActiveUser()).getCourseByName(
					courseName);
			final IEmployee employee = course.getCoachByFiscalCode(fiscalCode);
			final Pair<ICourse, IEmployee> pairInHour = new Pair<>(course, employee);
			//final Schedule sch = this.model.getGym(this.frame.getActiveUser()).getProgram().getCalendar()
			//		.get(day);
			this.temp.putPairInHour(pairInHour, hourFrom, hourTo);
			this.formTable();
		} catch (IllegalArgumentException exc) {
			this.frame.displayError(exc.getMessage());
		}
	}

	public void removePairInHourCmd(final Integer time, final String courseName,
			final String fiscalCode) {
		//final Schedule sch = this.model.getGym(this.frame.getActiveUser()).getProgram().getCalendar()
		//		.get(day);
		final ICourse courseToBeRemoved = this.model.getGym(this.frame.getActiveUser()).getCourseByName(courseName);
		final Pair<ICourse, IEmployee> pair = new Pair<>(courseToBeRemoved,
				courseToBeRemoved.getCoachByFiscalCode(fiscalCode));
		this.temp.removePairInHour(pair, time);
		this.formTable();
	}

	@Override
	public void endCmd(final Boolean isOpen, final Integer openingTime, final Integer closingTime) {
		try {
			this.finalControl(openingTime, closingTime);
			//final Schedule sch = this.model.getGym(this.frame.getActiveUser()).getProgram().getCalendar().get(this.day);
			
			if (isOpen) {
				this.temp.setOpened(isOpen);
				this.temp.setOpeningHourAndClosingHour(openingTime, closingTime);
				this.model.getGym(this.frame.getActiveUser()).getProgram().setSchedule(this.day, this.temp);
				this.homeController.loadCalendar();
				this.frame.getChild().closeDialog();
				return;
			}
			
			final int n = JOptionPane.showConfirmDialog(this.frame.getChild(), "La chiusura della palestra per il giorno " + this.day.getName() + 
					", provocherà la cancellazione del programma salvato nel giorno " + this.day.getName() + 
					". Sicuro di voler continuare?", "Warning", JOptionPane.OK_CANCEL_OPTION);
			
			if(n == JOptionPane.OK_OPTION) { 
				this.model.getGym(this.frame.getActiveUser()).getProgram().setSchedule(this.day, new Schedule());
				this.homeController.loadCalendar();
				this.frame.getChild().closeDialog();
			}
			
		} catch (final IllegalArgumentException e) {
			this.frame.displayError(e.getMessage());
		}

	}

	private void checkHours(final Integer hourFrom, final Integer hourTo,
			final Integer openingTime, final Integer closingTime)
			throws IllegalArgumentException {
		if (hourTo <= hourFrom) {
			throw new IllegalArgumentException(WRONG_COURSE_HOUR);
		}
		if (hourFrom < openingTime) {
			throw new IllegalArgumentException(NOT_VALID_START_HOUR_COURSE
					+ openingTime);
		}
		if (hourTo > closingTime) {
			throw new IllegalArgumentException(NOT_VALID_END_HOUR_COURSE
					+ closingTime);
		}
	}

	private void finalControl(final Integer openingTime, final Integer closingTime)
			throws IllegalArgumentException {
		final Iterator<Integer> iterator = this.temp.getProgram().keySet().iterator();
		Integer firstHour;
		Integer lastHour;
		if (iterator.hasNext()) {
			firstHour = iterator.next();
			lastHour = firstHour;
		} else {
			throw new IllegalArgumentException("Impossibile impostare una schedule senza inserire corsi");
		}
		if (openingTime >= closingTime) {
			throw new IllegalArgumentException(NOT_VALID_END_HOUR_COURSE);
		}
		while (iterator.hasNext()) {
			final Integer current = iterator.next();
			if (!iterator.hasNext()) {
				lastHour = current;
			}
		}
		if (firstHour < openingTime || lastHour > closingTime) {
			throw new IllegalArgumentException(HOUR_NOT_COMBINE);
		}
	}

}
