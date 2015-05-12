package exceptions;

public class UserAlreadyExistsException extends Exception{

	private static final long serialVersionUID = 2368324315553575980L;
	
	public UserAlreadyExistsException(final String message){
		super(message);
	}

}
