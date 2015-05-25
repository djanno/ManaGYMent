package controller.panels.login;

import javax.swing.JOptionPane;

import model.IModel;
import view.PrimaryFrame;
import view.panels.login.LoginPanel;
import view.panels.signup.SignupPanel;
import controller.panels.signup.SignupPanelController;
import exceptions.WrongCredentialsException;

public class LoginPanelController implements ILoginPanelController {

	private static final String BACKGROUND_PATH = "/background.png";
	private final PrimaryFrame frame;
	private final IModel model;
	private final LoginPanel view; 
	
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
				this.frame.setActiveUser(user);
				this.frame.getPrimaryController().buildHomePanel();
				this.frame.setNavigationMenuEnabled(true); //also disable the option "load"
				if(this.model.getGym(this.frame.getActiveUser()).getCourses().isEmpty()) {
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
	
	private void showTutorial() {
		JOptionPane.showMessageDialog(this.frame, 
				"Benvenuto in ManaGYMent. Per poter utilizzare a pieno\n"
				+ "le funzionalità offerte, aggiungi prima del personale\n"
				+ "e dei corsi. Dopodichè, aggiungi degli istruttori ai\n"
				+ "corsi selezionando Dettagli nel menù Gym.");
	}

}
