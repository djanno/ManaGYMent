package controller.panels.members;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import model.IModel;
import model.gym.members.Employee;
import model.gym.members.IEmployee;
import view.IPrimaryFrame;
import view.panels.members.IEmployeePanel;
import view.panels.members.IFormField;

/**
 * The controller for {@link EmployeePanel}.
 * 
 * @author Davide Borficchia
 *
 */

public class EmployeeAddController extends BaseController implements IEmployeeAddController{

	private static final String WRONG_NAME = "Il nome deve essere lungo pi� di 1 carattere.";
	private static final String WRONG_SURNAME = "Il cognome deve essere lungo pi� di 1 carattere.";
	private static final String WRONG_CF = "Il codice fiscale deve essere di 15 caratteri esatti.";
	private static final String WRONG_ADDRESS = "L'indirizzo deve essere lungo pi� di 7 caratteri.";
	private static final String WRONG_TELEPHONE = "Il numero telefonico deve essere composto da soli numeri.";
	private static final String WRONG_EMAIL = "L'E-mail inserita non � valida.";
	private static final String WRONG_SALARY = "Il campo salario deve essere un numero maggiore di 0";
	
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
			this.model.getUser(this.frame.getActiveUser()).getGym().setIncome(- employee.getSalary(), Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY));
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
		
		for(final IFormField f : mapToPass.keySet()){
			if(! f.getPred().test(mapToPass.get(f))){	
				if(f.getField().equals("Nome")){
					throw new IllegalArgumentException(WRONG_NAME);
				}
				if(f.getField().equals("Cognome")){
					throw new IllegalArgumentException(WRONG_SURNAME);
				}
				if(f.getField().equals("Codice fiscale")){
					throw new IllegalArgumentException(WRONG_CF);
				}
				if(f.getField().equals("Indirizzo")){
					throw new IllegalArgumentException(WRONG_ADDRESS);
				}
				if(f.getField().equals("Telefono")){
					throw new IllegalArgumentException(WRONG_TELEPHONE);
				}
				if(f.getField().equals("E-Mail")){
					throw new IllegalArgumentException(WRONG_EMAIL);
				}
			}
		}
		
		String name = "";
		String surname = "";
		String fiscalCode = "";
		String address = "";
		String phoneNumber = "";
		String email = "";

		for (final IFormField f : mapToPass.keySet()){
			switch (f.getField()){
				case "Nome":  name = new String( mapToPass.get(f).trim());
					break;
				case "Cognome":  surname = new String( mapToPass.get(f).trim());
	        		break;
				case "Codice fiscale":  fiscalCode = new String( mapToPass.get(f));
	        		break;
				case "Indirizzo":  address = new String( mapToPass.get(f));
	        		break;
				case "Telefono":  phoneNumber = new String( mapToPass.get(f));
	        		break;
				case "E-Mail":  email = new String( mapToPass.get(f));
	        		break;
	        	default:
	        		break;
			}
		}
		return new Employee(name, surname, fiscalCode, address, phoneNumber, email, this.model.getUser(this.frame.getActiveUser()).getGym(), doubleSalary);
	}
}
