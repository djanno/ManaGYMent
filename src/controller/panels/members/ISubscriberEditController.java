package controller.panels.members;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.swing.DefaultListModel;

import view.panels.members.IFormField;

public interface ISubscriberEditController {
	
	void cmdSave(final Map<IFormField, String> mapToPass, final Date subscriptionDate, 
			final Date expirationDate, final DefaultListModel<String> list, 
			final Calendar currentSubscriptionCalendar, 
			final Calendar currentExpirationCalendar);
	
	void loadData();
}
