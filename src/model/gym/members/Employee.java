package model.gym.members;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import model.gym.IGym;

public class Employee extends AbstractGymMember implements IEmployee, Serializable {
	
	private static final long serialVersionUID = 4856734291021213699L;
	
	private double salary;
	private double credit;
	private Calendar lastPayed;
//    private final Map<Integer, Set<Integer>> workingHours;

    //Deciso di utilizzare Integer invece dell'enum Day (utilizzeremo i campi
    //statici di Calendar -> Calendar.MONDAY, Calendar.TUESDAY...
    public Employee(final String name, final String surname, final String fiscalCode, final String address, final String phoneNumber, final String email, final IGym gym, final double salary){
        super(name, surname, fiscalCode, address, phoneNumber, email, gym);
        this.salary = salary;
        this.credit = salary;
        this.lastPayed = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
        /*this.workingHours = new HashMap<>();
        this.workingHours.put(Calendar.MONDAY, new HashSet<>());
        this.workingHours.put(Calendar.TUESDAY, new HashSet<>());
        this.workingHours.put(Calendar.WEDNESDAY, new HashSet<>());
        this.workingHours.put(Calendar.THURSDAY, new HashSet<>());
        this.workingHours.put(Calendar.FRIDAY, new HashSet<>());
        this.workingHours.put(Calendar.SATURDAY, new HashSet<>());
        this.workingHours.put(Calendar.SUNDAY, new HashSet<>());*/
    }

    @Override
    public double getSalary(){
        return this.salary;
    }

//    @Override
//    public Map<Integer, Set<Integer>> getWorkingHours(){
//        return this.workingHours;
//    }
    
    @Override
    public double getCredit() {
    	return this.credit;
    }

    @Override
    public Calendar getLastPayed() {
    	return this.lastPayed;
    }
    
    @Override
    public void setSalary(final double salary){
    	this.salary = salary;
    }
    
    
    @Override
    public void setCredit(final double credit) {
    	this.credit += credit;
    	this.lastPayed = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
    }
    
    /*@Override
    public void setWorkingHours(final Integer day, final Set<Integer> hours){
    	//controllare che non ci siano "doppioni" tra le ore.
    	this.workingHours.replace(day, hours);
    }*/

    @Override
    public void settleCredit() {
    	this.getGym().setIncome(- this.credit, Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY));
    	this.credit = 0.0;
    	}
    
    @Override
    public Object[] createRow() {
        return new Object[]{this.getName(),this.getSurname(),this.getFiscalCode(),this.getAddress(),this.getNumber(),this.getEmail(),this.getSalary(), this.getCredit()};
    }

}
