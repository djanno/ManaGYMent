package controller.panels.members;

import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import model.IModel;
import model.gym.members.IEmployee;
import view.IPrimaryFrame;
import view.panels.members.IEmployeePanel;
import view.panels.members.IFormField;

public class EmployeeEditController extends EmployeeAddController implements IEmployeeEditController{

	private final IEmployee exEmployee;
	private final int index;

	public EmployeeEditController(final IPrimaryFrame frame, final IEmployeePanel employeeView, final IModel model, final TableEmployeesController tableEmployeesController, final int index) {
		super(frame, employeeView, model, tableEmployeesController);
		this.index = index;
		this.exEmployee = this.model.getUser(this.frame.getActiveUser()).getGym().getEmployees().get(this.index);	
	}	
	
	@Override
	public void cmdSave(final Map<IFormField, String> mapToPass, final String salario, final DefaultListModel<String> list) {
		this.model.getUser(this.frame.getActiveUser()).getGym().removeEmployee(this.index);
		try{
			final int n = JOptionPane.showConfirmDialog(this.frame.getChild(), "Vuoi confermare le modifiche fatte ?", "Scegli", JOptionPane.OK_CANCEL_OPTION);
			if(n == JOptionPane.OK_OPTION) {
				super.cmdSave(mapToPass, salario);
			}
			else {
				this.model.getGym(this.frame.getActiveUser()).addEmployee(this.index, exEmployee);
				this.tableEmployeesController.createTable(this.model.getGym(this.frame.getActiveUser()).getEmployees());
			}
			
			//this.model.getUser(this.frame.getActiveUser()).getGym().setIncome(exEmployee.getSalary() - Double.parseDouble(salario) , Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY));
		}catch (IllegalArgumentException e){
			this.model.getUser(this.frame.getActiveUser()).getGym().addEmployee(this.index, exEmployee);
			this.frame.displayError(e.getMessage());
			this.tableEmployeesController.createTable(this.model.getGym(this.frame.getActiveUser()).getEmployees());
		}
	}

	@Override
	public void loadData(){
		view.showData(this.model.getUser(this.frame.getActiveUser()).getGym().getEmployees().get(this.index), this.model.getUser(this.frame.getActiveUser()).getGym().getCourses());
	}
}
