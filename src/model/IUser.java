package model;

import model.gym.IGym;

/**
 * The interface that defines the behavior of a {@link User}.
 * @author Federico Giannoni
 * 
 */
public interface IUser {

    /**
     * Returns the name of a user.
     * @return the name of a user.
     */
    String getName();

    /**
     * Returns the surname of a user.
     * @return the surname of a user.
     */
    String getSurname();

    /**
     * Returns the username associated to a user.
     * @return the username associated to a user.
     */
    String getUsername();

    /**
     * Returns the password associated to a user.
     * @return the password associated to a user.
     */
    char[] getPassword();

    /**
     * Returns the gym associated to a user.
     * @return the gym associated to a user.
     */
    IGym getGym();

    /**
     * Returns the email associated to a user.
     * @return the email associated to a user.
     */
    String getEmail();

    /**
     * Sets the password provided in input as the new password for a user.
     * @param passw the new password.
     */
    void setPassword(final char[] passw);

    /**
     * Sets the email provided in input as the new email for a user.
     * @param email the new email.
     */
    void setEmail(final String email);
    
}
