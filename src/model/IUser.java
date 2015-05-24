package model;

import java.util.List;

import model.gym.IGym;

public interface IUser {

    String getName();

    String getSurname();

    String getUsername();

    char[] getPassword();

    IGym getGym();

    String getEmail();

    void setPassword(final char[] passw);

    void setEmail(final String email);
    
    void sendEmail(final List<String> emailAddresses);

}
