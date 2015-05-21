package exceptions;

public class CourseIsFullException extends Exception {

	private static final long serialVersionUID = 2514525621771861881L;

	public CourseIsFullException(final String message) {
		super(message);
	}
}
