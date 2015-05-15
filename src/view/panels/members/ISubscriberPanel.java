package view.panels.members;

import java.util.List;

import model.gym.ICourse;
import model.gym.members.ISubscriber;
import view.panels.login.LoginPanel;
import controller.panels.members.ISubscriberAddController;

public interface ISubscriberPanel {
	/**
	 * Attachs the observer to the {@link LoginPanel}.
	 * @param observer the observer.
	 */

	CommonPanel getCommonPanel();
	
	void setComboBox(List<ICourse> list);
	
	void showData(ISubscriber iSubscriber);
	
	void attachObserver(ISubscriberAddController observer);
}
