package exceptions;

/**
 * @author simone
 * Thrown when try to adds user already existing
 *
 */
public class UserAlreadyExistsException extends Exception{

	private static final long serialVersionUID = 2368324315553575980L;
	private static final String MESSAGE = "Username già in uso"; 
	
	/**
	* Constructs an empty UserAlreadyExistsException with default message. 
	*/
	public UserAlreadyExistsException(){
		super(MESSAGE);
	}

}
