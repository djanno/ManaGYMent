package controller.panels.signup;

import java.util.Map;

import view.panels.signup.ISignupField;

/**
 * Defines the {@link SignupPanelController}.
 * @author Federico Giannoni
 *
 */
public interface ISignupPanelController {
	
	/**
	 * Manages the signup procedure.
	 * @param mapToPass a strategy map containing the credentials provided in input. Each credential is mapped to a respective enumerator value, used to verify if the input is allowed.
	 */
	void cmdSignup(final Map<ISignupField, String> mapToPass);
	
	/**
	 * Builds back the login page and its respective controller.
	 */
	void cmdCancel();	
}
