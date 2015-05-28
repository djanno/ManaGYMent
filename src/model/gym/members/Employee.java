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
        this.credit += credit;
        this.lastPayed = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
    }

    @Override
    public void setLastPayed(final Calendar lastPayed) {
        this.lastPayed = lastPayed;
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
