package exceptions;

/**
 * @author simone
 * Thrown when try to set calendar for a day but no one course have coaches
 *
 */
public class NoCourseWithCoachesException extends Exception{

    private static final long serialVersionUID = -5743359486423883844L;
    private static final String MESSAGE = "Non esistono corsi che hanno dei coach associati";

    /**
     * Constructs an empty NoCourseWithCoachesException with default message. 
     */
    public NoCourseWithCoachesException(){
        super(MESSAGE);
    }
}
