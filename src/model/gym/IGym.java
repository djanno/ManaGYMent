package model.gym;

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
    
    ICourse getCourseByName(String name);
    
    double getSale();

    void setSale(double sale);

    void addSubscriber(ISubscriber subscriber);

    void addEmployee(IEmployee employee);

    void addCourse(ICourse course);
    
    void addCourse(final int index,final ICourse course);

    public void removeCourse(ICourse course);

    void removeSubscriber(ISubscriber subscriber);

    void removeEmployee(IEmployee employee);

    double computeMonthlyIncome();

}
