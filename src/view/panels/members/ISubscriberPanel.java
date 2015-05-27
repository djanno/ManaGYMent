package view.panels.members;

import java.util.List;

import model.gym.ICourse;
import model.gym.members.ISubscriber;
import view.panels.login.LoginPanel;
import controller.panels.members.ISubscriberAddController;

/**
 * Interface for {@link SubscriberPanel}.
 * 
 * @author Davide Borficchia
 *
 */

public interface ISubscriberPanel {
	/**
	 * Attachs the observer to the {@link LoginPanel}.
	 * @param observer the observer.
	 */
	void attachObserver(ISubscriberAddController observer);

	/**
	 * 
	 * Gets the CommonPanels to the GUI
	 * 
	 * @return the CommonPanel
	 */
	CommonPanel getCommonPanel();
	
	/**
	 * 
	 * Sets the courses in the view's combobox
	 * 
	 * @param list
	 * 		the courses' list to set in the view
	 */
	void setComboBox(List<ICourse> list);
	
	/**
	 * 
	 * Show data of subscriber in the GUI
	 * 
	 * @param subscriber
	 * 		the subscriber to show
	 */
	void showData(ISubscriber subscriber);

}
