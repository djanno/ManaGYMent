package model;

import java.io.Serializable;

import model.gym.IGym;

/**
 * User of the application.
 * @author Federico Giannoni
 *
 */
public class User implements IUser, Serializable {

    private static final long serialVersionUID = -3416885631818258828L;

    private final String name;
    private final String surname;
    private final String username;
    private char[] password;
    private final IGym gym;
    private String email;

    /**
     * Constructs a new user with the informations provided in input.
     * @param name the name of the new user.
     * @param surname the surname of the new user.
     * @param username the username associated to the new user.
     * @param psw the password of the new user.
     * @param gym the gym associated to the new user.
     * @param email the email of the new user.
     */
    public User(final String name, final String surname, final String username, final char[] psw, final IGym gym, final String email) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = psw;
        this.gym = gym;
        this.email = email;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public char[] getPassword() {
        return this.password;
    }

    @Override
    public IGym getGym() {
        return this.gym;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setPassword(final char[] psw) {
        this.password = psw;
    }

    @Override
    public void setEmail(final String email) {
        this.email = email;
    }

}
