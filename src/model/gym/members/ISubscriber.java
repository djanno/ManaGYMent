package model.gym.members;

import java.util.Calendar;
import java.util.List;

import model.gym.ICourse;

public interface ISubscriber extends IGymMember {

    Calendar getExpirationDate();
    
    Calendar getSubscriptionDate();
    
    double getFee();
    
    boolean isExpired();
    
    void setExpired();
    
    void subscribe(final Calendar expirationDate);

    double computeFee();
    
    void setFee(double fee);
    
    void payFee();

	List<ICourse> getCourses();

	void setCourses(List<ICourse> courses);
	
}
