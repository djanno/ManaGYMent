package controller.panels.gym;

public interface IProfilePanelController {
	
	void cmdEditPsw(final char[] psw, final char[] confirmPsw);
	
	void cmdEditEmail(final String email, final String confirmEmail);
	
	void cmdCancel();
	
}
