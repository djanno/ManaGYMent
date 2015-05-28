package controller.panels.home;

/**
 * Defines the controller for the {@link HomePanel}.
 * @author Federico Giannoni
 *
 */
public interface IHomePanelController {

	/**
	 * Manages the procedure that loads the gym's week program from the model into a table.
	 */
	void loadCalendar();
	
	/**
	 * Builds a Dialog Window and its respective controller that allow to edit the gym's schedule for the specified day.
	 * @param day the day in which to modify the schedule.
	 */
	void cmdEditDaySchedule(final String day);
	
}
