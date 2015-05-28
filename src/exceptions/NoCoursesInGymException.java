package exceptions;

/**
 * @author simone Thrown when try to open subscriber page but there isn't any
 *         course in gym to which it want subscribe
 */
public class NoCoursesInGymException extends Exception {

    private static final long serialVersionUID = -7362722577422550674L;
    private static final String MESSAGE = "La lista dei corsi nella palestra è vuota.Si consiglia di aggiungere prima almeno un corso";

    /**
     * Constructs an empty NoCoursesInGymException with default message.
     */
    public NoCoursesInGymException() {
        super(MESSAGE);
    }
}
