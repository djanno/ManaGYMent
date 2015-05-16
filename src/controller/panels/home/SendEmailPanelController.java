package controller.panels.home;

import java.util.ArrayList;
import java.util.List;

import model.IModel;
import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;
import controller.panels.home.SenderEmail;
import view.IPrimaryFrame;
import view.panels.home.SendEmailPanel;

public class SendEmailPanelController implements ISendEmailPanelController{

	private final IPrimaryFrame frame; 
	private final SendEmailPanel view;
	private final IModel model;
	
	private static final String INVALID_RECIPIENTS = "Selezionare almeno un gruppo di membri a cui inviare l'email.";
	private static final String SENDED_EMAIL = "E-mail inviate!";
	
	public SendEmailPanelController(final IPrimaryFrame frame, final SendEmailPanel view, final IModel model) {
		this.frame = frame;
		this.view = view;
		this.view.attachObserver(this);
		this.model = model;
	}
	
	@Override
	public void cmdSend(final String subject, final String body, final boolean employee, final boolean subscriber, final boolean exSubscriber, final char[] password) {
		try {
			new SenderEmail(subject, body, getListRecipients(employee, subscriber, exSubscriber), this.model.getUser(this.frame.getActiveUser()).getEmail(), password).sendEmail();
			this.view.showMessage(SENDED_EMAIL);
		} catch (Exception e) {
			this.frame.displayError(e.getMessage());
		}
	}
	
	private List<String> getListRecipients(final boolean employee, final boolean subscriber, boolean exSubscriber) {
		List<String> destinatari = new ArrayList<String>();
		if (subscriber == false && employee == false && exSubscriber == false) {
			throw new IllegalArgumentException(INVALID_RECIPIENTS);
		} else {
			if (subscriber == true) {
				for (ISubscriber s : this.model.getUser(this.frame.getActiveUser()).getGym().getSubscribers()) {
					if (!s.isExpired()) {
						destinatari.add(s.getEmail());
					}
				}
			}
			if (employee == true) {
				for (IEmployee s : this.model.getUser(this.frame.getActiveUser()).getGym().getEmployees()){
					destinatari.add(s.getEmail());
				}
			}
			if (exSubscriber == true) {
				for (ISubscriber s : this.model.getUser(this.frame.getActiveUser()).getGym().getSubscribers()) {
					if (s.isExpired()) {
						destinatari.add(s.getEmail());
					}
				}
			}
		}
		return destinatari;
	}
}
