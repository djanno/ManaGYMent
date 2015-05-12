package controller.panels.signup;

import java.util.Map;

import view.panels.signup.ISignupField;

public interface ISignupPanelController {
	
	void cmdSignup(final Map<ISignupField, String> mapToPass);
	
	void cmdCancel();	
}
