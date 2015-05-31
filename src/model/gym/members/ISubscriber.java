package model.gym.members;

import java.util.Calendar;
import java.util.List;

import model.gym.ICourse;

/**
 * Interface that defines the behavior of a {@link Subscriber}.
 * @author Federico Giannoni
 * @author Simone Letizi
 *
 */
public interface ISubscriber extends IGymMember {

    /**
     * Returns a {@link Calendar} with the subscription date of the subscriber's membership.
     * @return a Calendar with the subscription date of the subscriber.
     */
    Calendar getSubscriptionDate();

    /**
     * Returns {@link Calendar} with the expiration date of the subscriber's membership.
     * @return a Calendar with the expiration date of the subscriber.
     */
    Calendar getExpirationDate();

    /**
     * Returns the amount the subscriber owes to the {@link Gym} of which he's a member.
     * @return the amount the subscriber owes to the gym.
     */
    double getFee();

    /**
     * Returns whether or not the subscriber's membership is expired.
     * @return true if his/hers membership is expired, false otherwise.
     */
    boolean isExpired();

    /**
     * Returns the list of {@link Course} of which the subscriber is a member.
     * @return the list of courses of which the subscriber is a member.
     */
    List<ICourse> getCourses();

    /**
     * Sets the subscription date of the subscriber.
     * @param subscriptionDate the new subscription date.
     */
    void setSubscriptionDate(final Calendar subscriptionDate);

    /**
     * Sets the expiration date of the subscriber.
     * @param expirationDate the new expiration date.
     */
    void setExpirationDate(final Calendar expirationDate);

    /**
     * Sets the fee of the subscriber.
     * @param fee the new fee.
     */
    void setFee(double fee);

    /**
     * Sets the subscriber's membership status to "expired", if his/hers membership is actually expired.
     */
    void setExpired();

    /**
     * Sets the list of {@link Course} of which the subscriber is a member.
     * @param courses the new list of courses.
     */
    void setCourses(final List<ICourse> courses);

    /**
     * Removes the given {@link Course} from the list of courses of which the subscriber is a member.
     * @param course the course from which the subscriber has to be removed as a member.
     */
    void removeFromCourse(ICourse course);

    /**
     * Removes the {@link Course} mapped to the given index from the list of courses of which the subscriber is a member.
     * @param courseIndex the index of the course from which the subscriber has to be removed as a member.
     */
    void removeFromCourse(int courseIndex);

    /**
     * Settles the debt that the subscriber has towards the gym, updating the balance of the gym for the relevant month.
     */
    void payFee();

}
