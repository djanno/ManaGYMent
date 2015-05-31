package controller.panels.members.tables;

import javax.swing.JOptionPane;

import model.gym.IGym;
import model.gym.members.ISubscriber;
import view.PrimaryFrame;
import view.panels.members.SubscriberPanel;
import view.panels.members.tables.TableMemberPanel;
import controller.panels.members.SubscriberAddController;
import controller.panels.members.SubscriberEditController;

/**
 * a specification of {@link AbstractTableMemberController} based on a specific
 * members, like Subscribers
 * 
 * @author simone
 */
public class TableSubscribersController extends AbstractTableMemberController {

    protected static final int WIDTH_PANEL = 465;
    protected static final int HEIGHT_PANEL = 387;

    /**
     * Constructor
     * 
     * @param model
     *            the model
     * @param frame
     *            the application's frame
     * @param view
     *            the view
     */
    public TableSubscribersController(final IGym gym, final PrimaryFrame frame, final TableMemberPanel view) {
        super(gym, frame, view);
    }

    /*
     * @see
     * controller.panels.members.AbstractTableMemberController#addMemberCmd()
     */
    @Override
    public void addMemberCmd() {
        final SubscriberPanel addSubscriberPanel = new SubscriberPanel();
        new SubscriberAddController(this.frame, addSubscriberPanel, gym, this);
        frame.new DialogWindow("Aggiungi iscritto", WIDTH_PANEL, HEIGHT_PANEL, this.frame, addSubscriberPanel);

    }

    /*
     * @see
     * controller.panels.members.AbstractTableMemberController#editMemberCmd
     * (int)
     */
    @Override
    public void editMemberCmd(final int index) {
        final SubscriberPanel editSubscriberPanel = new SubscriberPanel();
        new SubscriberEditController(this.frame, editSubscriberPanel, this.gym, this, index).loadData();
        frame.new DialogWindow("Modifica iscritto", WIDTH_PANEL, HEIGHT_PANEL, this.frame, editSubscriberPanel);
    }

    /*
     * @see
     * controller.panels.members.AbstractTableMemberController#deleteMember(int)
     */
    @Override
    protected void deleteMember(final int index) {
        this.gym.removeSubscriber(index);
        this.createTable(this.gym.getSubscribers());
    }

    @Override
    public void handlePaymentCmd(final int index) {
        final ISubscriber subscriber = this.gym.getSubscribers().get(index);
        final int n = JOptionPane.showConfirmDialog(this.frame, "Vuoi confermare il pagamento dell'abbonamento di " + subscriber.getName() + " "
                + subscriber.getSurname() + "?", "Conferma", JOptionPane.OK_CANCEL_OPTION);

        if (n == JOptionPane.OK_OPTION) {
            subscriber.payFee();
            this.createTable(this.gym.getSubscribers());
        }
    }
}
