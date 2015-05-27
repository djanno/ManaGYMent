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
import view.panels.members.FieldsCommon.EnumFieldsCommon;
import exceptions.CourseIsFullException;

/**
 * The controller for {@link SubscriberPanel}.
 * 
 * @author Davide Borficchia
 *
 */

public class SubscriberEditController extends SubscriberAddController implements ISubscriberEditController{

	private final ISubscriber exSubscriber;
	private final int index;	
	private final Calendar exceptionCalendar; //a cosa cazzo serve?
	
	/**
	 * 
	 * Constructor
	 * 
	 * @param frame
	 * 		the primary frame
	 * @param subscriberView
	 * 		the subscriber panel
	 * @param model
	 * 		the model
	 * @param tableSubscribersController
	 * 		the table subscribers controller
	 * @param index
	 * 		the index of the subscriber to edit
	 */
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
	public void cmdSave(final Map<IFormField, String> mapToPass, final Date subscriptionDate, final Date expirationDate, 
			final DefaultListModel<String> list) {
		
		try{
			final int n = JOptionPane.showConfirmDialog(this.frame.getChild(), "Vuoi confermare le modifiche fatte?", "Scegli",
					JOptionPane.OK_CANCEL_OPTION);
			
			if(n == JOptionPane.OK_OPTION) {
				this.editSubscriber(mapToPass, subscriptionDate, expirationDate, list);
				this.tableSubscribersController.createTable(this.model.getGym(this.frame.getActiveUser()).getSubscribers());
			}

		}catch (IllegalArgumentException e){
			this.frame.displayError(e.getMessage());
			this.tableSubscribersController.createTable(this.model.getGym(this.frame.getActiveUser()).getSubscribers());
		}
	}
		
	@Override
	public void loadData(){
		this.view.showData(this.model.getUser(this.frame.getActiveUser()).getGym().getSubscribers().get(this.index));
	}
	
	private void editSubscriber(final Map<IFormField, String> mapToPass, final Date subscriptionDate, final Date expirationDate, final DefaultListModel<String> list) {
		
		if (compareCalendars(dateToCalendar(subscriptionDate), this.exSubscriber.getSubscriptionDate())
			&& compareCalendars(dateToCalendar(expirationDate), this.exSubscriber.getExpirationDate())
			&& convertList(list, this.model.getGym(this.frame.getActiveUser()).getCourses()).equals(this.exSubscriber.getCourses())) {
			
			final Map<IFormField, String> fields = this.getCommonFields(mapToPass);

			this.exSubscriber.setName(fields.get(EnumFieldsCommon.NOME));
			this.exSubscriber.setSurname(fields.get(EnumFieldsCommon.COGNOME));
			this.exSubscriber.setFiscalCode(fields.get(EnumFieldsCommon.CODICE_FISCALE));
			this.exSubscriber.setAddress(fields.get(EnumFieldsCommon.INDIRIZZO));
			this.exSubscriber.setNumber(fields.get(EnumFieldsCommon.TELEFONO));
			this.exSubscriber.setEmail(fields.get(EnumFieldsCommon.EMAIL));
			
			this.frame.getChild().closeDialog();
		}
		
		else if(this.exSubscriber.getFee() == 0.0) {
			this.model.getGym(this.frame.getActiveUser()).removeSubscriber(this.index);	
			final int size = this.model.getGym(this.frame.getActiveUser()).getSubscribers().size();
			super.cmdSave(mapToPass, subscriptionDate, expirationDate, list);
			if(size == this.model.getGym(this.frame.getActiveUser()).getSubscribers().size()) {
				this.reAddExSubscriberToModel(this.exSubscriber);
			}
		}
		
		else {				
			throw new IllegalArgumentException("Non è possibile modificare l'abbonamento di questo iscritto, in quanto non ha"
						+ "ancora pagato\n la sua quota d'iscrizione (Se si ritiene necessario modificarlo, occorrerà eliminarlo e reinserirlo).");
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
