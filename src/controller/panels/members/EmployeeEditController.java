package controller.panels.members;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.swing.DefaultListModel;

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
			super.cmdSave(mapToPass, salario, list);
			this.model.getUser(this.frame.getActiveUser()).getGym().setIncome(exEmployee.getSalary() - Double.parseDouble(salario) , Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY));
		}catch (IllegalArgumentException e){
			this.model.getUser(this.frame.getActiveUser()).getGym().addEmployee(exEmployee);
//			super.view.showException(e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void loadData(){
		view.showData(this.model.getUser(this.frame.getActiveUser()).getGym().getEmployees().get(this.index), this.model.getUser(this.frame.getActiveUser()).getGym().getCourses());
	}
}
