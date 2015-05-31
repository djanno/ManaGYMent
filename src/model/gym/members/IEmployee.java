package model.gym.members;

import java.util.Calendar;

/**
 * Interface that defines the behavior of a {@link Employee}.
 * @author Federico Giannoni
 * @author Simone Letizi
 *
 */
public interface IEmployee extends IGymMember {

    /**
     * Returns the salary of the employee.
     * @return the salary of the employee.
     */
    double getSalary();

    /**
     * Returns the credit of the employee.
     * @return the credit of the employee.
     */
    double getCredit();

    /**
     * Returns a {@link Calendar} containing the date of the last update of the employee's credit.
     * Keep in my mind that an employee's credit should be update once a month.
     * @return a Calendar containing the date of the last update of the employee's credit.
     */
    Calendar getLastPayed();

    /**
     * Sets the salary of the employee.
     * @param salary the new salary.
     */
    void setSalary(final double salary);

    /**
     * Sets the credit of the employee, increasing it by an amount equals to the employee's salary.
     * @param credit the new credit.
     */
    void setCredit(final double credit);

    /**
     * Sets the date of the last update of the employee's credit.
     * @param lastPayed the new Calendar.
     */
    void setLastPayed(final Calendar lastPayed);

    /**
     * Settles the credit of the employee and updates the {@link Gym}'s balance for the relevant month.
     */
    void settleCredit();

}
