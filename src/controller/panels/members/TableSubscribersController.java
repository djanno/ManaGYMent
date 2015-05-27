package controller.panels.members;

import javax.swing.JOptionPane;

import model.IModel;
import model.gym.members.ISubscriber;
import view.PrimaryFrame;
import view.panels.members.SubscriberPanel;
import view.panels.members.TableMemberPanel;

public class TableSubscribersController extends AbstractTableMemberController{

  protected static final int WIDTH_PANEL = 465;
  protected static final int HEIGHT_PANEL = 387;
    
  public TableSubscribersController(final IModel model, final PrimaryFrame frame,
          final TableMemberPanel view) {
      super(model, frame, view);
  }

  @Override
  public void addMemberCmd() {
      final SubscriberPanel addSubscriberPanel=new SubscriberPanel();
      new SubscriberAddController(this.frame, addSubscriberPanel, model, this);
      frame.new DialogWindow("Aggiungi iscritto", WIDTH_PANEL, HEIGHT_PANEL, this.frame, addSubscriberPanel);

  }

  @Override
  public void editMemberCmd(final int index) {
      final SubscriberPanel editSubscriberPanel=new SubscriberPanel();
      new SubscriberEditController(this.frame, editSubscriberPanel, this.model, this, index).loadData();
      frame.new DialogWindow("Modifica iscritto", WIDTH_PANEL, HEIGHT_PANEL, this.frame, editSubscriberPanel);
  }

  @Override
  public void deleteMember(final int index) {
      this.model.getUser(this.frame.getActiveUser()).getGym().removeSubscriber(index);
      this.createTable(this.model.getUser(this.frame.getActiveUser()).getGym().getSubscribers());
  }
  
  @Override
  public void handlePaymentCmd(final int index) {
	 final ISubscriber subscriber = this.model.getGym(this.frame.getActiveUser()).getSubscribers().get(index);
  	final int n = JOptionPane.showConfirmDialog(this.frame, "Vuoi confermare il pagamento dell'abbonamento di " + subscriber.getName() + " " + subscriber.getSurname() + "?",
  			"Conferma", JOptionPane.OK_CANCEL_OPTION);
  	
  	if(n == JOptionPane.OK_OPTION) {
  		subscriber.payFee();
  		this.createTable(this.model.getGym(this.frame.getActiveUser()).getSubscribers());
  	}
  }
}
