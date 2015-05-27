package controller.panels.members;

import java.util.Map;

import javax.swing.JOptionPane;

import model.gym.IGym;
import model.gym.members.IEmployee;
import view.IPrimaryFrame;
import view.panels.members.EmployeePanel;
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
	 * 		the primary frame
	 * @param employeeView
	 * 		the employee panel
	 * @param model
	 * 		the model
	 * @param tableEmployeesController
	 * 		the table employees controller
	 * @param index
	 * 		the index of the employee to edit
	 */
	public EmployeeEditController(final IPrimaryFrame frame, final IEmployeePanel employeeView, final IGym gym, final TableEmployeesController tableEmployeesController, final int index) {
		super(frame, employeeView, gym, tableEmployeesController);
		this.index = index;
		this.exEmployee = this.gym.getEmployees().get(this.index);	
	}	
	
	@Override
	public void cmdSave(final Map<IFormField, String> mapToPass, final String salario) {
		this.gym.removeEmployee(this.index);
		try{
			final int n = JOptionPane.showConfirmDialog(this.frame.getChild(), "Vuoi confermare le modifiche fatte ?", "Scegli", JOptionPane.OK_CANCEL_OPTION);
			if(n == JOptionPane.OK_OPTION) {
				super.cmdSave(mapToPass, salario);
			}
			else {
				this.gym.addEmployee(this.index, exEmployee);
				this.tableEmployeesController.createTable(this.gym.getEmployees());
			}
			
			//this.model.getUser(this.frame.getActiveUser()).getGym().setIncome(exEmployee.getSalary() - Double.parseDouble(salario) , Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY));
		}catch (IllegalArgumentException e){
			this.gym.addEmployee(this.index, exEmployee);
			this.frame.displayError(e.getMessage());
			this.tableEmployeesController.createTable(this.gym.getEmployees());
		}
	}

	@Override
	public void loadData(){
		view.showData(this.gym.getEmployees().get(this.index));
	}
}
