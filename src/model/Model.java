package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
//import exceptions.UserAlreadyExistsException;
//import exceptions.WrongUsernameException;

import model.gym.IGym;
import exceptions.UserAlreadyExistsException;
import exceptions.WrongCredentialsException;

public class Model implements IModel, Serializable {
	
	private static final long serialVersionUID = -7892915667915120441L;
	
	private final Map<String, IUser> users;

    public Model(){
    	super();
    	this.users = new HashMap<>();
    }

    @Override
    public boolean checkAccount(final String username, final char[] psw) throws WrongCredentialsException {
    	if(this.users.containsKey(username) && this.users.get(username).getPassword().length == psw.length){
    		for(int i = 0; i < psw.length; i++){
    			if(this.users.get(username).getPassword()[i] != psw[i]){
    				throw new WrongCredentialsException("I dati inseriti non sono corretti");
    			}
    		}
    		return true;
    	}
    	throw new WrongCredentialsException("I dati inseriti non sono corretti.");
    }
    
    @Override
    public IUser getUser(final String username) {
    	return this.users.get(username);
    }
    
    @Override
    public IGym getGym(final String userLogged) {
    	return this.users.get(userLogged).getGym();
    }

	@Override
	public void addUser(final IUser user) throws UserAlreadyExistsException {
		if(this.users.containsKey(user.getUsername())){
    		throw new UserAlreadyExistsException("Username già in uso.");
    	}
    	else{
    		this.users.put(user.getUsername(), user);
    	}
	}

}
