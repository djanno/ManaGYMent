package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import model.gym.IGym;
//import exceptions.UserAlreadyExistsException;
//import exceptions.WrongUsernameException;

public class Model implements IModel, Serializable {
	
	private static final long serialVersionUID = -7892915667915120441L;
	private final Map<String, IUser> users;

    public Model(){
    	super();
    	this.users = new HashMap<>();
    }

    @Override
    public boolean checkAccount(final String username, final char[] psw) throws Exception{//WrongUsernameException{
    	if(this.users.containsKey(username) && this.users.get(username).getPassword().length == psw.length){
    		for(int i = 0; i < psw.length; i++){
    			if(this.users.get(username).getPassword()[i] != psw[i]){
    				return false;
    			}
    		}
    		return true;
    	}
    	throw new Exception("Wrong ID, no match found.");//WrongUsernameException("Wrong ID, no match found.");
    }

	@Override
	public void addUser(final IUser user) throws Exception{ //UserAlreadyExistsException {
		if(this.users.containsKey(user.getUsername())){
    		throw new Exception("Wrong ID, no match found.");//UserAlreadyExistsException("Your ID has already been used.");
    	}
    	else{
    		this.users.put(user.getUsername(), user);
    	}
	}

	@Override
	public IUser getUser(String username) {
		return this.users.get(username);
	}
	

}
