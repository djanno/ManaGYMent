package controller.panels.members;


import model.IModel;
import view.PrimaryFrame;
import view.panels.members.EmployeePanel;
import view.panels.members.TableMemberPanel;

public class TableEmployeesController extends AbstractTableMemberController{

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
    public void editMemberCmd(int index) {
        final EmployeePanel editEmployeePanel= new EmployeePanel();
        new EmployeeEditController(this.frame,editEmployeePanel,this.model, this, index).loadData(index);
        frame.new DialogWindow("Modifica Impiegato", WIDTH_PANEL, HEIGHT_PANEL, this.frame, editEmployeePanel);
    }

    @Override
    public void deleteMember(int index) {
        this.model.getUser(this.frame.getActiveUser()).getGym().removeEmployee(index);
        this.createTable(this.model.getUser(this.frame.getActiveUser()).getGym().getEmployees());
    }

}
