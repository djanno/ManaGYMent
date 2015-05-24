package controller.panels.members;

import model.IModel;
import model.gym.members.ISubscriber;
import view.PrimaryFrame;
import view.panels.members.SubscriberPanel;
import view.panels.members.TableMemberPanel;

public class TableSubscribersController extends AbstractTableMemberController {

    protected static final int WIDTH_PANEL = 465;
    protected static final int HEIGHT_PANEL = 387;

    public TableSubscribersController(final IModel model,
            final PrimaryFrame frame, final TableMemberPanel view) {
        super(model, frame, view);
    }

    @Override
    public void addMemberCmd() {
        final SubscriberPanel addSubscriberPanel = new SubscriberPanel();
        new SubscriberAddController(this.frame, addSubscriberPanel, model, this);
        frame.new DialogWindow("Aggiungi iscritto", WIDTH_PANEL, HEIGHT_PANEL, this.frame, addSubscriberPanel);

    }

    @Override
    public void editMemberCmd(final int index) {
        final SubscriberPanel editSubscriberPanel = new SubscriberPanel();
        new SubscriberEditController(this.frame, editSubscriberPanel, this.model, this, index).loadData();
        frame.new DialogWindow("Modifica iscritto", WIDTH_PANEL, HEIGHT_PANEL, this.frame, editSubscriberPanel);
    }

    @Override
    public void deleteMember(final int index) {
        ISubscriber subsriber = this.model.getGym(this.frame.getActiveUser()).getSubscribers().get(index);
        this.model.getGym(this.frame.getActiveUser()).getCourses()
                .stream()
                .filter(course -> course.getCurrentMembers().contains(subsriber))
                .forEach(course -> course.removeMember(subsriber));
        this.model.getUser(this.frame.getActiveUser()).getGym().removeSubscriber(index);
        this.createTable(this.model.getUser(this.frame.getActiveUser()).getGym().getSubscribers());
    }
}
