package exceptions;

public class CourseIsFullException extends Exception {

	private static final long serialVersionUID = 2514525621771861881L;
	private static final String MESSAGE = "Il corso ha già raggiunto il massimo degli iscritti";
	
	public CourseIsFullException() {
		super(MESSAGE);
	}
}
