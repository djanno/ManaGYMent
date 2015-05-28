package controller.panels.email;


/**
 * Defines the {@link SendEmailPanel}.
 * @author Davide Borficchia
 *
 */

public interface ISendEmailPanelController {
	/**
	 * Creates new SenderEmail
	 * 
	 * @param subject
	 * 		the email's subject
	 * @param body
	 * 		the email's body
	 * @param subscriber
	 *		 true if you want to send email to subscriber, false  otherwise	
	 * @param employee
	 * 		true if you want to send email to employee, false  otherwise
	 * @param exSubscriber
	 * 		true if you want to send email to expired subscriber, false  otherwise
	 * @param password
	 * 		the email's password
	 */
	void cmdSend(final String subject, final String body, final boolean subscriber, final boolean employee, 
			final boolean exSubscriber, final char[] password);
}
