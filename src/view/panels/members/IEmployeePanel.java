package view.panels.members;

import java.util.List;

import model.gym.ICourse;
import model.gym.members.IEmployee;
import view.panels.login.LoginPanel;
import controller.panels.members.IEmployeeAddController;

public interface IEmployeePanel {
	
	/**
	 * Attachs the observer to the {@link LoginPanel}.
	 * @param observer the observer.
	 */
	
	void attachObserver(IEmployeeAddController observer);
	
	CommonPanel getCommonPanel();
	
	void showData(final IEmployee iEmployee, final List<ICourse> courses);
	
}
