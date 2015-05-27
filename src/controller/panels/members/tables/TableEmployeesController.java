package controller.panels.members.tables;

import javax.swing.JOptionPane;

import model.gym.IGym;
import model.gym.members.IEmployee;
import view.PrimaryFrame;
import view.panels.members.EmployeePanel;
import view.panels.members.tables.TableMemberPanel;
import controller.panels.members.EmployeeAddController;
import controller.panels.members.EmployeeEditController;

/**
 * a specification of {@link AbstractTableMemberController} based on a specific members, like Employees
 * @author simone
 *  
 */
public class TableEmployeesController extends AbstractTableMemberController{
	
    protected static final int WIDTH_PANEL = 460;
    protected static final int HEIGHT_PANEL = 257;
//    volendo il campo model si potrebbe usare nelle classi figlie,cosi come userLogged
    
    /**
     * Constructor
     * 
     * @param gym
     *            the gym
     * @param frame
     *            the application's frame
     * @param view
     *            the view
     */
    public TableEmployeesController(final IGym gym, final PrimaryFrame frame,
            final TableMemberPanel view) {
        super(gym, frame, view);
    }

    /* 
     * @see controller.panels.members.AbstractTableMemberController#addMemberCmd()
     */
    @Override
    public void addMemberCmd() {
        final EmployeePanel addEmployeePanel= new EmployeePanel();
        new EmployeeAddController(this.frame, addEmployeePanel, this.gym, this);
        frame.new DialogWindow("Aggiungi Impiegato", WIDTH_PANEL, HEIGHT_PANEL, this.frame, addEmployeePanel);
    }

    /* 
     * @see controller.panels.members.AbstractTableMemberController#editMemberCmd(int)
     */
    @Override
    public void editMemberCmd(final int index) {
        final EmployeePanel editEmployeePanel= new EmployeePanel();
        new EmployeeEditController(this.frame,editEmployeePanel,this.gym, this, index).loadData();
        frame.new DialogWindow("Modifica Impiegato", WIDTH_PANEL, HEIGHT_PANEL, this.frame, editEmployeePanel);
    }

    /* 
     * @see controller.panels.members.AbstractTableMemberController#deleteMember(int)
     */
    @Override
    protected void deleteMember(final int index) {
        final Thread agent = new Thread() {
            
            @Override
            public void run() {
                gym.removeEmployee(index);
                createTable(gym.getEmployees());
            }
        };
        
        agent.start();
        
    }
    
    @Override
    public void handlePaymentCmd(final int index) {
    	final IEmployee employeeToPay = this.gym.getEmployees().get(index);
    	final int n = JOptionPane.showConfirmDialog(this.frame, "Vuoi confermare il pagamento del salario di " + employeeToPay.getName() + " " + employeeToPay.getSurname() + "?",
    			"Conferma", JOptionPane.OK_CANCEL_OPTION);
    	
    	if(n == JOptionPane.OK_OPTION) {
    		employeeToPay.settleCredit();
    		this.createTable(this.gym.getEmployees());
    	}
    	
    }

}
