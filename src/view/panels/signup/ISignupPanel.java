package view.panels.signup;

import controller.panels.signup.ISignupPanelController;

/**
 * Interface that defines the behavior of a {@link SignupPanel}.
 * @author Federico Giannoni
 *
 */
public interface ISignupPanel {
	
    /**
     * Attaches the specified observer to the panel.
     * @param observer the observer to be attached to the panel.
     */
    void attachObserver(final ISignupPanelController observer);
    
}
