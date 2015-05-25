package controller.panels.members;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultListModel;
import model.IModel;
import model.gym.ICourse;
import model.gym.members.ISubscriber;
import model.gym.members.Subscriber;
import view.IPrimaryFrame;
import view.panels.members.IFormField;
import view.panels.members.ISubscriberPanel;

/**
 * The controller for {@link SubscriberPanel}.
 * 
 * @author Davide Borficchia
 *
 */

public class SubscriberAddController extends BaseController implements ISubscriberAddController{
	private static final String WRONG_NAME = "Il nome deve essere lungo più di 1 carattere.";
	private static final String WRONG_SURNAME = "Il cognome deve essere lungo più di 1 carattere.";
	private static final String WRONG_CF = "Il codice fiscale deve essere di 15 caratteri esatti.";
	private static final String WRONG_ADDRESS = "L'indirizzo deve essere lungo più di 7 caratteri.";
	private static final String WRONG_TELEPHONE = "Il numero telefonico deve essere composto da soli numeri.";
	private static final String WRONG_EMAIL = "L'E-mail inserita non è valida.";
	private static final String EMPTY_LIST = "Bisogna aggiungere almeno un corso.";
	protected static final String NULL_DATA = "La data inserita non è valida.";
	private static final String INVALID_EXPIRATION = "Le data di scadenza non è valida.";
	private static final String INVALID_SUBSCRIPTION = "Le data di iscrizione non è valida.";
	
	protected IPrimaryFrame frame;
	protected final ISubscriberPanel view;
	protected final IModel model;
	protected final TableSubscribersController tableSubscribersController;
	private final Calendar currentCalendar;
	
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
	public SubscriberAddController (final IPrimaryFrame frame, final ISubscriberPanel subscriberView, final IModel model, final TableSubscribersController tableSubscribersController){
		super();
		this.frame = frame;
		this.view = subscriberView;
		this.model = model;
		this.tableSubscribersController = tableSubscribersController;
		this.currentCalendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
		this.view.attachObserver(this);
		this.view.setComboBox(this.model.getUser(this.frame.getActiveUser()).getGym().getCourses());
	}
	
	@Override
	public void cmdSave(final Map<IFormField, String> mapToPass, final Date subscriptionDate, final  Date expirationDate, final DefaultListModel<String> list) throws IllegalArgumentException {
		try{
			final ISubscriber subscriber = createSubscriber(mapToPass, dateToCalendar(subscriptionDate), dateToCalendar(expirationDate), list);
			
			for (final ICourse course : subscriber.getCourses()){
				this.model.getGym(this.frame.getActiveUser()).getCourseByName(course.getCourseName()).addMember(subscriber);
			}
			
			this.model.getUser(this.frame.getActiveUser()).getGym().addSubscriber(subscriber);
			
			/*for (final ICourse course : this.model.getGym(this.frame.getActiveUser()).getCourses()){
				for (final ISubscriber su : this.model.getGym(this.frame.getActiveUser()).getCourseByName(course.getCourseName()).getCurrentMembers()){
					System.out.println(su.getName() + " " + course.getCourseName());
				}
			}*/
			
			countAddIncome(subscriber);
			tableSubscribersController.createTable(this.model.getUser(this.frame.getActiveUser()).getGym().getSubscribers());
			this.frame.getChild().closeDialog();
		}catch (Throwable t){
			this.frame.displayError(t.getMessage());
		}		
	}
	
	@Override
	public List<ICourse> loadCourses(){
		return this.model.getUser(this.frame.getActiveUser()).getGym().getCourses();
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
	
	/**
	 * 
	 * Counts the amount to decrease from income
	 * 
	 * @param subscriber
	 * 		the new subscriber
	 */
	protected void countDecreaseIncome(final ISubscriber subscriber){
        final long dayDiff = TimeUnit.MILLISECONDS.toDays(subscriber.getExpirationDate().getTimeInMillis() - Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome")).getTimeInMillis());
        
        if(dayDiff > 0){
            for(final ICourse c : subscriber.getCourses()){
            	this.model.getUser(this.frame.getActiveUser()).getGym().setIncome(- (dayDiff * c.getCoursePrice()), subscriber.getSubscriptionDate());
            }
        }
	}
	
	/**
	 * 
	 * Counts the amount to add from income
	 * 
	 * @param subscriber
	 * 		the new subscriber
	 */
	protected void countAddIncome(final ISubscriber subscriber){
        final long newDayDiff = TimeUnit.MILLISECONDS.toDays(subscriber.getExpirationDate().getTimeInMillis() - subscriber.getSubscriptionDate().getTimeInMillis());
	    
	    for(final ICourse c : subscriber.getCourses()){
             this.model.getUser(this.frame.getActiveUser()).getGym().setIncome(newDayDiff * c.getCoursePrice(), subscriber.getSubscriptionDate());
        }
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
	private Subscriber createSubscriber(final Map<IFormField, String> mapToPass, final Calendar subscriptionDate, final  Calendar expirationDate, final DefaultListModel<String> list) throws Exception{
		for(final IFormField f : mapToPass.keySet()){
			if(! f.getPred().test(mapToPass.get(f))){	
				if(f.getField().equals("Nome")){
					throw new IllegalArgumentException(WRONG_NAME);
				}
				if(f.getField().equals("Cognome")){
					throw new IllegalArgumentException(WRONG_SURNAME);
				}
				if(f.getField().equals("Codice fiscale")){
					throw new IllegalArgumentException(WRONG_CF);
				}
				if(f.getField().equals("Indirizzo")){
					throw new IllegalArgumentException(WRONG_ADDRESS);
				}
				if(f.getField().equals("Telefono")){
					throw new IllegalArgumentException(WRONG_TELEPHONE);
				}
				if(f.getField().equals("E-Mail")){
					throw new IllegalArgumentException(WRONG_EMAIL);
				}
			}
		}
		if(list.isEmpty()){
			throw new IllegalArgumentException(EMPTY_LIST);
		}
		
		if(expirationDate == null || subscriptionDate == null){
			throw new IllegalArgumentException(NULL_DATA);
		}
		
		if(expirationDate.get(Calendar.YEAR) < this.currentCalendar.get(Calendar.YEAR)){
			throw new IllegalArgumentException(INVALID_EXPIRATION);
		}else{
			if(expirationDate.get(Calendar.MONTH) < this.currentCalendar.get(Calendar.MONTH)){
				throw new IllegalArgumentException(INVALID_EXPIRATION);
			}else{
				if(expirationDate.get(Calendar.DAY_OF_MONTH) < this.currentCalendar.get(Calendar.DAY_OF_MONTH)){
					throw new IllegalArgumentException(INVALID_EXPIRATION);
				}
			}
		}
		
		if(subscriptionDate.get(Calendar.YEAR) < this.currentCalendar.get(Calendar.YEAR)){
			throw new IllegalArgumentException(INVALID_SUBSCRIPTION);
		}else{
			if(subscriptionDate.get(Calendar.MONTH) < this.currentCalendar.get(Calendar.MONTH)){
				throw new IllegalArgumentException(INVALID_SUBSCRIPTION);
			}else{
				if(subscriptionDate.get(Calendar.DAY_OF_MONTH) < this.currentCalendar.get(Calendar.DAY_OF_MONTH)){
					throw new IllegalArgumentException(INVALID_SUBSCRIPTION);
				}
			}
		}
		
		String name = "";
		String surname = "";
		String fiscalCode = "";
		String address = "";
		String phoneNumber = "";
		String email = "";

		for (final IFormField f : mapToPass.keySet()){
			switch (f.getField()){
				case "Nome":  name = new String( mapToPass.get(f).trim());
	            	break;
				case "Cognome":  surname = new String( mapToPass.get(f).trim());
	        		break;
				case "Codice fiscale":  fiscalCode = new String( mapToPass.get(f));
	        		break;
				case "Indirizzo":  address = new String( mapToPass.get(f));
	        		break;
				case "Telefono":  phoneNumber = new String( mapToPass.get(f));
	        		break;
				case "E-Mail":  email = new String( mapToPass.get(f));
	        		break;
	        	default:
	        		break;
			}
		}
		return new Subscriber(name, surname, fiscalCode, address, phoneNumber, email, this.model.getUser(this.frame.getActiveUser()).getGym(), subscriptionDate, expirationDate, convertList(list, this.model.getUser(this.frame.getActiveUser()).getGym().getCourses()));
	}
}
