package controller.panels.email;

import java.util.ArrayList;
import java.util.List;

import model.IModel;
import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;
import view.PrimaryFrame;
import view.panels.email.SendEmailPanel;

/**
 * The controller for {@link SendEmailPanel}.
 * 
 * @author Davide Borficchia
 *
 */

public class SendEmailPanelController implements ISendEmailPanelController {

    private final PrimaryFrame frame;
    private final SendEmailPanel view;
    private final IModel model;

    private static final String INVALID_RECIPIENTS = "Selezionare almeno un gruppo di membri a cui inviare l'email.";
    private static final String SENDED_EMAIL = "E-mail inviate!";

    /**
     * @param frame
     *            the Primary frame
     * @param view
     *            the send email panel
     * @param model
     *            the model
     */
    public SendEmailPanelController(final PrimaryFrame frame, final SendEmailPanel view, final IModel model) {
        this.frame = frame;
        this.view = view;
        this.view.attachObserver(this);
        this.model = model;
    }

    @Override
    public void cmdSend(final String subject, final String body, final boolean employee, final boolean subscriber, final boolean exSubscriber,
            final char[] password) {
        try {
            new SenderEmail(subject, body, getListRecipients(employee, subscriber, exSubscriber),
                    this.model.getUser(this.getLoggedUser()).getEmail(), password).sendEmail();
            this.view.showMessage(SENDED_EMAIL);
        } catch (Exception e) {
            this.frame.displayError(e.getMessage());
        }
    }

    /**
     * @param employee
     *            true if you want to send email to employee, false otherwise
     * @param subscriber
     *            true if you want to send email to subscriber, false otherwise
     * @param exSubscriber
     *            true if you want to send email to expired subscriber, false
     *            otherwise
     * @return the list of recipients to send email
     */
    private List<String> getListRecipients(final boolean employee, final boolean subscriber, final boolean exSubscriber) {
        final List<String> destinatari = new ArrayList<String>();
        if (subscriber || employee || exSubscriber) {
            if (subscriber) {
                for (final ISubscriber s : this.model.getUser(this.getLoggedUser()).getGym().getSubscribers()) {
                    if (!s.isExpired()) {
                        destinatari.add(s.getEmail());
                    }
                }
            }
            if (employee) {
                for (final IEmployee s : this.model.getUser(this.getLoggedUser()).getGym().getEmployees()) {
                    destinatari.add(s.getEmail());
                }
            }
            if (exSubscriber) {
                for (final ISubscriber s : this.model.getUser(this.getLoggedUser()).getGym().getSubscribers()) {
                    if (s.isExpired()) {
                        destinatari.add(s.getEmail());
                    }
                }
            }
        } else {
            throw new IllegalArgumentException(INVALID_RECIPIENTS);
        }
        return destinatari;
    }

    /**
     * Returns the username of the user that is currently logged in. The purpose
     * of this method is to avoid code duplication.
     * 
     * @return the username of the user that is currently logged in.
     */
    private String getLoggedUser() {
        return this.frame.getPrimaryController().getActiveUser();
    }
}
