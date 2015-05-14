package model.gym;

import java.awt.Color;
import java.util.List;

import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;

public interface IGym {

    String getGymName();

    List<ISubscriber> getSubscribers();

    List<IEmployee> getEmployees();

    IGymCalendar getProgram();

    List<ICourse> getCourses();

    List<ICourse> getCoursesWithCoaches();

    ICourse getCourseByName(final String name);

    ICourse getCourseByColor(final Color color);

    double getSale();

    void setSale(final double sale);

    void addSubscriber(final ISubscriber subscriber);

    void addEmployee(final IEmployee employee);

    void addCourse(final ICourse course);

    void addCourse(final int index, final ICourse course);

    void removeCourse(final int courseIndex);

    void removeSubscriber(final int subscriberIndex);

    void removeEmployee(final int employeeIndex);

    double computeMonthlyIncome();

    // void addIncome(Double ammount) throws IllegalArgumentException;

    // void decreseIncome(Double ammount) throws IllegalArgumentException;

    // Map<EnumMonths, Double> getIncome();
    
    // void setMonth(String monthName);

    // EnumMonths getMonth();

    // double getCurrentIncome();

}
