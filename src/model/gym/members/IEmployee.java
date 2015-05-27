package model.gym.members;


public interface IEmployee extends IGymMember {

    double getSalary();

//    Map<Integer, Set<Integer>> getWorkingHours();

    void setSalary(final double salary);
    
//    void setWorkingHours(final Integer day, final Set<Integer> hours);
    
}
