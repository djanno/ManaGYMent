package controller.panels.members;

import java.util.Map;

import model.IModel;
import model.gym.members.Employee;
import model.gym.members.IEmployee;
import view.IPrimaryFrame;
import view.panels.members.EmployeePanel;
import view.panels.members.FieldsCommon.EnumFieldsCommon;
import view.panels.members.IEmployeePanel;
import view.panels.members.IFormField;

/**
 * The controller for {@link EmployeePanel}.
 * 
 * @author Davide Borficchia
 *
 */

public class EmployeeAddController extends BaseController implements IEmployeeAddController{

	private static final String WRONG_SALARY = "Il salario specificato non è un numero valido";
	
	protected final IEmployeePanel view;
	protected IPrimaryFrame frame;
	protected final IModel model;
	protected final TableEmployeesController tableEmployeesController;

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
	 */
	public EmployeeAddController (final IPrimaryFrame frame, final IEmployeePanel employeeView, final IModel model, final TableEmployeesController tableEmployeesController){
		super();
		this.frame = frame;
		this.view = employeeView;
		this.model = model;
		this.tableEmployeesController = tableEmployeesController;
		this.view.attachObserver(this);
	}
	
	@Override
	public void cmdSave(final Map<IFormField, String> mapToPass, final String salario){
		try{
			final IEmployee employee = createEmployee(mapToPass, salario);
			this.model.getUser(this.frame.getActiveUser()).getGym().addEmployee(employee);
			tableEmployeesController.createTable(this.model.getUser(this.frame.getActiveUser()).getGym().getEmployees());
			this.frame.getChild().closeDialog();
		}catch (Exception e){
			this.frame.displayError(e.getMessage());
		}
	}
	
	/**
	 * 
	 * Creates new employee from GUI
	 * 
	 * @param mapToPass
	 * 		the common fields of employee
	 * @param salary
	 * 		the employee's salary
	 * @return the new employee
	 * @throws IllegalArgumentException
	 */
	
	private Employee createEmployee(final Map<IFormField, String> mapToPass, final String salary) throws IllegalArgumentException {
		Double doubleSalary;
		try{
			doubleSalary = Double.parseDouble(salary);
		}catch (NumberFormatException e){
			throw new IllegalArgumentException(WRONG_SALARY);
		}
		
		final Map<IFormField, String> fields = this.getCommonFields(mapToPass);
		
		return new Employee(fields.get(EnumFieldsCommon.NOME), fields.get(EnumFieldsCommon.COGNOME), 
				fields.get(EnumFieldsCommon.CODICE_FISCALE), fields.get(EnumFieldsCommon.INDIRIZZO), 
				fields.get(EnumFieldsCommon.TELEFONO), fields.get(EnumFieldsCommon.EMAIL), 
				this.model.getUser(this.frame.getActiveUser()).getGym(), doubleSalary);
	}
}
