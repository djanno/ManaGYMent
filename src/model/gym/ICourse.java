package model.gym;

import java.awt.Color;
import java.util.List;

import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;
import exceptions.CourseIsFullException;

/**
 * @author Simone Letizi
 * @author Federico Giannoni
 * 
 * Defines {@link Course}
 */
public interface ICourse {

    /**
     * @return
     *          the name of course
     */
    String getCourseName();

    /**
     * @return
     *          the price of course
     */
    double getCoursePrice();

    /**
     * @return
     *          the members of the course
     */
    List<ISubscriber> getCurrentMembers();

    /**
     * @return
     *          the coaches of the course
     */
    List<IEmployee> getCoaches();

    /**
     * @return
     *          the number of members that the course can reach
     */
    int getMaxMembers();

    /**
     * @return
     *          the color assigned to the course
     */
    Color getCourseColor();

    /**
     * @param name
     *          the new name of the course
     */
    void setCourseName(final String name);

    /**
     * @param color
     *          the new color of the course
     */
    void setCourseColor(final Color color);

    /**
     * @param price
     *          the new price of the course
     */
    void setCoursePrice(final double price);

    /**
     * @param maxMember
     *          the new number of members that the course can reach
     */
    void setMaxMembers(final int maxMember);

    /**
     * Return true if the course have at least one coach
     * @return
     *          true if the course have at least one coach
     */
    boolean hasCoaches();

    /**
     * Returns the employee that has the fiscal code specified.
     * 
     * @param fiscalCode
     *          the fiscal code of coach to search
     * @return
     *          the employee that has the fiscal code specified.
     */
    IEmployee getCoachByFiscalCode(String fiscalCode);

    /**
     * Appends the specified member to the end of subscribers' list 
     * 
     * @param member
     *          the member that wants to enroll for the course
     * @throws CourseIsFullException
     *          is the course has reached a max number of members
     */
    void addMember(final ISubscriber member) throws CourseIsFullException;

    /**
     * Appends the specified coach to the end of the list of coaches
     * 
     * @param coach
     *          the coach to be appended to the list of coaches of the course
     */
    void addCoach(final IEmployee coach);

    /**
     * Remove the member in the index specified from members' list 
     * 
     * @param indexOfMember
     *          the index of member to be removed
     * @throws IllegalArgumentException
     *          if the member does not exist in members' list
     */
    void removeMember(final int indexOfMember) throws IllegalArgumentException;

    /**
     * Remove all expired members
     */
    void removeExpiredMembers();

    /**
     * Remove the coach in the index specified from coaches' list
     * 
     * @param indexOfCoach
     *          the index of coach to be removed
     * @throws IllegalArgumentException
     *          if the coach does not exist in coaches' list       
     */
    void removeCoach(final int indexOfCoach) throws IllegalArgumentException;

    /**
     * Remove the specified member to members' list
     * 
     * @param member
     *          the member to be removed
     * @throws IllegalArgumentException
     *          if the member does not exist in members' list
     */
    void removeMember(final ISubscriber member) throws IllegalArgumentException;

    /**
     * Remove the specified coach to coaches' list
     * 
     * @param coach
     *          the coach to be removed
     * @throws IllegalArgumentException
     *          if the coach does not exist in coaches' list
     */
    void removeCoach(final IEmployee coach) throws IllegalArgumentException;

}
