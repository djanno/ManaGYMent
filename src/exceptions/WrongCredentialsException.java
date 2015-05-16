package exceptions;

public class WrongCredentialsException extends Exception {

	private static final long serialVersionUID = 446402473952630665L;
	
	public WrongCredentialsException(final String message){
		super(message);
	}

}
