package model.gym.members;

import java.util.Calendar;
import java.util.List;

import model.gym.ICourse;

public interface ISubscriber extends IGymMember {

    Calendar getSubscriptionDate();
    
    Calendar getExpirationDate();
    
    double getFee();

    boolean isExpired();
    
    List<ICourse> getCourses();

    void setSubscriptionDate(final Calendar subscriptionDate);
    
    void setExpirationDate(final Calendar expirationDate);
    
    void setFee(double fee);
    
    void setExpired();
    
    void setCourses(final List<ICourse> courses);
    
    void payFee();
	
}
