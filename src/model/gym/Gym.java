package model.gym;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;

public class Gym implements IGym {
	
    private final String gymName;
    private final List<ISubscriber> subscribers;
    private final List<IEmployee> employees;
    private IGymCalendar calendar;
    private final List<ICourse> courses;
    private double sale;

    public Gym(final String gymName){
    	super();
    	this.gymName = gymName;
    	this.subscribers = new ArrayList<>();
    	this.employees = new ArrayList<>();
    	this.courses = new ArrayList<>();
    	this.calendar = new GymCalendar();
    	this.sale = 0.0;
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
    public ICourse getCourseByName(String name){
        Optional<ICourse> course= this.courses.stream().filter(c->c.getCourseName().equals(name)).findAny();
        if(course.isPresent()){
            return  course.get();
        }else{
            throw new IllegalArgumentException();
        }
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
    public void addSubscriber(final ISubscriber subscriber){
    	this.subscribers.add(subscriber);
    }

    @Override
    public void addEmployee(final IEmployee employee){
    	this.employees.add(employee);
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
    public void removeCourse(final ICourse course){
    	this.courses.remove(course);
    }

    @Override
    public void removeSubscriber(final ISubscriber subscriber){
    	this.subscribers.remove(subscriber);
    }

    @Override
    public void removeEmployee(final IEmployee employee){
    	this.employees.remove(employee);
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

}
