package controller.panels.members;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.swing.DefaultListModel;

import model.gym.ICourse;
import model.gym.IGym;
import model.gym.members.ISubscriber;
import model.gym.members.Subscriber;
import view.PrimaryFrame;
import view.panels.members.FieldsCommon.EnumFieldsCommon;
import view.panels.members.IFormField;
import view.panels.members.ISubscriberPanel;
import view.panels.members.SubscriberPanel;
import controller.panels.members.tables.TableSubscribersController;

/**
 * The controller for {@link SubscriberPanel}.
 * 
 * @author Davide Borficchia
 *
 */

public class SubscriberAddController extends BaseController implements ISubscriberAddController{
	
	private static final String EMPTY_LIST = "Bisogna aggiungere almeno un corso.";
	protected static final String NULL_DATA = "Inserire le date di iscrizione/scadenza.";
	private static final String INVALID_DATES = "Le date d'iscrizione/scadenza non sono valide.";
	
	protected PrimaryFrame frame;
	protected final ISubscriberPanel view;
	protected final IGym gym;
	protected final TableSubscribersController tableSubscribersController;
	//private final Calendar currentSubscriptionCalendar;
	//private final Calendar currentExpirationCalendar;
	
	/**
	 * Constructor
	 * 
	 * @param frame
	 * 		the primary frame
	 * @param subscriberView
	 * 		the subscriber view
	 * @param model
	 * 		the model
	 * @param tableSubscribersController
	 * 		the table subscribers controller
	 */
	public SubscriberAddController (final PrimaryFrame frame, final ISubscriberPanel subscriberView, final IGym gym, final TableSubscribersController tableSubscribersController){
		super();
		this.frame = frame;
		this.view = subscriberView;
		this.gym = gym;
		this.tableSubscribersController = tableSubscribersController;
		this.view.attachObserver(this);
		this.view.setComboBox(this.gym.getCourses());
	}
	
	@Override
	public void cmdSave(final Map<IFormField, String> mapToPass, final Date subscriptionDate,
			final  Date expirationDate, final DefaultListModel<String> list) throws IllegalArgumentException {
		
		try {
			final ISubscriber subscriber = createSubscriber(mapToPass, dateToCalendar(subscriptionDate), dateToCalendar(expirationDate), list);
			this.gym.addSubscriber(subscriber);
			tableSubscribersController.createTable(this.gym.getSubscribers());
			this.frame.getChild().closeDialog();
		}catch (Throwable t){
			this.frame.displayError(t.getMessage());
		}		
	}
	
	@Override
	public List<ICourse> loadCourses(){
		return this.gym.getCourses();
	}	
		
	/**
	 * @param date
	 * 		the date to converter
	 * @return the Calendar of date
	 * @throws IllegalArgumentException
	 */
	protected static Calendar dateToCalendar(final Date date) throws IllegalArgumentException{ 
		  final Calendar cal = Calendar.getInstance();
		  try{
			  cal.setTime(date);
		  }catch (Exception t){
			  throw new IllegalArgumentException(NULL_DATA);
		  }
		  return cal;
	}
	
	protected static boolean compareCalendars(final Calendar initialDate, final Calendar finalDate) {
		return (initialDate.get(Calendar.DAY_OF_MONTH) == finalDate.get(Calendar.DAY_OF_MONTH) && 
				initialDate.get(Calendar.MONTH) == finalDate.get(Calendar.MONTH) && 
				initialDate.get(Calendar.YEAR) == finalDate.get(Calendar.YEAR));
	}
	
	/**
	 * 
	 * Creates new subscriber from GUI
	 * 
	 * @param mapToPass
	 * 		the common fields of subscriber
	 * @param subscriptionDate
	 * 		the subscriber's subscription date 
	 * @param expirationDate
	 * 		the subscriber's expiration date 
	 * @param list
	 * 		the subscriber's courses 
	 * @return the new subscriber
	 * @throws Exception
	 */
	private Subscriber createSubscriber(final Map<IFormField, String> mapToPass, final Calendar subscriptionDate, 
			final  Calendar expirationDate, final DefaultListModel<String> list) throws Exception{
		
		final Map<IFormField, String> fields = this.getCommonFields(mapToPass);
		final Calendar today = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
		
		if(list.isEmpty()){
			throw new IllegalArgumentException(EMPTY_LIST);
		}
		
		if(expirationDate == null || subscriptionDate == null){
			throw new IllegalArgumentException(NULL_DATA);
		}
		
		this.checkForDateExceptions(subscriptionDate, today);
		this.checkForDateExceptions(expirationDate, today);
		
		if(compareCalendars(subscriptionDate, expirationDate)) {
			throw new IllegalArgumentException("La data di scadenza non può coincidere con quella di iscrizione.");
		}
		
		return new Subscriber(fields.get(EnumFieldsCommon.NOME).trim(), fields.get(EnumFieldsCommon.COGNOME).trim(), fields.get(EnumFieldsCommon.CODICE_FISCALE).trim(), 
				fields.get(EnumFieldsCommon.INDIRIZZO).trim(), fields.get(EnumFieldsCommon.TELEFONO).trim(), fields.get(EnumFieldsCommon.EMAIL).trim(), this.gym, subscriptionDate, expirationDate, convertList(list, this.gym.getCourses()));
	}
	
	
	private void checkForDateExceptions(final Calendar date, final Calendar lowerLimit) {
		
		if(date.get(Calendar.YEAR) < lowerLimit.get(Calendar.YEAR)) {
			throw new IllegalArgumentException(INVALID_DATES);
		}
		
		else {
			if(date.get(Calendar.YEAR) == lowerLimit.get(Calendar.YEAR) && date.get(Calendar.MONTH) < lowerLimit.get(Calendar.MONTH)) {
				throw new IllegalArgumentException(INVALID_DATES);
			}
			
			else if(date.get(Calendar.MONTH) == lowerLimit.get(Calendar.MONTH) && date.get(Calendar.DAY_OF_MONTH) < lowerLimit.get(Calendar.DAY_OF_MONTH)){
				throw new IllegalArgumentException(INVALID_DATES);
			}
		}
	}
}
