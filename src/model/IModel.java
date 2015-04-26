package model;

import model.gym.IGym;
//import exceptions.UserAlreadyExistsException;
//import exceptions.WrongUsernameException;

public interface IModel {

    boolean checkAccount(final String username, final char[] psw) throws Exception;//WrongUsernameException;

    void addUser(final IUser user) throws Exception;//UserAlreadyExistsException;
    
    IUser getUser(String username);

}
