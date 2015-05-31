package model.gym;

import java.awt.Color;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import model.gym.Schedule.Pair;
import model.gym.members.IEmployee;
import model.gym.members.IGymMember;
import model.gym.members.ISubscriber;
import utility.UtilityClass;
import exceptions.CourseIsFullException;

/**
 * @author Simone Letizi
 * @author Federico Giannoni
 * @author Davide Borficchia
 * This class represent a generic gym
 */
public class Gym implements IGym, Serializable {

    private static final long serialVersionUID = 3617529257067437822L;

    private static final String CF_ALREADY_EXISTING = "Il codice fiscale inserito è già stato assegnato a un membro della palestra";

    private final String gymName;
    private final List<ISubscriber> subscribers;
    private final List<IEmployee> employees;
    private IGymCalendar calendar;
    private final List<ICourse> courses;
    private final Map<String, Double> map;
    private final DateFormat df;

    /**
     * Construct a gym by name
     * @param gymName
     *          the name of the gym
     */
    public Gym(final String gymName) {
        super();
        this.gymName = gymName;
        this.subscribers = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.calendar = new GymCalendar();
        this.df = new SimpleDateFormat("MM/YYYY", Locale.ITALY);
        this.map = new TreeMap<>();
    }

    @Override
    public String getGymName() {
        return this.gymName;
    }

    @Override
    public List<ISubscriber> getSubscribers() {
        return UtilityClass.defend(this.subscribers);
    }

    @Override
    public List<IEmployee> getEmployees() {
        return UtilityClass.defend(this.employees);
    }

    @Override
    public IGymCalendar getProgram() {
        return this.calendar;
    }

    @Override
    public List<ICourse> getCourses() {
        return UtilityClass.defend(this.courses);
    }

    @Override
    public List<ICourse> getCoursesWithCoaches() {
        return this.courses.stream().filter(c -> c.hasCoaches()).collect(Collectors.toList());
    }

    @Override
    public ICourse getCourseByName(final String name) {
        return this.courses.stream().filter(c -> c.getCourseName().equals(name)).findAny().orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public ICourse getCourseByColor(final Color color) {
        return this.courses.stream().filter(c -> c.getCourseColor().equals(color)).findAny().orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void addSubscriber(final ISubscriber subscriber) throws IllegalArgumentException, CourseIsFullException {
        if (findMember(this.subscribers, subscriber.getFiscalCode())) {
            throw new IllegalArgumentException(CF_ALREADY_EXISTING);
        } else {
            for (final ICourse course : subscriber.getCourses()) {
                course.addMember(subscriber);
            }
            this.subscribers.add(subscriber);
        }
    }

    @Override
    public void addSubscriber(final int index, final ISubscriber subscriber) throws IllegalArgumentException, CourseIsFullException {
        if (findMember(this.subscribers, subscriber.getFiscalCode())) {
            throw new IllegalArgumentException(CF_ALREADY_EXISTING);
        } else {
            for (final ICourse course : subscriber.getCourses()) {
                course.addMember(subscriber);
            }
            this.subscribers.add(index, subscriber);
        }
    }

    @Override
    public void addEmployee(final IEmployee employee) throws IllegalArgumentException {
        if (findMember(this.employees, employee.getFiscalCode())) {
            throw new IllegalArgumentException(CF_ALREADY_EXISTING);
        } else {
            this.employees.add(employee);
        }
    }

    @Override
    public void addEmployee(final int index, final IEmployee employee) throws IllegalArgumentException {
        if (findMember(this.employees, employee.getFiscalCode())) {
            throw new IllegalArgumentException(CF_ALREADY_EXISTING);
        } else {
            this.employees.add(index, employee);
        }
    }

    @Override
    public void setExpiredSubscribers() {
        this.subscribers.forEach(subscriber -> subscriber.setExpired());
    }

    @Override
    public void setCalendar(final IGymCalendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void addCourse(final ICourse course) {
        this.courses.add(course);
    }

    @Override
    public void addCourse(final int index, final ICourse course) {
        this.courses.add(index, course);
    }

    @Override
    public void removeCourse(final int courseIndex) {
        if (courseIndex < this.courses.size() || courseIndex >= 0){
            final ICourse courseToDelete = this.courses.get(courseIndex);
            this.cleanScheduleAndRefundSubscribers(courseToDelete);
            this.courses.remove(courseIndex);
        }else{
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void removeCourse(final ICourse course) {
        if(this.courses.contains(course)){
            this.cleanScheduleAndRefundSubscribers(course);
            this.courses.remove(course);
        }else{
            throw new IllegalArgumentException();
        }
        
    }

    @Override
    public void removeSubscriber(final int subscriberIndex) {
        if (subscriberIndex < this.subscribers.size() || subscriberIndex >= 0){
            final ISubscriber subscriberToRemove = this.subscribers.get(subscriberIndex);
            this.removeDeletedMembersFromCourses(subscriberToRemove);
            this.subscribers.remove(subscriberIndex);
        }else{
            throw new IllegalArgumentException();
        }
        
    }

    @Override
    public void removeSubscriber(final ISubscriber subscriber) {
        if (this.subscribers.contains(subscriber)){
            this.removeDeletedMembersFromCourses(subscriber);
            this.subscribers.remove(subscriber);
        }else{
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void removeEmployee(final int employeeIndex) {
        if (employeeIndex < this.employees.size() || employeeIndex >= 0){
            final IEmployee employeeToRemove = this.employees.get(employeeIndex);
            this.cleanScheduleAndCoursesWithCoach(employeeToRemove);
            this.employees.remove(employeeIndex);
        }else{
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void removeEmployee(final IEmployee employee) {
        if(this.employees.contains(employee)){
            this.cleanScheduleAndCoursesWithCoach(employee);
            this.employees.remove(employee);
        }else{
            throw new IllegalArgumentException();
        }
        
    }

    @Override
    public void setIncome(final Double amount, final Calendar subscriptionCalendar) {
        this.map.put(this.df.format(subscriptionCalendar.getTime()),
                amount + this.map.getOrDefault(this.df.format(subscriptionCalendar.getTime()), 0.0));
    }

    @Override
    public Map<String, Double> getIncome() {
        return this.map;
    }

    @Override
    public double getCurrentIncome() {
        try {
            return this.map.get(this.df.format(this.getCurrentCalendar().getTime()));
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public void updateEmployeesCredit() {
        this.employees
                .stream()
                .filter(employee -> TimeUnit.MILLISECONDS.toDays(this.getCurrentCalendar().getTimeInMillis()
                        - employee.getLastPayed().getTimeInMillis()) > 29).forEach(employee -> employee.updateCredit(employee.getSalary()));
    }

    /**
     * After a {@link ICourse} has been deleted from the gym, the same course is also removed from the {@link GymCalendar} and
     * from the subscribers' membership and refunds those subscribers. 
     * @param courseToDelete the course that has been deleted from the gym.
     */
    private void cleanScheduleAndRefundSubscribers(final ICourse courseToDelete) {
        for (final ISchedule schedule : this.getProgram().getCalendar().values()) {
            courseToDelete.getCoaches().forEach(coach -> schedule.deletePair(new Pair<ICourse, IEmployee>(courseToDelete, coach)));
        }

        for (final ISubscriber sub : courseToDelete.getCurrentMembers()) {
            final long daysLeft = TimeUnit.MILLISECONDS.toDays(sub.getExpirationDate().getTimeInMillis()
                    - Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome")).getTimeInMillis());
            sub.setFee(sub.getFee() - daysLeft * courseToDelete.getCoursePrice());
            sub.removeFromCourse(courseToDelete);
        }
    }
    
    /**
     * After a {@link ISubscriber} has been removed from the list of members, such subscriber is also removed from
     * the various {@link ICourse} of which he/she was a member.
     * @param subscriberToRemove the subscriber that has been deleted from the gym.
     */
    private void removeDeletedMembersFromCourses(final ISubscriber subscriberToRemove) {
        subscriberToRemove.getCourses().forEach(course -> {
            if (course.getCurrentMembers().contains(subscriberToRemove)) {
                course.removeMember(subscriberToRemove);
            }
        });
    }
    
    /**
     * After a {@link IEmployee} has been removed from the gym, it is also removed from the {@link ICourse} in which
     * he used to be assigned as a coach. These changes are also applied to the {@link IGymCalendar}.
     * @param employeeToRemove the employee that has been removed from the gym.
     */
    private void cleanScheduleAndCoursesWithCoach(final IEmployee employeeToRemove) {
        this.courses.stream().filter(course -> course.getCoaches().contains(employeeToRemove)).forEach(course -> {
            course.removeCoach(employeeToRemove);
            this.calendar.getCalendar().forEach((day, schedule) -> schedule.deletePair(new Pair<ICourse, IEmployee>(course, employeeToRemove)));
        });
    }
    
    /**
     * Returns an instance of {@link Calendar} with the current date and time.
     * @return a calendar with the current date and time.
     */
    private Calendar getCurrentCalendar() {
        return Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
    }
    
    /**
     * Checks if a {@link IGymMember} exists in this {@link IGym}.
     * @param list the list (subscribers or employees) in which to search for the member.
     * @param cf the fiscal code of the member to find.
     * @return true if the member with the specified fiscal code has been found in the specified list, false
     * otherwise.
     */
    private boolean findMember(final List<? extends IGymMember> list, final String cf){
        return list.stream().anyMatch(s->s.getFiscalCode().equals(cf));
    }
}
