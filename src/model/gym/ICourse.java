package model.gym;

import java.awt.Color;
import java.util.List;

import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;
//import exceptions.CourseIsFullException;

public interface ICourse {

    String getCourseName();

    double getCoursePrice();

    List<ISubscriber> getCurrentMembers();

    List<IEmployee> getCoaches();

    int getMaxMembers();
    
    Color getCourseColor();

    void setCoursePrice(final double price);

    void setMaxMembers(final int max);
    
    boolean hasCoaches();
    
    IEmployee getCoachByFiscalCode(String fiscalCode);

    void addMember(final ISubscriber member) throws Exception;//CourseIsFullException;

    void addCoach(final IEmployee coach);

    void removeMember(final ISubscriber member) throws IllegalArgumentException;
    
    void removeCoach(final int indexOfCoach) throws IllegalArgumentException;

}
