package controller.panels.members;

import java.util.Map;

import view.panels.members.IFormField;

/**
 * Defines the {@link EmployeeEditController}.
 * @author Davide Borficchia
 *
 */

public interface IEmployeeEditController {
	
	/**
	 * 
	 * Saves the employee in the model
	 * 
	 * @param mapToPass
	 * 		the employee's commons fields
	 * @param salary
	 * 		the employee's salary
	 */
	void cmdSave(final Map<IFormField,String> mapToPass, String salario);
	
	/**
	 * Loads data in the GUI
	 */
	void loadData();
}
