package model.gym.members;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import model.gym.ICourse;
import model.gym.IGym;


public class Subscriber extends AbstractGymMember implements ISubscriber, Serializable {
	
	private static final long serialVersionUID = -1414275393882247088L;
	
	private Calendar subscriptionDate;
    private Calendar expirationDate;
    private boolean expired;

    //Deciso di utilizzare Calendar invece di Date.
    public Subscriber(final String name, final String surname, final String fiscalCode, final String address, final String phoneNumber, final String email, final IGym gym, final Calendar expirationDate, final List<ICourse> corsi){
    	super(name, surname, fiscalCode, address, phoneNumber, email, gym, corsi);
    	this.subscriptionDate = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
    	this.expirationDate = expirationDate;
    	this.expired = false;
    }

    @Override
    public Calendar getExpirationDate(){
        return this.expirationDate;
    }
    
    @Override
    public Calendar getSubscriptionDate(){
    	return this.subscriptionDate;
    }

    @Override
    public boolean isExpired(){
    	return this.expired;
    }
    
    @Override
    public void setExpired(){
    	this.expired = this.expirationDate.before(Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY));
    }
    
    @Override
    public void subscribe(final Calendar expirationDate){
    	this.subscriptionDate = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
    	this.expirationDate = expirationDate;
    	this.setExpired();
    }

    @Override
    public double computeFee(){
    	double fee = 0;
    	for(final ICourse course : this.getGym().getCourses()) {
    		if(course.getCurrentMembers().contains(this)) {
    			fee += course.getCoursePrice() * this.getDays() - this.getGym().getSale() * this.getDays();
    		}
    	}
        return fee;
    }

    private long getDays(){
    	return TimeUnit.MILLISECONDS.toDays(this.expirationDate.getTimeInMillis() - this.subscriptionDate.getTimeInMillis());
    }

    
    @Override
    public Object[] createRow() {
        return new Object[]{this.getName(),this.getSurname(),this.getFiscalCode(),this.getAddress(),this.getNumber(),this.getEmail(),this.getExpirationDate()};

    }
}
