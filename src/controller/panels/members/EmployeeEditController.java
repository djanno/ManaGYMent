package controller.panels.members;

import java.util.Map;

import javax.swing.JOptionPane;

import model.gym.IGym;
import model.gym.members.IEmployee;
import view.IPrimaryFrame;
import view.panels.members.EmployeePanel;
import view.panels.members.FieldsCommon.EnumFieldsCommon;
import view.panels.members.IEmployeePanel;
import view.panels.members.IFormField;
import controller.panels.members.tables.TableEmployeesController;

/**
 * The controller for {@link EmployeePanel}.
 * 
 * @author Davide Borficchia
 *
 */

public class EmployeeEditController extends EmployeeAddController implements IEmployeeEditController{

    private final IEmployee exEmployee;
    private final int index;

    /**
     * 
     * Constructor
     * 
     * @param frame
     *              the primary frame
     * @param employeeView
     *              the employee panel
     * @param gym
     *              the gym
     * @param tableEmployeesController
     *              the table employees controller
     * @param index
     *              the index of the employee to edit
     */
    public EmployeeEditController(final IPrimaryFrame frame, final IEmployeePanel employeeView, final IGym gym, final TableEmployeesController tableEmployeesController, final int index) {
            super(frame, employeeView, gym, tableEmployeesController);
            this.index = index;
            this.exEmployee = this.gym.getEmployees().get(this.index);      
    }       
    
    @Override
    public void cmdSave(final Map<IFormField, String> mapToPass, final String salario) {
            try{
                    final int n = JOptionPane.showConfirmDialog(this.frame.getChild(), "Vuoi confermare le modifiche fatte ?", "Scegli", JOptionPane.OK_CANCEL_OPTION);
                    if (n == JOptionPane.OK_OPTION) {
                        this.editEmployee(mapToPass, salario);
                        this.tableEmployeesController.createTable(this.gym.getEmployees());
                    }
                    
            }catch (IllegalArgumentException e){
                this.frame.displayError(e.getMessage());
                this.tableEmployeesController.createTable(this.gym.getEmployees());
            }
    }

    @Override
    public void loadData(){
            view.showData(this.gym.getEmployees().get(this.index));
    }
    
    /**
     * Edits the employee in the model
     * 
     * @param mapToPass
     *              the employee's common fields
     * @param salary
     *              the salary
     */
    private void editEmployee(final Map<IFormField, String> mapToPass, final String salary) {
            
            if(this.exEmployee.getSalary() == this.exEmployee.getCredit()) {        
                    this.gym.removeEmployee(this.index);
                    final int size = this.gym.getEmployees().size();
                    super.cmdSave(mapToPass, salary);
                    if(size == this.gym.getEmployees().size()) {
                            this.gym.addEmployee(this.index, exEmployee);
                    }
            }
            
            else {
                    final Map<IFormField, String> fields = this.getCommonFields(mapToPass);
                    try {
                            Double.parseDouble(salary);
                    } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Il salario specificato non è un numero valido.");
                    }

                    this.exEmployee.setName(fields.get(EnumFieldsCommon.NOME).trim());
                    this.exEmployee.setSurname(fields.get(EnumFieldsCommon.COGNOME).trim());
                    this.exEmployee.setFiscalCode(fields.get(EnumFieldsCommon.CODICE_FISCALE).trim());
                    this.exEmployee.setAddress(fields.get(EnumFieldsCommon.INDIRIZZO).trim());
                    this.exEmployee.setNumber(fields.get(EnumFieldsCommon.TELEFONO).trim());
                    this.exEmployee.setEmail(fields.get(EnumFieldsCommon.EMAIL).trim());
                    this.exEmployee.setSalary(Double.parseDouble(salary));
                    
                    this.frame.getChild().closeDialog();
            }
    }
}
