package model.gym.members;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.gym.ICourse;
import model.gym.IGym;

public class Employee extends AbstractGymMember implements IEmployee {
	
    private double salary;
    private final Map<Integer, Set<Integer>> workingHours;

    //Deciso di utilizzare Integer invece dell'enum Day (utilizzeremo i campi
    //statici di Calendar -> Calendar.MONDAY, Calendar.TUESDAY...
    public Employee(final String name, final String surname, final String fiscalCode, final String address, final String phoneNumber, final String email, final IGym gym, final double salary, final List<ICourse> corsi){
        super(name, surname, fiscalCode, address, phoneNumber, email, gym, corsi);
        this.salary = salary;
        this.workingHours = new HashMap<>();
        this.workingHours.put(Calendar.MONDAY, new HashSet<>());
        this.workingHours.put(Calendar.TUESDAY, new HashSet<>());
        this.workingHours.put(Calendar.WEDNESDAY, new HashSet<>());
        this.workingHours.put(Calendar.THURSDAY, new HashSet<>());
        this.workingHours.put(Calendar.FRIDAY, new HashSet<>());
        this.workingHours.put(Calendar.SATURDAY, new HashSet<>());
        this.workingHours.put(Calendar.SUNDAY, new HashSet<>());
    }

    @Override
    public double getSalary(){
        return this.salary;
    }

    @Override
    public Map<Integer, Set<Integer>> getWorkingHours(){
        return this.workingHours;
    }

    @Override
    public void setSalary(final double salary){
    	this.salary = salary;
    }
    
    @Override
    public void setWorkingHours(final Integer day, final Set<Integer> hours){
    	//controllare che non ci siano "doppioni" tra le ore.
    	this.workingHours.replace(day, hours);
    }

    @Override
    public Object[] createRow() {
        return new Object[]{this.getName(),this.getSurname(),this.getFiscalCode(),this.getAddress(),this.getNumber(),this.getEmail(),this.getSalary()};
    }

}
