package model;

import model.gym.IGym;
import exceptions.UserAlreadyExistsException;
import exceptions.WrongCredentialsException;

public interface IModel {

    boolean checkAccount(final String username, final char[] psw) throws WrongCredentialsException;

    IUser getUser(String username);
    
    IGym getGym(final String userLogged);
    
    void addUser(final IUser user) throws UserAlreadyExistsException;
    
}
