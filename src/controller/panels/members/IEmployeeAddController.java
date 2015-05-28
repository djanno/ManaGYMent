package controller.panels.members;

import java.util.Map;
import view.panels.members.IFormField;

/**
 * Defines the {@link EmployeeAddController}.
 * 
 * @author Davide Borficchia
 *
 */

public interface IEmployeeAddController {

    /**
     * 
     * Saves the employee in the model
     * 
     * @param mapToPass
     *            the employee's commons fields
     * @param salary
     *            the employee's salary
     */
    void cmdSave(final Map<IFormField, String> mapToPass, String salario);

}
