package model.gym;

import java.awt.Color;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
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

	double getCurrentIncome();

	Map<String, Double> getIncome();

	void setIncome(Double amount, Calendar subscriptionCalendar)
			throws IllegalArgumentException;

}
