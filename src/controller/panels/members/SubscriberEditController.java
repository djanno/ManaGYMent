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
import view.PrimaryFrame;
import view.panels.members.IFormField;
import view.panels.members.ISubscriberPanel;
import exceptions.CourseIsFullException;

public class SubscriberEditController extends SubscriberAddController implements ISubscriberEditController{

	private final ISubscriber exSubscriber;
	private final int index;	
	private final Calendar exceptionCalendar;
	
	public SubscriberEditController (final PrimaryFrame frame, final ISubscriberPanel subscriberView, final IModel model, final TableSubscribersController tableSubscribersController, final int index){
		super(frame, subscriberView, model, tableSubscribersController);
		this.exceptionCalendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
		this.exceptionCalendar.set(Calendar.YEAR,1900);
		this.exceptionCalendar.set(Calendar.MONTH,1);
		this.exceptionCalendar.set(Calendar.DAY_OF_MONTH,1);
		this.index = index;
		this.exSubscriber = this.model.getUser(this.frame.getActiveUser()).getGym().getSubscribers().get(this.index);
	}
	
	@Override
	public void cmdSave(final Map<IFormField, String> mapToPass, final Date subscriptionDate, 
			final Date expirationDate, final DefaultListModel<String> list,
			final Calendar currentSubscriptionCalendar, final Calendar currentExpirationCalendar) {
		this.model.getUser(this.frame.getActiveUser()).getGym().removeSubscriber(this.index);
		try{
			if(this.exSubscriber.getFee() != 0.0) {
				if (compareCalendars(dateToCalendar(subscriptionDate), this.exSubscriber.getSubscriptionDate())
						&& compareCalendars(dateToCalendar(expirationDate), this.exSubscriber.getExpirationDate())
						&& convertList(list, this.model.getGym(this.frame.getActiveUser()).getCourses()).equals(this.exSubscriber.getCourses())) {
					final int selectedOption = JOptionPane.showConfirmDialog(this.frame.getChild(), "Vuoi confermare le modifiche fatte?", "Scegli", JOptionPane.OK_CANCEL_OPTION);
					//this.removeSubscriberFromCourses(this.exSubscriber);
					if (selectedOption == JOptionPane.YES_OPTION) {
						this.editSubscriber(mapToPass, subscriptionDate, expirationDate, list, this.exSubscriber.getSubscriptionDate(), this.exSubscriber.getExpirationDate());
						//super.cmdSave(mapToPass, subscriptionDate, expirationDate, list);
						//countDecreseIncome(exSubscriber);
					}
					else {
						this.reAddExSubscriberToModel(this.exSubscriber);
						this.tableSubscribersController.createTable(this.model.getGym(this.frame.getActiveUser()).getSubscribers());
					}
				}
				else {
					throw new IllegalArgumentException("Non è possibile modificare l'abbonamento di questo iscritto, in quanto non ha\n"
						+ "ancora pagato la sua quota d'iscrizione (Se si ritiene necessario modificarlo, occorrerà eliminarlo e reinserirlo).");
				}
			}
			else {
				final int selectedOption = JOptionPane.showConfirmDialog(this.frame.getChild(), "Vuoi confermare le modifiche fatte?", "Scegli", JOptionPane.OK_CANCEL_OPTION);
				//this.removeSubscriberFromCourses(this.exSubscriber);
				if (selectedOption == JOptionPane.YES_OPTION) {
					this.editSubscriber(mapToPass, subscriptionDate, expirationDate, list, currentSubscriptionCalendar,
							currentExpirationCalendar);
					//super.cmdSave(mapToPass, subscriptionDate, expirationDate, list);
					//countDecreseIncome(exSubscriber);	
				}
			}
		}catch (IllegalArgumentException e){
			this.reAddExSubscriberToModel(this.exSubscriber);
			this.frame.displayError(e.getMessage());
			this.tableSubscribersController.createTable(this.model.getGym(this.frame.getActiveUser()).getSubscribers());
		}
	}
		
	@Override
	public void loadData(){
		this.view.showData(this.model.getUser(this.frame.getActiveUser()).getGym().getSubscribers().get(this.index));
	}
	
/*	private void removeSubscriberFromCourses(final ISubscriber subscriber){
		//final int indexOfSubscriber = this.model.getGym(this.frame.getActiveUser()).getSubscribers().indexOf(subscriber);
		if(!subscriber.isExpired()) {
			for (final ICourse course : subscriber.getCourses()){
				course.removeMember(course.getCurrentMembers().indexOf(subscriber));
			}
		}
	}*/
	
	private void editSubscriber(final Map<IFormField, String> mapToPass, final Date subscriptionDate, final Date expirationDate, 
			final DefaultListModel<String> list, final Calendar currentSubscriptionCalendar, final Calendar currentExpirationCalendar) {
		final int size = this.model.getGym(this.frame.getActiveUser()).getSubscribers().size();
		super.cmdSave(mapToPass, subscriptionDate, expirationDate, list, currentSubscriptionCalendar, currentExpirationCalendar);
		if(size == this.model.getGym(this.frame.getActiveUser()).getSubscribers().size()) {
			this.reAddExSubscriberToModel(this.exSubscriber);
		}
	}
	
	private void reAddExSubscriberToModel(final ISubscriber exSubscriber) {
		try {
			this.model.getUser(this.frame.getActiveUser()).getGym().addSubscriber(this.index, exSubscriber);
		}catch(CourseIsFullException e) {
				this.frame.displayError("Non è possibile aggiungere l'iscritto perchè alcuni dei corsi selezionati sono pieni.");
			}
	}
}
