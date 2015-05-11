package model.gym.members;

import java.util.Map;
import java.util.Set;

public interface IEmployee extends IGymMember {

    double getSalary();

    Map<Integer, Set<Integer>> getWorkingHours();

    void setSalary(final double salary);
    
    void setWorkingHours(final Integer day, final Set<Integer> hours);
    
}
