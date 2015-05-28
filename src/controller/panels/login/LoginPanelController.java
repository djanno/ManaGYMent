package controller.panels.login;

import javax.swing.JOptionPane;

import model.IModel;
import view.PrimaryFrame;
import view.panels.login.LoginPanel;
import view.panels.signup.SignupPanel;
import controller.panels.signup.SignupPanelController;
import exceptions.WrongCredentialsException;

/**
 * The controller for the {@link LoginPanel}.
 * @author Federico Giannoni
 *
 */
public class LoginPanelController implements ILoginPanelController {

	private static final String BACKGROUND_PATH = "/background.png";
	private final PrimaryFrame frame;
	private final IModel model;
	private final LoginPanel view; 
	
	/**
	 * The constructor for the controller.
	 * @param model the data model to be manipulated.
	 * @param frame the primary frame of the application.
	 * @param view the view.
	 */
	public LoginPanelController(final IModel model, final PrimaryFrame frame, final LoginPanel view) {
		this.frame = frame;
		this.model = model;
		this.view = view;
		this.view.attachObserver(this);
	}
	
	@Override
	public void cmdLogin(final String user, final char[] psw) {
		try {
			if(this.model.checkAccount(user, psw)) {
				this.frame.getPrimaryController().setActiveUser(user);
				this.frame.getPrimaryController().buildHomePanel();
				this.frame.setNavigationMenuEnabled(true); //also disable the option "load"
				if(this.model.getGym(this.frame.getPrimaryController().getActiveUser()).getCourses().isEmpty()) {
					this.showTutorial();
				}
			}
		}
		catch(WrongCredentialsException e) {
			this.frame.displayError(e.getMessage());
		}
	}

	@Override
	public void buildSignupPanel() {
		final SignupPanel panel = new SignupPanel(BACKGROUND_PATH);
		new SignupPanelController(this.frame, this.model, panel);
		this.frame.setCurrentPanel(panel);
	}
	
	/**
	 * Builds a JOptionPane containing a small tutorial that shows new users how to get started in using the application.
	 */
	private void showTutorial() {
		JOptionPane.showMessageDialog(this.frame, 
				"Benvenuto in ManaGYMent. Per poter utilizzare a pieno\n"
				+ "le funzionalità offerte, aggiungi prima del personale\n"
				+ "e dei corsi. Dopodichè, aggiungi degli istruttori ai\n"
				+ "corsi selezionando Dettagli nel menù Gym.");
	}

}
