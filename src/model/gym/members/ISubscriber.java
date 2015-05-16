package model.gym.members;

import java.util.Calendar;

public interface ISubscriber extends IGymMember {

    Calendar getExpirationDate();
    
    Calendar getSubscriptionDate();
    
    boolean isExpired();
    
    void setExpired();
    
    void subscribe(final Calendar expirationDate);

    double computeFee();
    
}
