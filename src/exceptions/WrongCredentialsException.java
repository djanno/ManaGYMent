package exceptions;

public class WrongCredentialsException extends Exception {

    private static final long serialVersionUID = 446402473952630665L;
    private static final String MESSAGE = "I dati inseriti non sono validi";

    public WrongCredentialsException() {
        super(MESSAGE);
    }

}
