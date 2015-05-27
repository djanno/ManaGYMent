package model.gym;

import java.awt.Color;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;
import exceptions.CourseIsFullException;

public interface IGym {

    String getGymName();

    List<ISubscriber> getSubscribers();

    List<IEmployee> getEmployees();

    IGymCalendar getProgram();

    List<ICourse> getCourses();

    List<ICourse> getCoursesWithCoaches();

    ICourse getCourseByName(final String name);

    ICourse getCourseByColor(final Color color);

    void addSubscriber(final ISubscriber subscriber) throws CourseIsFullException;
    
    void addSubscriber(final int index, final ISubscriber subscriber) throws CourseIsFullException;

    void addEmployee(final IEmployee employee);
    
    void addEmployee(final int index, final IEmployee employee);

    void addCourse(final ICourse course);

    void addCourse(final int index, final ICourse course);

    void removeCourse(final int courseIndex);

    void removeSubscriber(final int subscriberIndex);

    void removeEmployee(final int employeeIndex);

	double getCurrentIncome();

	Map<String, Double> getIncome();

	void setIncome(Double amount, Calendar subscriptionCalendar)
			throws IllegalArgumentException;


	void setExpiredSubscribers();

    void setCalendar(IGymCalendar calendar);

	void updateEmployeesCredit();

}
