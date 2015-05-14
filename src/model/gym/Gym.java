package model.gym;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.stream.Collectors;

import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;

public class Gym implements IGym, Serializable {
	
	private static final long serialVersionUID = 3617529257067437822L;
	
	private final String gymName;
    private final List<ISubscriber> subscribers;
    private final List<IEmployee> employees;
    private IGymCalendar calendar;
    private final List<ICourse> courses;
    private double sale;
    private final Map<String, Double> map;
    private String month;
    
    private static final String SUBSCRIBER_ALREADY_EXISTING = "L'iscritto che si vuole aggiungere esiste già.";
    private static final String EMPLOYEE_ALREADY_EXISTING = "L'impiegato che si vuole aggiungere esiste già.";
    private static final String NEGATIVE_AMMOUNT = "L'importo deve essere positivo";
    public Gym(final String gymName){
    	super();
    	this.gymName = gymName;
    	this.subscribers = new ArrayList<>();
    	this.employees = new ArrayList<>();
    	this.courses = new ArrayList<>();
    	this.calendar = new GymCalendar();
    	this.sale = 0.0;
		this.map = new TreeMap<>();
//      createMap();
//        this.month = EnumMonths.valueOf(Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()).toUpperCase());
    }

    @Override
    public String getGymName(){
        return this.gymName;
    }

    @Override
    public List<ISubscriber> getSubscribers(){
        return new ArrayList<ISubscriber>(this.subscribers);
    }

    @Override
    public List<IEmployee> getEmployees(){
        return new ArrayList<IEmployee>(this.employees);
    }

    @Override
    public IGymCalendar getProgram(){
        return this.calendar;
    }

    @Override
    public List<ICourse> getCourses(){
        return new ArrayList<ICourse>(this.courses);
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

    public void setCalendar(final GymCalendar calendar){
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

    /*@Override
    public void addIncome(Double ammount) throws IllegalArgumentException{
            if(ammount > 0){
                    try{
                            this.map.replace(getMonth(), this.map.get(getMonth()) + ammount);
                    }catch (Exception e){
                            this.map.put(getMonth(), ammount);
                    }
            }else{
                    throw new IllegalArgumentException(NEGATIVE_AMMOUNT);
            }
    }

    @Override
    public void decreseIncome(Double ammount) throws IllegalArgumentException{
            if(ammount > 0){
                    try{
                            this.map.replace(getMonth(), this.map.get(getMonth()) - ammount);
                    }catch(Exception e){
                            this.map.put(getMonth(), -ammount);
                    }
            }else{
                    throw new IllegalArgumentException(NEGATIVE_AMMOUNT);
            }
    }

    @Override
    public Map<EnumMonths, Double> getIncome() {
            return this.map;
    }

    @Override
    public void setMonth(String monthName) {
            this.month = EnumMonths.valueOf(monthName);
    }

    @Override
    public EnumMonths getMonth() {
            return this.month;
    }
    
    @Override
    public double getCurrentIncome(){
            return this.map.get(getMonth());
    }
    */
    private boolean findSubscriber(String cf){
            for (ISubscriber s : this.subscribers){
                    if (s.getFiscalCode().equals(cf)){
                            return true;
                    }
            }
            return false;
    }
    
    private boolean findEmployee(String cf){
            for (IEmployee e : this.employees){
                    if (e.getFiscalCode().equals(cf)){
                            return true;
                    }
            }
            return false;
    }
    /*
    private void createMap(){
            for (EnumMonths e : EnumMonths.values()){
                    this.map.put(e, 0.0);
            }
    }*/


}
