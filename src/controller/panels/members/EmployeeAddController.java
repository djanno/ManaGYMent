package controller.panels.members;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.swing.DefaultListModel;

import model.IModel;
import model.gym.ICourse;
import model.gym.members.Employee;
import model.gym.members.IEmployee;
import view.IPrimaryFrame;
import view.panels.members.IEmployeePanel;
import view.panels.members.IFormField;

public class EmployeeAddController extends BaseController implements IEmployeeAddController{

	private static final String WRONG_PREDICATE = "Tutti i campi devono essere riempiti."
			+ "\nIl codice fiscale deve essere di 15 caratteri. "
			+ "\nL'indirizzo deve essere di alemno 7 caratteri."
			+ "\nL'indirizzo emaile il numero di telefono devono essere valido.";
	private static final String WRONG_SALARY = "Il campo salario deve essere un numero maggiore di 0";
	private static final String EMPTY_LIST = "Bisogna aggiungere almeno un corso.";
	private static final String REGISTERED = "L'utente inserito è stato aggiunto";
	
	protected final IEmployeePanel view;
	protected IPrimaryFrame frame;
	protected final IModel model;
	protected final TableEmployeesController tableEmployeesController;
	
	public EmployeeAddController (final IPrimaryFrame frame, final IEmployeePanel employeeView, final IModel model, final TableEmployeesController tableEmployeesController){
		super();
		this.frame = frame;
		this.view = employeeView;
		this.model = model;
		this.tableEmployeesController = tableEmployeesController;
		this.view.attachObserver(this);
		this.view.setComboBox(this.model.getUser(this.frame.getActiveUser()).getGym().getCourses());
	}
	
	@Override
	public void cmdSave(Map<IFormField, String> mapToPass, String salario, DefaultListModel<String> list ){
		try{
			IEmployee employee = createEmployee(mapToPass, salario, list);
			this.model.getUser(this.frame.getActiveUser()).getGym().addEmployee(employee);
			System.out.println(REGISTERED);
			tableEmployeesController.createTable(this.model.getUser(this.frame.getActiveUser()).getGym().getEmployees());
			this.model.getUser(this.frame.getActiveUser()).getGym().setIncome(employee.getSalary(), Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY));
			this.frame.getChild().closeDialog();
		}catch (Throwable t){
			this.frame.displayError(t.getMessage());
		}
	}
	
	@Override
	public List<ICourse> loadCourses(){
		return this.model.getUser(this.frame.getActiveUser()).getGym().getCourses();
	}
	
	private Employee createEmployee(Map<IFormField, String> mapToPass, String salary, DefaultListModel<String> list) throws Throwable{
		Double doubleSalary;
		try{
			doubleSalary = Double.parseDouble(salary);
		}catch (NumberFormatException e){
			throw new IllegalArgumentException(WRONG_SALARY);
		}
		
		for(IFormField f : mapToPass.keySet()){
			if(! f.getPred().test(mapToPass.get(f))){
				throw new IllegalArgumentException(WRONG_PREDICATE);
			}
		}
		if(list.isEmpty()){
			throw new IllegalArgumentException(EMPTY_LIST);
		}
		
		String name = new String();
		String surname = new String();
		String fiscalCode = new String();
		String address = new String();
		String phoneNumber = new String();
		String email = new String();

		for (IFormField f : mapToPass.keySet()){
			switch (f.getField()){
				case "Nome":  name = new String( mapToPass.get(f));
	            break;
				case "Cognome":  surname = new String( mapToPass.get(f));
	        	break;
				case "Codice fiscale":  fiscalCode = new String( mapToPass.get(f));
	        	break;
				case "Indirizzo":  address = new String( mapToPass.get(f));
	        	break;
				case "Telefono":  phoneNumber = new String( mapToPass.get(f));
	        	break;
				case "E-Mail":  email = new String( mapToPass.get(f));
	        	break;
			}
		}
		return new Employee(name, surname, fiscalCode, address, phoneNumber, email, this.model.getUser(this.frame.getActiveUser()).getGym(), doubleSalary, convertList(list, this.model.getUser(this.frame.getActiveUser()).getGym().getCourses()));
	}
}
