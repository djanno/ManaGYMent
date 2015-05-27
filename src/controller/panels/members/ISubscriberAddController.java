package controller.panels.members;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;

import model.gym.ICourse;
import view.panels.members.IFormField;

/**
 * Defines the {@link SubscriberAddController}.
 * @author Davide Borficchia
 *
 */

public interface ISubscriberAddController {
	
	/**
	 * @return the gym's courses
	 */
	List<ICourse> loadCourses();
	
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
	void cmdSave(final Map<IFormField, String> mapToPass, final Date subscriptionDate, 
			final Date expirationDate, final DefaultListModel<String> list, 
			final Calendar currentSubscriptionCalendar, 
			final Calendar currentExpirationCalendar);
}
