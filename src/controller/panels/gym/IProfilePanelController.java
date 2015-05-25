package controller.panels.gym;

/**
 * Defines the {@link ProfilePanel}.
 * @author Davide Borficchia
 *
 */

public interface IProfilePanelController {
	/**
	 * Edit the current user's password.
	 **/
	void cmdEditPsw(final char[] psw, final char[] confirmPsw);
	
	/**
	 * Edit the current user's email.
	 **/
	void cmdEditEmail(final String email, final String confirmEmail);
	
	/**
	 * Close the dialog.
	 **/
	void cmdCancel();
	
}
