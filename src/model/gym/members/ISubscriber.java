package model.gym.members;

import java.util.Calendar;
import java.util.List;

import model.gym.ICourse;

public interface ISubscriber extends IGymMember {

    Calendar getExpirationDate();
    
    Calendar getSubscriptionDate();
    
    boolean isExpired();
    
    void setExpired();
    
    void subscribe(final Calendar expirationDate);

    double computeFee();

	List<ICourse> getCourses();

	void setCourses(List<ICourse> courses);
    
}
