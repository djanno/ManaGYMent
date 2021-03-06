package model.gym;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;
import utility.UtilityClass;
//import exceptions.CourseIsFullException;
import exceptions.CourseIsFullException;

/**
 * @author Simone Letizi
 * @author Federico Giannoni
 * This class represent a generic gym's course 
 */
public class Course implements ICourse, Serializable {

    private static final long serialVersionUID = 6012814332577508826L;

    private static final String COACH_ALREDY_PRESENT = "Questo coach insegna già in questo corso";
    private static final String EMPLOYEE_NOT_FOUND = "Coach non trovato";
    private static final String SUBSCRIBER_NOT_FOUND = "Iscritto non trovato";

    private String name;
    private double price;
    private final List<ISubscriber> members;
    private final List<IEmployee> coaches;
    private int maxMembers;
    private Color color;

    /**
     * Construct a course without members and coaches
     * 
     * @param name
     *          the name of the course
     * @param color
     *          the color of the course
     * @param price
     *          the price of the course
     * @param maxMembers
     *          the number of max member that the course can reach
     */
    public Course(final String name, final Color color, final double price, final int maxMembers) {
        super();
        this.name = name;
        this.price = price;
        this.color = color;
        this.maxMembers = maxMembers;
        this.members = new ArrayList<>();
        this.coaches = new ArrayList<>();
    }

    /**
     * @param name
     *          the name of the course
     * @param color
     *          the color of the course
     * @param price
     *          the price of the course
     * @param maxMembers
     *          the number of max member that the course can reach
     * @param coaches
     *          the list of coaches
     * @param members
     *          the list of members
     */
    public Course(final String name, final Color color, final double price, final int maxMembers, final List<IEmployee> coaches,
            final List<ISubscriber> members) {
        this(name, color, price, maxMembers);
        this.members.addAll(members);
        this.coaches.addAll(coaches);
    }

    @Override
    public String getCourseName() {
        return this.name;
    }

    @Override
    public double getCoursePrice() {
        return this.price;
    }

    @Override
    public List<ISubscriber> getCurrentMembers() {
        return UtilityClass.defend(this.members);
    }

    @Override
    public List<IEmployee> getCoaches() {
        return UtilityClass.defend(this.coaches);
    }

    @Override
    public int getMaxMembers() {
        return this.maxMembers;
    }

    @Override
    public Color getCourseColor() {
        return this.color;
    }

    @Override
    public void setCoursePrice(final double price) {
        this.price = price;
    }

    @Override
    public void setMaxMembers(final int max) {
        this.maxMembers = max;
    }

    @Override
    public boolean hasCoaches() {
        return !coaches.isEmpty();
    }

    @Override
    public IEmployee getCoachByFiscalCode(final String fiscalCode) {
        return this.coaches.stream().filter(employee->employee.getFiscalCode().equalsIgnoreCase(fiscalCode)).findAny().orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void addMember(final ISubscriber member) throws CourseIsFullException {                                                                              
        if (this.members.size() == this.maxMembers) {
            throw new CourseIsFullException(); 
        } else {
            this.members.add(member);
        }
    }

    @Override
    public void addCoach(final IEmployee coach) {
        if (coaches.contains(coach)) {
            throw new IllegalArgumentException(COACH_ALREDY_PRESENT);
        } else {
            this.coaches.add(coach);
        }
    }

    @Override
    public void removeMember(final int indexOfMember) throws IllegalArgumentException {
        if (indexOfMember < this.members.size() || indexOfMember >= 0) {
            this.members.remove(indexOfMember);
        } else {
            throw new IllegalArgumentException(SUBSCRIBER_NOT_FOUND);
        }
    }

    @Override
    public void removeMember(final ISubscriber member) throws IllegalArgumentException {
        if (this.members.contains(member)) {
            this.members.remove(member);
        } else {
            throw new IllegalArgumentException(SUBSCRIBER_NOT_FOUND);
        }
    }

    @Override
    public void removeExpiredMembers() {
        this.members.removeAll(this.members.stream().filter(member -> member.isExpired()).collect(Collectors.toList()));
    }

    @Override
    public void removeCoach(final int indexOfCoach) throws IllegalArgumentException {
        if (indexOfCoach < this.coaches.size() || indexOfCoach >= 0) {
            this.coaches.remove(indexOfCoach);
        } else {
            throw new IllegalArgumentException(EMPLOYEE_NOT_FOUND);
        }
    }

    @Override
    public void removeCoach(final IEmployee coach) throws IllegalArgumentException {
        if (this.coaches.contains(coach)) {
            this.coaches.remove(coach);
        } else {
            throw new IllegalArgumentException(EMPLOYEE_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Course) {
            final Course course = (Course) obj;
            if (this.name.equals(course.getCourseName()) && this.color.equals(course.getCourseColor()) && this.price == course.getCoursePrice()
                    && this.maxMembers == course.getMaxMembers() && this.coaches.equals(course.getCoaches())
                    && this.members.equals(course.getCurrentMembers())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setCourseName(String name) {
        this.name = name;
    }

    @Override
    public void setCourseColor(Color color) {
        this.color = color;
    }
}
