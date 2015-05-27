package model.gym.members;

import java.util.Calendar;

public interface IEmployee extends IGymMember {

    double getSalary();

    double getCredit();

    Calendar getLastPayed();

    void setSalary(final double salary);

    void setCredit(final double credit);
    
    void setLastPayed(final Calendar lastPayed);

    void settleCredit();

}
