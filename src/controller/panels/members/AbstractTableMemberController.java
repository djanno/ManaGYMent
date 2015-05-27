package controller.panels.members;

import java.util.List;

import javax.swing.JOptionPane;

import model.IModel;
import model.gym.members.IGymMember;
import view.PrimaryFrame;
import view.panels.members.TableMemberPanel;

/**
 * Defines common behaviors of {@link TableMemberPanel},that contains a JTable with row relating to {@link IGymMember} 
 * @author simone
 *
 */
public abstract class AbstractTableMemberController implements IAbstractTableMemberController{
    
    private static final String CONFIRM = "Sei sicuro di voler cancellare il membro selezionato?";
    protected final PrimaryFrame frame;
    protected final IModel model;
    private final TableMemberPanel view;
    
    
    /**
     * @param model
     *          the model
     * @param frame
     *          the application's frame
     * @param view
     *          the view
     */
    public AbstractTableMemberController(final IModel model, final PrimaryFrame frame,  final TableMemberPanel view) {
            this.model = model;
            this.frame = frame;
            this.view = view;
            this.view.attachViewObserver(this);
    }
    
    
    public void createTable(final List<? extends IGymMember> list) {
            this.view.refreshTable();
            list.forEach(member->{
                this.view.addDataRow(member.createRow());
            });
    }

   
    public abstract void addMemberCmd();
   
    public abstract void editMemberCmd(final int index);
    
    protected abstract void deleteMember(final int index);
    
    public void deleteMemberCmd(final int index) {
            final int n = JOptionPane.showConfirmDialog(this.view, CONFIRM, "Conferma", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                    this.deleteMember(index);
            }
    }
    
    public abstract void handlePaymentCmd(final int index);
    
}
