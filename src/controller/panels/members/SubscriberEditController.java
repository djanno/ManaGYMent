package controller.panels.members;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import model.IModel;
import model.gym.members.ISubscriber;
import view.IPrimaryFrame;
import view.panels.members.IFormField;
import view.panels.members.ISubscriberPanel;

public class SubscriberEditController extends SubscriberAddController implements ISubscriberEditController{

	private ISubscriber exSubscriber;
	private final int index;	
	private Calendar exceptionCalendar;
	
	public SubscriberEditController (final IPrimaryFrame frame, final ISubscriberPanel subscriberView, final IModel model, final TableSubscribersController tableSubscribersController, final int index){
		super(frame, subscriberView, model, tableSubscribersController);
		this.exceptionCalendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
		this.exceptionCalendar.set(Calendar.YEAR,1900);
		this.exceptionCalendar.set(Calendar.MONTH,1);
		this.exceptionCalendar.set(Calendar.DAY_OF_MONTH,1);
		this.index = index;
		this.exSubscriber = this.model.getUser(this.frame.getActiveUser()).getGym().getSubscribers().get(this.index);
	}
	
	@Override
	public void cmdSave(final Map<IFormField, String> mapToPass, final Date subscriptionDate, final Date expirationDate, final DefaultListModel<String> list) {
		this.model.getUser(this.frame.getActiveUser()).getGym().removeSubscriber(this.index);
		try{
			if (!subscriptionDate.equals(exSubscriber.getSubscriptionDate().getTime())){

				int selectedOption = JOptionPane.showConfirmDialog(null, "La data di iscrizione è stata modificata, confermi?", "Scegli", JOptionPane.YES_NO_OPTION); 
				if (selectedOption == JOptionPane.YES_OPTION) {
					super.cmdSave(mapToPass, subscriptionDate, expirationDate, list);
					countDecreseIncome(exSubscriber);
				}else{
					super.cmdSave(mapToPass, exSubscriber.getSubscriptionDate().getTime(), expirationDate, list);
				}
			}else{
				super.cmdSave(mapToPass, exSubscriber.getSubscriptionDate().getTime(), expirationDate, list);
			}
		}catch (IllegalArgumentException e){
			this.model.getUser(this.frame.getActiveUser()).getGym().addSubscriber(exSubscriber);
			System.out.println("exception cmdsave editcontroller" + e.getMessage());
//			super.view.showException(e.getMessage());
		}
	}
		
	@Override
	public void loadData(){
		this.view.showData(this.model.getUser(this.frame.getActiveUser()).getGym().getSubscribers().get(this.index));
	}
}
