package view.panels.home;

import java.util.List;

import view.panels.IGenericTable;
import model.gym.GymCalendar.DaysOfWeek;
import model.gym.ICourse;
import model.gym.ISchedule;
import model.gym.members.IEmployee;
import controller.panels.home.ISetCalendarController;

/**
 * @author simone Defines the {@link SetCalendarPanel}
 */
public interface ISetCalendarPanel extends IGenericTable{

    /**
     * load the information for the specified day in the view's components
     * 
     * @param day
     *            the day to load
     * @param schedule
     *            the schedule
     * @param gymCourses
     *            the list of courses to be added in program
     * @param gymCoaches
     *            the list of coaches to be added in program in combination of a
     *            specific course
     */
    void loadFields(final DaysOfWeek day, final ISchedule schedule, final List<ICourse> gymCourses, final List<IEmployee> gymCoaches);

    /**
     * Changes the current {@link ISetCalendarController}.
     * 
     * @param observer
     *            the new {@link ISetCalendarController}.
     */
    void attachViewObserver(final ISetCalendarController observer);
}
