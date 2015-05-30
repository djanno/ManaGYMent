package model.gym;

import java.awt.Color;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;
import exceptions.CourseIsFullException;

/**
 * The interface that defines the behavior of a {@link Gym}.
 * @author Federico Giannoni
 * @author Simone Letizi
 * @author Davide Borficchia
 *
 */
public interface IGym {

    /**
     * Returns the name of the gym
     * @return
     *          the name of the gym
     */
    String getGymName();

    /**
     * Returns the list of subscribers of the gym.
     * @return
     *          the subscribers of the gym
     */
    List<ISubscriber> getSubscribers();

    /**
     * Returns the list of employees of the gym.
     * @return
     *          the employees of the gym
     */
    List<IEmployee> getEmployees();

    /**
     * Returns the weekly program of the gym.
     * @return
     *          the weekly program of the gym
     */
    IGymCalendar getProgram();

    /**
     * Returns the list of courses of the gym.
     * @return
     *          the courses of the gym
     */
    List<ICourse> getCourses();

    /**
     * Returns a list of all the courses that have at least one coach.
     * @return
     *          the courses that have at least one coach
     */
    List<ICourse> getCoursesWithCoaches();

    /**
     * Returns the course that has the name specified.
     * @param name
     *          the name of course to search
     * @return
     *          the course that has the name specified.
     */
    ICourse getCourseByName(final String name);

    /**
     * Returns the course that has the color specified.
     * @param color
     *          the color of course to search
     * @return
     *          the course that has the color specified.
     */
    ICourse getCourseByColor(final Color color);

    /**
     * Adds a new {@link ISubscriber} to the gym.
     * @param subscriber the new subscriber to be added to the gym.
     * @throws CourseIsFullException if there is at least one {@link ICourse} to which the new subscriber
     * should be added as a member, that has already reached the maximum number of members.
     */
    void addSubscriber(final ISubscriber subscriber) throws CourseIsFullException;

    /**
     * Adds a new {@link ISubscriber} to the gym in the specified index.
     * @param index the index in which the subscriber will be added.
     * @param subscriber the new subscriber to be added to the gym.
     * @throws CourseIsFullException if there is at least one {@link ICourse} to which the new subscriber
     * should be added as a member, that has already reached the maximum number of members.
     */
    void addSubscriber(final int index, final ISubscriber subscriber) throws CourseIsFullException;

    /**
     * Adds a new {@link IEmployee} to the gym.
     * @param employee the new employee to be added to the gym.
     */
    void addEmployee(final IEmployee employee);

    /**
     * Adds a new {@link IEmployee} to the gym in the specified index.
     * @param index the index in which the employee will be added.
     * @param employee the new employee to be added.
     */
    void addEmployee(final int index, final IEmployee employee);

    /**
     * Adds a new {@link ICourse} to the gym.
     * @param course the new course to be added to the gym.
     */
    void addCourse(final ICourse course);

    /**
     * Adds a new {@link ICourse} to the gym in the specified index.
     * @param index the index in which the course will be added.
     * @param course the new course to be added to the gym.
     */
    void addCourse(final int index, final ICourse course);

    /**
     * Removes the {@link ICourse} mapped to the specified index from the gym.
     * @param courseIndex the index from which the course will be removed.
     */
    void removeCourse(final int courseIndex);

    /**
     * Removes the {@link ISubscriber} mapped to the specified index from the gym.
     * @param subscriberIndex the index from which the subscriber will be removed.
     */
    void removeSubscriber(final int subscriberIndex);

    /**
     * Removes the {@link IEmployee} mapped to the specified index from the gym.
     * @param employeeIndex the index from which the employee will be removed.
     */
    void removeEmployee(final int employeeIndex);

    /**
     * Returns the income relative to the current month.
     * @return the income relative to the current month.
     */
    double getCurrentIncome();

    /**
     * Returns the income of the gym divided in income per month.
     * @return the income of the gym divided in income per month.
     */
    Map<String, Double> getIncome();

    /**
     * Modifies the income relative to a specified month by adding a specified amount to that income.
     * @param amount the amount to add/subtract to the income.
     * @param subscriptionCalendar a calendar used to identify the month in which the income has to be modified.
     */
    void setIncome(final Double amount, final Calendar subscriptionCalendar);

    /**
     * Set the subscribers status to expired, if their membership is expired.
     */
    void setExpiredSubscribers();

    /**
     * Sets the {@link IGymCalendar} of the gym.
     * @param calendar the new calendar.
     */
    void setCalendar(final IGymCalendar calendar);

    /**
     * Update the employees' credit. Once every month, an employee's credit increase by an amount equals
     * to the employee's salary.
     */
    void updateEmployeesCredit();

    /**
     * Removes the specified {@link ICourse} from the gym.
     * @param course the course to be removed.
     */
    void removeCourse(final ICourse course);

    /**
     * Removes the specified {@link ISubscriber} from the gym.
     * @param subscriber the subscriber to be removed.
     */
    void removeSubscriber(final ISubscriber subscriber);

    /**
     * Removes the specified {@link IEmployee} from the gym.
     * @param employee the employee to be removed.
     */
    void removeEmployee(final IEmployee employee);

}
