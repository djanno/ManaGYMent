package view.panels.signup;

/**
 * Interface that defines the behavior of a {@link SignupField}.
 * @author Federico Giannoni
 *
 */
public interface ISignupField {

    /**
     * Returns the name corresponding to the field.
     * @return the name corresponding to the field.
     */
    String getName();

    /**
     * Returns the length of the field.
     * @return the length of the field.
     */
    int getLength();

    /**
     * Returns whether or not this field should be hidden.
     * @return true if the field should be hidden, false otherwise.
     */
    boolean isHidden();

    /**
     * Checks if the String provided in input passes the check for the {@link Predicate} of the field.
     * @param s the string to be verified.
     * @return true if the string has passed the test, false otherwise.
     */
    boolean checkPredicate(final String s);

    /**
     * Returns the tool tip text for the field.
     * @return the tool tip text for the field.
     */
    String getToolTipText();

}
