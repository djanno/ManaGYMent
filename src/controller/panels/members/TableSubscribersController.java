package controller.panels.member;

import model.IModel;
import model.gym.members.ISubscriber;
import view.PrimaryFrame;
import view.panels.members.SubscriberPanel;
import view.panels.members.TableMemberPanel;

public class TableSubscribersController extends AbstractTableMemberController{

//  volendo il campo model si potrebbe usare nelle classi figlie,cosi come userLogged
  public TableSubscribersController(final PrimaryFrame frame,final IModel model,
          final TableMemberPanel view) {
      super(frame, model, view);
  }

  @Override
  public void addMemberCmd() {
      final SubscriberPanel addSubscriberPanel=new SubscriberPanel();
      new SubscriberEditorController(this.frame, addSubscriberPanel, this.model);
      frame.new DialogWindow("Aggiungi iscritto", 455, 360, this.frame, addSubscriberPanel);

  }

  @Override
  public void editMemberCmd(final int index) {
      final SubscriberPanel editSubscriberPanel=new SubscriberPanel();
      new SubscriberEditorController(this.frame, editSubscriberPanel, this.model).loadData(index);
      frame.new DialogWindow("Modifica iscritto", 455, 360, this.frame, editSubscriberPanel);
  }

  @Override
  public void deleteMember(final int index) {
//    sarebbe da rifare removeEmployee and removeSubscriber nel modello che prendono come ingresso un indice
      final ISubscriber subscriber=this.model.getUser(this.frame.getActiveUser()).getGym().getSubscribers().get(index);
      this.model.getUser(this.frame.getActiveUser()).getGym().removeSubscriber(subscriber);
      this.createTable(this.model.getUser(this.frame.getActiveUser()).getGym().getSubscribers());
  }
}
