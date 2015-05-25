package exceptions;

public class NoCourseWithCoachesException extends Exception{

    private static final long serialVersionUID = -5743359486423883844L;
    private static final String MESSAGE = "Non esistono corsi che hanno dei coach associati";

    public NoCourseWithCoachesException(){
        super(MESSAGE);
    }
}
