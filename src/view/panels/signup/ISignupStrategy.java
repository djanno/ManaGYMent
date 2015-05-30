package view.panels.signup;

import java.util.List;

/**
 * Interface that defines the behavior of a {@link SignupStrategy}.
 * @author Federico Giannoni
 *
 */
public interface ISignupStrategy {

    /**
     * Returns a list containing all the values of the {@link SignupStrategy} enumerator.
     * @return a list containing all the values of the signup strategy enumerator.
     */
    List<ISignupField> fields();

}
