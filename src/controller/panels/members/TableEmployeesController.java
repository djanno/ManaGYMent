package controller.panels.members;

import javax.swing.JOptionPane;

import model.IModel;
import model.gym.ICourse;
import model.gym.IGymCalendar;
import model.gym.Pair;
import model.gym.members.IEmployee;
import view.PrimaryFrame;
import view.panels.members.EmployeePanel;
import view.panels.members.TableMemberPanel;

public class TableEmployeesController extends AbstractTableMemberController{
	
	protected static final int WIDTH_PANEL = 460;
    protected static final int HEIGHT_PANEL = 257;
//    volendo il campo model si potrebbe usare nelle classi figlie,cosi come userLogged
    public TableEmployeesController(final IModel model, final PrimaryFrame frame,
            final TableMemberPanel view) {
        super(model, frame, view);
    }

    @Override
    public void addMemberCmd() {
        final EmployeePanel addEmployeePanel= new EmployeePanel();
        new EmployeeAddController(this.frame, addEmployeePanel, this.model, this);
        frame.new DialogWindow("Aggiungi Impiegato", WIDTH_PANEL, HEIGHT_PANEL, this.frame, addEmployeePanel);
    }

    @Override
    public void editMemberCmd(final int index) {
        final EmployeePanel editEmployeePanel= new EmployeePanel();
        new EmployeeEditController(this.frame,editEmployeePanel,this.model, this, index).loadData();
        frame.new DialogWindow("Modifica Impiegato", WIDTH_PANEL, HEIGHT_PANEL, this.frame, editEmployeePanel);
    }

    @Override
    public void deleteMember(final int index) {
    	final IEmployee employeeToDelete = model.getGym(frame.getActiveUser()).getEmployees().get(index);
		final IGymCalendar calendar = model.getGym(frame.getActiveUser()).getProgram();
    	
    	final Thread agent = new Thread() {
    		
    		@Override
    		public void run() {
    			model.getGym(frame.getActiveUser()).getCourses().stream().filter(course -> course.getCoaches()
    	    			.contains(employeeToDelete)).forEach(course -> {
    	    				course.removeCoach(course.getCoaches().indexOf(employeeToDelete));
    	    				calendar.getCalendar().forEach((day, schedule) -> schedule.deletePair(new Pair<ICourse, IEmployee>(course, employeeToDelete)));
    	    			});
    		}
    	};
    	
    	agent.start();
        this.model.getGym(this.frame.getActiveUser()).removeEmployee(index);
        this.createTable(this.model.getGym(this.frame.getActiveUser()).getEmployees());
    }
    
    public void handlePaymentCmd(final int index) {
    	final IEmployee employeeToPay = this.model.getGym(this.frame.getActiveUser()).getEmployees().get(index);
    	final int n = JOptionPane.showConfirmDialog(this.frame, "Vuoi confermare il pagamento del salario di " + employeeToPay.getName() + " " + employeeToPay.getSurname() + "?",
    			"Conferma", JOptionPane.OK_CANCEL_OPTION);
    	
    	if(n == JOptionPane.OK_OPTION) {
    		employeeToPay.settleCredit();
    		this.createTable(this.model.getGym(this.frame.getActiveUser()).getEmployees());
    	}
    	
    }

}
