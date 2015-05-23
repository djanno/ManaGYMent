package model.gym;

import java.awt.Color;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.stream.Collectors;

import utility.UtilityClass;
import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;

public class Gym implements IGym, Serializable {
	
	private static final long serialVersionUID = 3617529257067437822L;
	
	private static final String SUBSCRIBER_ALREADY_EXISTING = "L'iscritto che si vuole aggiungere esiste già.";
    private static final String EMPLOYEE_ALREADY_EXISTING = "L'impiegato che si vuole aggiungere esiste già.";
    
    private final String gymName;
    private final List<ISubscriber> subscribers;
    private final List<IEmployee> employees;
    private IGymCalendar calendar;
    private final List<ICourse> courses;
    private double sale;
    private Map<String, Double> map;
	private DateFormat df;
    
    public Gym(final String gymName){
    	super();
    	this.gymName = gymName;
    	this.subscribers = new ArrayList<>();
    	this.employees = new ArrayList<>();
    	this.courses = new ArrayList<>();
    	this.calendar = new GymCalendar();
    	this.sale = 0.0;
    	this.df = new SimpleDateFormat("MM/YYYY", Locale.ITALY);
    	this.map = new TreeMap<>();
    }

    @Override
    public String getGymName(){
        return this.gymName;
    }

    @Override
    public List<ISubscriber> getSubscribers(){
        return UtilityClass.defend(this.subscribers);
    }

    @Override
    public List<IEmployee> getEmployees(){
        return UtilityClass.defend(this.employees);
    }

    @Override
    public IGymCalendar getProgram(){
        return this.calendar;
    }

    @Override
    public List<ICourse> getCourses(){
        return UtilityClass.defend(this.courses);
    }
    
    @Override
    public List<ICourse> getCoursesWithCoaches(){
        return this.courses.stream().filter(c->c.hasCoaches()).collect(Collectors.toList());
    }
    
    @Override
    public ICourse getCourseByName(final String name){
        return this.courses.stream().filter(c->c.getCourseName().equals(name)).findAny().orElseThrow(()-> new IllegalArgumentException());
    }
    
    @Override
    public ICourse getCourseByColor(final Color color) {
        return this.courses.stream().filter(c->c.getCourseColor().equals(color)).findAny().orElseThrow(()-> new IllegalArgumentException());
    }

    @Override
    public double getSale(){
        return this.sale;
    }

    @Override
    public void setSale(final double sale){
    	this.sale = sale;
    }

    @Override
    public void addSubscriber(final ISubscriber subscriber) throws IllegalArgumentException{
        if (!findSubscriber(subscriber.getFiscalCode())){
                this.subscribers.add(subscriber);
        }else{
                throw new IllegalArgumentException(SUBSCRIBER_ALREADY_EXISTING);
        }
    }

    @Override
    public void addEmployee(final IEmployee employee) throws IllegalArgumentException{
        if (!findEmployee(employee.getFiscalCode())){
                this.employees.add(employee);
        }else{
                throw new IllegalArgumentException(EMPLOYEE_ALREADY_EXISTING);
        }
    } 

    @Override
    public void setExpiredSubscribers() {
    	this.subscribers.forEach(subscriber -> subscriber.setExpired());
    }
    
    @Override
    public void setCalendar(final IGymCalendar calendar){
        this.calendar=calendar;
    }
    
    @Override
    public void addCourse(final ICourse course){
    	this.courses.add(course);
    }

    public void addCourse(final int index,final ICourse course){
        this.courses.add(index, course);
    }
    
    @Override
    public void removeCourse(final int courseIndex){
    	this.courses.remove(courseIndex);
    }

    @Override
    public void removeSubscriber(final int subscriberIndex){
    	this.subscribers.remove(subscriberIndex);
    }

    @Override
    public void removeEmployee(final int employeeIndex){
    	this.employees.remove(employeeIndex);
    }

    @Override
    public double computeMonthlyIncome(){
    	double income = 0.0;
    	
    	for(final ISubscriber subscriber : this.subscribers){
    		if(subscriber.getSubscriptionDate().get(Calendar.MONTH) == Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY).get(Calendar.MONTH)){
    			income += subscriber.computeFee();
    		}
    	}
    	
    	for(final IEmployee employee : this.employees){
    		income -= employee.getSalary();
    	}
    	
        return income;
    }

    @Override
	public void setIncome(final Double amount, final Calendar subscriptionCalendar) throws IllegalArgumentException{
		double prev = 0;

		this.df.format(subscriptionCalendar.getTime());
		if(this.map.keySet().contains(this.df.format(subscriptionCalendar.getTime()))){
			prev = this.map.get(this.df.format(subscriptionCalendar.getTime()));
		}
		this.map.put(this.df.format(subscriptionCalendar.getTime()), amount + prev);
	}

	@Override
	public Map<String, Double> getIncome() {
		return this.map;
	}
	
	@Override
	public double getCurrentIncome(){
		try{
			return this.map.get(this.df.format(this.getCurrentDate()));
		}catch(Exception e){
			return 0.0;
		}
	}
	
	private Date getCurrentDate() {
		return Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY).getTime();
	}
	
    private boolean findSubscriber(final String cf){
            for (final ISubscriber s : this.subscribers){
                    if (s.getFiscalCode().equals(cf)){
                            return true;
                    }
            }
            return false;
    }
    
    private boolean findEmployee(final String cf){
            for (final IEmployee e : this.employees){
                    if (e.getFiscalCode().equals(cf)){
                            return true;
                    }
            }
            return false;
    }
}
