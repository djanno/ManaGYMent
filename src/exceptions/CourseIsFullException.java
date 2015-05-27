package exceptions;

/**
 * @author simone
 * Thrown when try to subscribe someone but the course have already the number max of subscribers
 *
 */
public class CourseIsFullException extends Exception {

	private static final long serialVersionUID = 2514525621771861881L;
	private static final String MESSAGE = "Il corso ha già raggiunto il massimo degli iscritti";
	
	/**
	* Constructs an empty CourseIsFullException with default message. 
	*/
	public CourseIsFullException() {
		super(MESSAGE);
	}
}
