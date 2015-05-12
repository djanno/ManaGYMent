package controller.panels.members;


import model.IModel;
import model.gym.members.IEmployee;
import view.PrimaryFrame;
import view.panels.members.TableMemberPanel;

public class TableEmployeesController extends AbstractTableMemberController{

//    volendo il campo model si potrebbe usare nelle classi figlie,cosi come userLogged
    public TableEmployeesController(final PrimaryFrame frame,final IModel model,
            final TableMemberPanel view) {
        super(frame, model, view);
    }

    @Override
    public void addMemberCmd() {
        final EmployeePanel addEmployeePanel= new EmployeePanel();
        new EmployeeEditorController(this.frame,addEmployeePanel,this.model);
        frame.new DialogWindow("Aggiungi Impiegato", 455, 360, this.frame, addEmployeePanel);
    }

    @Override
    public void editMemberCmd(int index) {
        final EmployeePanel editEmployeePanel= new EmployeePanel();
        new EmployeeEditorController(this.frame,editEmployeePanel,this.model).loadData(index);
        frame.new DialogWindow("Modifica Impiegato", 455, 360, this.frame, editEmployeePanel);
    }

    @Override
    public void deleteMember(int index) {
        final IEmployee employee=this.model.getUser(this.frame.getActiveUser()).getGym().getEmployees().get(index);
//        sarebbe da rifare removeEmployee and removeSubscriber nel modello che prendono come ingresso un indice
        this.model.getUser(this.frame.getActiveUser()).getGym().removeEmployee(employee);
        this.createTable(this.model.getUser(this.frame.getActiveUser()).getGym().getEmployees());
    }

}
