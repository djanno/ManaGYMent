package controller.panels.home;

import java.util.Iterator;
import java.util.List;

import model.IModel;
import model.gym.GymCalendar.DaysOfWeek;
import model.gym.ICourse;
import model.gym.Pair;
import model.gym.Schedule;
import model.gym.members.IEmployee;
import view.PrimaryFrame;
import view.panels.home.SetCalendarPanel;

public class SetCalendarController implements ISetCalendarObserver {

	private static final String WRONG_COURSE_HOUR = "L'ora di fine corso deve essere maggiore rispetto a quella di inizio corso";
	private static final String NOT_VALID_START_HOUR_COURSE = "L'ora di inizio corso non coincidere con l'orario di aperura della palestra.\n Selezionare un'ora di inizio corso maggiore oppure modificare l'orario di apertura della palestra ";
	private static final String NOT_VALID_END_HOUR_COURSE = "L'ora di fine corso non coincidere con l'orario di chiusura della palestra.\nSelezionare un'ora di fine corso minore oppure modificare l'orario di chiusura della palestra";
	private static final String HOUR_NOT_COMBINE = "L'ora in cui sono stati introdotti i corsi non coincide con l'apertura della palestra."
			+ "Modificare o l'orario di apertura/chiusura oppure rimuovere i corsi inseriti nelle ore in cui la palestra risulta chiusa";

	private final IModel model;
	private final PrimaryFrame frame;
	private final SetCalendarPanel view;
	private final IHomeController homeController;
	private final DaysOfWeek day;

	public SetCalendarController(final IModel model, final PrimaryFrame frame,
			final SetCalendarPanel view, final IHomeController homeController, final DaysOfWeek day) {
		super();
		this.model = model;
		this.view = view;
		this.homeController = homeController;
		this.frame = frame;
		this.day = day;
		this.view.attachViewObserver(this);
	}

	@Override
	public void loadData() {
		final List<ICourse> courseWithCoaches = this.model.getUser(this.frame.getActiveUser()).getGym().getCoursesWithCoaches();
		if (courseWithCoaches.isEmpty()) {
//		    io direi di creare un eccezione specifica e non un illegalargument
			throw new IllegalArgumentException("Non esistono corsi con degli insegnanti");
		} else {
			view.loadFields(day,
        			        this.model.getUser(this.frame.getActiveUser()).getGym().getProgram().getCalendar().get(this.day),
        			        courseWithCoaches,
        			        courseWithCoaches.get(0).getCoaches());
		}
	}

	public List<IEmployee> loadCoachesByCourseName(final String courseName) {
		return this.model.getUser(this.frame.getActiveUser()).getGym().getCourseByName(courseName)
				.getCoaches();
	}

	public void formTable() {
		this.view.refreshTable();
		final Schedule s = this.model.getUser(this.frame.getActiveUser()).getGym().getProgram().getCalendar()
				.get(day);
		for (final Integer i : s.getProgram().keySet()) {
			for (final Pair<ICourse, IEmployee> pair : s.getProgram().get(i)) {
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
			final ICourse course = this.model.getUser(this.frame.getActiveUser()).getGym().getCourseByName(
					courseName);
			final IEmployee employee = course.getCoachByFiscalCode(fiscalCode);
			final Pair<ICourse, IEmployee> pairInHour = new Pair<>(course, employee);
			final Schedule sch = this.model.getUser(this.frame.getActiveUser()).getGym().getProgram().getCalendar()
					.get(day);
			sch.putPairInHour(pairInHour, hourFrom, hourTo);
			this.formTable();
		} catch (IllegalArgumentException exc) {
			this.view.displayError(exc.getMessage());
		}
	}

	public void removePairInHourCmd(final Integer time, final String courseName,
			final String fiscalCode) {
		final Schedule sch = this.model.getUser(this.frame.getActiveUser()).getGym().getProgram().getCalendar()
				.get(day);
		final ICourse courseToBeRemoved = this.model.getUser(this.frame.getActiveUser()).getGym().getCourseByName(courseName);
		final Pair<ICourse, IEmployee> pair = new Pair<>(courseToBeRemoved,
				courseToBeRemoved.getCoachByFiscalCode(fiscalCode));
		sch.removePairInHour(pair, time);
		this.formTable();
	}

	@Override
	public void endCmd(final Boolean isOpen, final Integer openingTime, final Integer closingTime) {
		try {
			this.finalControl(openingTime, closingTime);
			final Schedule sch = this.model.getUser(this.frame.getActiveUser()).getGym().getProgram().getCalendar()
					.get(day);
			if (isOpen) {
				sch.setOpened(isOpen);
				sch.setOpeningHourAndClosingHour(openingTime, closingTime);
			} else {
				new Schedule();
			}
		} catch (final IllegalArgumentException e) {
			this.view.displayError(e.getMessage());
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
		final Iterator<Integer> iterator = this.model.getUser(this.frame.getActiveUser()).getGym().getProgram()
				.getCalendar().get(this.day).getProgram().keySet().iterator();
		Integer firstHour;
		Integer lastHour;
		if (iterator.hasNext()) {
			firstHour = iterator.next();
			lastHour = firstHour;
		} else {
			throw new IllegalArgumentException(
					"Impossibile impostare una schedule senza inserire corsi");
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
