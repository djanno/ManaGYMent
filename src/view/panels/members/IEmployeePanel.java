package view.panels.members;

import model.gym.members.IEmployee;
import controller.panels.members.IEmployeeAddController;

public interface IEmployeePanel {

    /**
     * Attachs the observer to the {@link EmployeePanel}.
     * 
     * @param observer
     *            the observer.
     */
    void attachObserver(IEmployeeAddController observer);

    /**
     * 
     * Gets the CommonPanel from the GUI
     * 
     * @return the CommonPanel
     */
    CommonPanel getCommonPanel();

    /**
     * 
     * Show the employee's data in the GUI
     * 
     * @param employee
     *            the employee to show
     * @param courses
     *            the
     */
    void showData(final IEmployee employee);
}
