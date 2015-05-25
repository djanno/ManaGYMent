package controller.panels.members;

import java.util.Date;
import java.util.Map;
import javax.swing.DefaultListModel;
import view.panels.members.IFormField;

/**
 * Defines the {@link SubscriberEditController}.
 * @author Davide Borficchia
 *
 */

public interface ISubscriberEditController {
	
	/**
	 * 
	 * Saves the subscriber in the model
	 * 
	 * @param mapToPass
	 * 		the subscriber's commons fields
	 * @param subscriptionDate
	 * 		the subscriber's subscription date 
	 * @param expirationDate
	 * 		the subscriber's expiration date 
	 * @param list
	 * 		the subscriber's courses 
	 */
	void cmdSave(final Map<IFormField, String> mapToPass, final Date subscriptionDate, final Date expirationDate, final DefaultListModel<String> list);
	
	/**
	 * Loads data in the GUI
	 */
	void loadData();
}
