package view.panels.email;

import controller.panels.email.ISendEmailPanelController;

/**
 * Interface for {@link SendEmailPanel}.
 * 
 * @author Davide Borficchia
 *
 */

public interface ISendEmailPanel {

	/**
	 * Attach the observer to this panel.
	 * @param observer the observer
	 */
	public void attachObserver(final ISendEmailPanelController observer);

	/**
	 * 
	 * Open a OptionPane to show a message in the view
	 * 
	 * @param s
	 * 		the massage to show
	 */
	void showMessage(final String s);
	
}
