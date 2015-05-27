package model.gym.members;

import java.util.Calendar;


public interface IEmployee extends IGymMember {

    double getSalary();
    
    double getCredit();
    
    Calendar getLastPayed();
//    Map<Integer, Set<Integer>> getWorkingHours();

    void setSalary(final double salary);

	void setCredit(double credit);

	void settleCredit();
	
//    void setWorkingHours(final Integer day, final Set<Integer> hours);
    
}
