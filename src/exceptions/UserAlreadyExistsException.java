package exceptions;

public class UserAlreadyExistsException extends Exception{

	private static final long serialVersionUID = 2368324315553575980L;
	private static final String MESSAGE = "Username già in uso"; 
	
	public UserAlreadyExistsException(){
		super(MESSAGE);
	}

}
