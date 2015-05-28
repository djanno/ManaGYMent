package model.gym.members;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import utility.UtilityClass;
import model.gym.ICourse;
import model.gym.IGym;

public class Subscriber extends AbstractGymMember implements ISubscriber, Serializable {

    private static final long serialVersionUID = -1414275393882247088L;

    private Calendar subscriptionDate;
    private Calendar expirationDate;
    private double fee;
    private boolean expired;
    private List<ICourse> courses;

    // Deciso di utilizzare Calendar invece di Date.
    public Subscriber(final String name, final String surname, final String fiscalCode, final String address, final String phoneNumber,
            final String email, final IGym gym, final Calendar subscriptionDate, final Calendar expirationDate, final List<ICourse> courses) {
        super(name, surname, fiscalCode, address, phoneNumber, email, gym);
        this.subscriptionDate = subscriptionDate;
        this.expirationDate = expirationDate;
        this.fee = 0.0;
        this.expired = false;
        this.courses = courses;

        this.courses.forEach(course -> this.fee += course.getCoursePrice() * this.getDays());
    }

    @Override
    public Calendar getSubscriptionDate() {
        return this.subscriptionDate;
    }

    @Override
    public Calendar getExpirationDate() {
        return this.expirationDate;
    }

    @Override
    public double getFee() {
        return this.fee;
    }

    @Override
    public boolean isExpired() {
        return this.expired;
    }

    @Override
    public List<ICourse> getCourses() {
        return UtilityClass.defend(this.courses);
    }

    @Override
    public void setSubscriptionDate(final Calendar subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    @Override
    public void setExpirationDate(final Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public void setFee(final double fee) {
        this.fee = fee;
    }

    @Override
    public void setExpired() {
        this.expired = this.expirationDate.before(Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY));
    }

    @Override
    public void setCourses(final List<ICourse> courses) {
        this.courses = courses;
    }

    @Override
    public void payFee() {
        this.getGym().setIncome(this.fee, this.subscriptionDate);
        this.fee = 0.0;
        // usiamo la data di iscrizione o la data in cui avviene il pagamento?
    }
    
    @Override
    public void removeFromCourse(final ICourse course) {
        this.courses.remove(course);
    }
    
    @Override
    public void removeFromCourse(final int courseIndex) {
        this.courses.remove(courseIndex);
    }

    @Override
    public Object[] createRow() {
        return new Object[] { this.getName(), this.getSurname(), this.getFiscalCode(), this.getAddress(), this.getNumber(), this.getEmail(),
                this.getExpirationDate(), this.getFee() };
    }// sarebbe meglio usare direttamente i campi anzichè i metodi

    private long getDays() {
        return TimeUnit.MILLISECONDS.toDays(this.expirationDate.getTimeInMillis() - this.subscriptionDate.getTimeInMillis());
    }

}
