package exceptions;


/**
 * Exception thrown when a user provides invalid login credentials.
 * @author Federico Giannoni
 *
 */
public class WrongCredentialsException extends Exception {

	private static final long serialVersionUID = 446402473952630665L;
	private static final String MESSAGE = "I dati inseriti non sono validi";
	
	/**
	 * Constructs a WrongCredentialsException with a default message.
	 */
	public WrongCredentialsException(){
		super(MESSAGE);
	}

}
