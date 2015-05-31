package model;

import model.gym.IGym;
import exceptions.UserAlreadyExistsException;
import exceptions.WrongCredentialsException;

/**
 * The interface for the model of the application.
 * @author Federico Giannoni
 *
 */
public interface IModel {

    /**
     * Checks if a specified account is present within the model.
     * @param username the username associated to the account.
     * @param psw the password associated to the username.
     * @return true if the account exists, false otherwise.
     * @throws WrongCredentialsException if the specified password is not correct.
     */
    boolean checkAccount(final String username, final char[] psw) throws WrongCredentialsException;

    /**
     * Returns the {@link IUser} associated to the specified username.
     * @param username the username.
     * @return the user associated to the specified username.
     */
    IUser getUser(String username);

    /**
     * Returns the {@link IGym} of the {@link IUser} associated to the username provided in input.
     * @param userLogged the username of the user whose gym is to be returned.
     * @return the gym of the user associated to the specified username. 
     */
    IGym getGym(final String userLogged);

    /**
     * Adds a new {@link IUser} to the model.
     * @param user the user to be added.
     * @throws UserAlreadyExistsException if the username of the user to be added is already associated to another user.
     */
    void addUser(final IUser user) throws UserAlreadyExistsException;

}
