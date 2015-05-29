package model.gym;

import java.awt.Color;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;
import exceptions.CourseIsFullException;

/**
 * @author simone
 *
 */
public interface IGym {

    /**
     * @return
     *          the name of the gym
     */
    String getGymName();

    /**
     * @return
     *          the subscribers of the gym
     */
    List<ISubscriber> getSubscribers();

    /**
     * @return
     *          the employees of the gym
     */
    List<IEmployee> getEmployees();

    /**
     * @return
     *          the weekly program of the gym
     */
    IGymCalendar getProgram();

    /**
     * @return
     *          the courses of the gym
     */
    List<ICourse> getCourses();

    /**
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
     * @param subscriber
     * @throws CourseIsFullException
     */
    void addSubscriber(final ISubscriber subscriber) throws CourseIsFullException;

    /**
     * @param index
     * @param subscriber
     * @throws CourseIsFullException
     */
    void addSubscriber(final int index, final ISubscriber subscriber) throws CourseIsFullException;

    /**
     * @param employee
     */
    void addEmployee(final IEmployee employee);

    /**
     * @param index
     * @param employee
     */
    void addEmployee(final int index, final IEmployee employee);

    /**
     * @param course
     */
    void addCourse(final ICourse course);

    /**
     * @param index
     * @param course
     */
    void addCourse(final int index, final ICourse course);

    /**
     * @param courseIndex
     */
    void removeCourse(final int courseIndex);

    /**
     * @param subscriberIndex
     */
    void removeSubscriber(final int subscriberIndex);

    /**
     * @param employeeIndex
     */
    void removeEmployee(final int employeeIndex);

    /**
     * @return
     */
    double getCurrentIncome();

    /**
     * @return
     */
    Map<String, Double> getIncome();

    /**
     * @param amount
     * @param subscriptionCalendar
     * @throws IllegalArgumentException
     */
    void setIncome(final Double amount, final Calendar subscriptionCalendar) throws IllegalArgumentException;

    /**
     * 
     */
    void setExpiredSubscribers();

    /**
     * @param calendar
     */
    void setCalendar(final IGymCalendar calendar);

    /**
     * 
     */
    void updateEmployeesCredit();

    /**
     * @param course
     */
    void removeCourse(final ICourse course);

    /**
     * @param subscriber
     */
    void removeSubscriber(final ISubscriber subscriber);

    /**
     * @param employee
     */
    void removeEmployee(final IEmployee employee);

}
