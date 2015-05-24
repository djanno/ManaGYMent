package exceptions;

public class NoCoursesInGymException extends Exception{
    
    private static final long serialVersionUID = -7362722577422550674L;
    private static final String MESSAGE = "La lista dei corsi nella palestra è vuota.Si consiglia di aggiungere prima almeno un corso";

    public NoCoursesInGymException(){
        super(MESSAGE);
    }
}
