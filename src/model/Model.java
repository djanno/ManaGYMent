package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
//import exceptions.UserAlreadyExistsException;
//import exceptions.WrongUsernameException;

import model.gym.IGym;
import exceptions.UserAlreadyExistsException;
import exceptions.WrongCredentialsException;

/**
 * The model of the application.
 * @author Federico Giannoni
 * @author Simone Letizi
 *
 */
public final class Model implements IModel, Serializable {

	private static final Model SINGLETON_MODEL = new Model();
	
    private static final long serialVersionUID = -7892915667915120441L;

    private final Map<String, IUser> users;

    /**
     * Constructs a new empty model.
     */
    private Model() {
        super();
        this.users = new HashMap<>();
    }

    public static Model getModel(){
    	return SINGLETON_MODEL;
    }
    
    @Override
    public boolean checkAccount(final String username, final char[] psw) throws WrongCredentialsException {
        if (this.users.containsKey(username) && this.users.get(username).getPassword().length == psw.length) {
            for (int i = 0; i < psw.length; i++) {
                if (this.users.get(username).getPassword()[i] != psw[i]) {
                    throw new WrongCredentialsException();
                }
            }
            return true;
        }
        throw new WrongCredentialsException();
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
        if (this.users.containsKey(user.getUsername())) {
            throw new UserAlreadyExistsException();
        } else {
            this.users.put(user.getUsername(), user);
        }
    }

}
