package controller.panels.home;

import java.util.List;

import model.gym.members.IEmployee;
import exceptions.NoCourseWithCoachesException;

/**
 * Defines the {@link SetCalendarController}.
 * @author simone
 *
 */
public interface ISetCalendarController {

	/**
	 * load from model the list of course that have at least one coach
	 * @throws NoCourseWithCoachesException if there is't any course that have at least one coach
	 */
	void loadData() throws NoCourseWithCoachesException;

	/**
	 * load a coach list for a specified course
	 * @param courseName
	 *             the course of which you want to see the coaches
	 * @return the coaches' list for the course specified in parameter courseName
	 */
	List<IEmployee> loadCoachesByCourseName(final String courseName);
	
	/**
	 * reset and create from scratch program's table by the program in model
	 */
	void formTable();

	/**
	 * add the combination employee-course from hourFrom to hourTo in program
	 * @param courseName
	 *             the name of the course
	 * @param employee
	 *             the information of the employee
	 * @param hourFrom
	 *             the start time of the course  
	 * @param hourTo
	 *             the end time of the course
	 * @param openingTime
	 *             the opening time 
	 * @param closingTime
	 *             the closing time
	 */
	void addPairInHoursCmd(final String courseName, final String employee,
			final Integer hourFrom, final Integer hourTo, final  Integer openingTime,
			final Integer closingTime);

	/**
	 * remove the combination employee-course in time  
	 * @param time
	 *             the hour of pair to remove 
	 * @param courseName
	 *             the name of course to remove
	 * @param fiscalCode
	 *             the fiscal code of emplotee to remove
	 */
	void removePairInHourCmd(final Integer time, final String courseName, final String fiscalCode);

	/**
	 * @param isOpen
	 *             is true if the gym is open, otherwise false
	 * @param openingTime
	 *             the opening time
	 * @param closingTime
	 *             the closing time
	 */
	void endCmd(final Boolean isOpen, final Integer openingTime, final Integer closingTime);

	
}
