package model.gym.members;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import model.gym.IGym;

/**
 * An employee of the {@link Gym}.
 * @author Federico Giannoni
 * @author Simone Letizi
 *
 */
public class Employee extends AbstractGymMember implements IEmployee, Serializable {

    private static final long serialVersionUID = 4856734291021213699L;

    private double salary;
    private double credit;
    private Calendar lastPayed;

    /**
     * Constructs an employee with the given data.
     * @param name the name of the employee.
     * @param surname the surname of the employee.
     * @param fiscalCode the fiscal code of the employee.
     * @param address the address of the employee.
     * @param phoneNumber the phone number of the employee.
     * @param email the email of the employee.
     * @param gym the gym of the employee.
     * @param salary the salary of the employee.
     */
    public Employee(final String name, final String surname, final String fiscalCode, final String address, final String phoneNumber,
            final String email, final IGym gym, final double salary) {
        super(name, surname, fiscalCode, address, phoneNumber, email, gym);
        this.salary = salary;
        this.credit = salary;
        this.lastPayed = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
    }

    @Override
    public double getSalary() {
        return this.salary;
    }

    @Override
    public double getCredit() {
        return this.credit;
    }

    @Override
    public Calendar getLastPayed() {
        return this.lastPayed;
    }

    @Override
    public void setSalary(final double salary) {
        this.salary = salary;
    }

    @Override
    public void setCredit(final double credit) {
        this.credit = credit;
    }
    
    @Override
    public void setLastPayed(final Calendar lastPayed) {
        this.lastPayed = lastPayed;
    }

    @Override
    public void updateCredit(final double credit) {
        this.credit += credit;
        this.lastPayed = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
    }
    
    @Override
    public void settleCredit() {
        this.getGym().setIncome(-this.credit, Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY));
        this.credit = 0.0;
    }

    @Override
    public Object[] createRow() {
        return new Object[] { this.getName(), this.getSurname(), this.getFiscalCode(), this.getAddress(), this.getNumber(), this.getEmail(),
                this.getSalary(), this.getCredit() };
    }

}
