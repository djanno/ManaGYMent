package model;

import java.util.List;

import model.gym.IGym;

public class User implements IUser {
    private final String name;
    private final String surname;
    private final String username;
    private char[] password;
    private final IGym gym;
    private String email;

    public User(final String name, final String surname, final String username, final char[] psw , final IGym gym, final String email){
    	this.name = name;
    	this.surname = surname;
    	this.username = username;
    	this.password = psw;
    	this.gym = gym;
    	this.email = email;
    }

    public String getName(){
        return this.name;
    }

    public String getSurname(){
        return this.surname;
    }

    public String getUsername(){
        return this.username;
    }

    public char[] getPassword(){
        return this.password;
    }

    public IGym getGym(){
        return this.gym;
    }

    public String getEmail(){
        return this.email;
    }

    public void setPassword(final char[] psw){
    	this.password = psw;
    }

    public void setEmail(final String email){
    	this.email = email;
    }
    
    public void sendEmail(final List<String> emailAddresses){
    	
    }

}
