package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import model.IModel;
import view.PrimaryFrame;
import view.panels.gym.GymPanel;
import view.panels.home.HomePanel;
import view.panels.login.LoginPanel;
import view.panels.members.TableMemberPanel;
import controller.panels.gym.GymPanelController;
import controller.panels.gym.IGymPanelController;
import controller.panels.home.HomePanelController;
import controller.panels.home.IHomePanelController;
import controller.panels.login.LoginPanelController;
import controller.panels.members.AbstractTableMemberController;
import controller.panels.members.TableEmployeesController;
import controller.panels.members.TableSubscribersController;

public class PrimaryFrameController implements IPrimaryFrameController {

	private final static String SAVE_FILE_PATH = System.getProperty("user.home") + "/data.gym";
	private final static String EXIT_MSG = "Sei sicuro di voler uscire? (I dati verranno salvati automaticamente)";
	private final static String SAVE_ERROR = "Errore nel salvataggio";
	private final static String LOAD_ERROR = "Errore nel caricamento";
	private final static String BACKGROUND_PATH = "/background.png";
	
	private IModel model;
	private final PrimaryFrame primaryFrame;
	
	public PrimaryFrameController(final IModel model, final PrimaryFrame frame) {
		this.model = model;
		this.primaryFrame = frame;
		this.primaryFrame.attachObserver(this);
	}
	
	@Override
	public void buildLoginPanel() {
		final LoginPanel panel = new LoginPanel(BACKGROUND_PATH);
		new LoginPanelController(this.model, this.primaryFrame, panel);
		this.primaryFrame.setCurrentPanel(panel);
	}

	@Override
	public void buildHomePanel() {
		final HomePanel panel = new HomePanel(BACKGROUND_PATH);
		final IHomePanelController controller = new HomePanelController(this.model, this.primaryFrame, panel);
		this.primaryFrame.setCurrentPanel(panel);	
		controller.loadCalendar();
	}

	@Override
	public void buildSubPagePanel() {
//		final TableMemberPanel panel = new TableMemberPanel(new SubscriberStrategy(), BACKGROUND_PATH);
//		final AbstractTableMemberController observer = new TableSubscribersController(this.model, this.primaryFrame, panel);
//		this.primaryFrame.setCurrentPanel(panel);
//		observer.createTable(this.model.getGym(this.primaryFrame.getActiveUser()).getSubscribers());
	}

	@Override
	public void buildEmployeePagePanel() {
//	    final TableMemberPanel panel = new TableMemberPanel(new EmployeeStrategy(), BACKGROUND_PATH);
//            final AbstractTableMemberController observer = new TableEmployeesController(this.model, this.primaryFrame, panel);
//            this.primaryFrame.setCurrentPanel(panel);
//            observer.createTable(this.model.getGym(this.primaryFrame.getActiveUser()).getEmployees());
	}

	@Override
	public void buildGymPagePanel() {
		final GymPanel panel = new GymPanel(BACKGROUND_PATH);
		final IGymPanelController observer = new GymPanelController(this.model, this.primaryFrame, panel);
		this.primaryFrame.setCurrentPanel(panel);
		observer.loadCoursesTable();
	}

	@Override
	public void buildProfilePagePanel() {
		//final ProfilePagePanel panel = new ProfilePagePanel(BACKGROUND_PATH);
		//new ProfilePagePanelController(this.model, this.primaryFrame, panel);
		//this.primaryFrame.setCurrentPanel(panel);
	}
	
	@Override
	public void cmdLogout() {
		final int n = JOptionPane.showConfirmDialog(this.primaryFrame, "Sei sicuro di volerti scollegare?", "Logging out...", JOptionPane.YES_NO_OPTION);
		if(n == JOptionPane.YES_OPTION) {
			this.primaryFrame.setNavigationMenuEnabled(false);
			this.buildLoginPanel();
		}
	}

	@Override
	public void cmdLoad(final String path) {
		try {
			if(this.isFilePresent(path == null ? SAVE_FILE_PATH : path)) {
				final BufferedInputStream bistream = new BufferedInputStream(new FileInputStream(path == null ? SAVE_FILE_PATH : path));
				final ObjectInputStream oistream = new ObjectInputStream(bistream);
				this.model = (IModel) oistream.readObject();
				this.primaryFrame.setNavigationMenuEnabled(false);
				this.buildLoginPanel();
				oistream.close();
			}
		}
		catch(IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this.primaryFrame, PrimaryFrameController.LOAD_ERROR, "Error", JOptionPane.OK_OPTION);
		}
		
	}

	@Override
	public void cmdSave(final String path) {
		try {
			final BufferedOutputStream bostream = new BufferedOutputStream(new FileOutputStream(path == null ? SAVE_FILE_PATH : path + ".gym"));
			final ObjectOutputStream oostream = new ObjectOutputStream(bostream);
			oostream.writeObject(this.model);
			oostream.close();
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(this.primaryFrame, PrimaryFrameController.SAVE_ERROR, "Error", JOptionPane.OK_OPTION);
		}
		
	}

	@Override
	public void cmdQuit() {
		final int n = JOptionPane.showConfirmDialog(this.primaryFrame, PrimaryFrameController.EXIT_MSG, "Closing...", JOptionPane.YES_NO_OPTION);
		if(n == JOptionPane.YES_OPTION) {
			this.cmdSave(null);
			System.exit(0);
		}
	}
	
	private boolean isFilePresent(final String path) {
		final File data = new File(path);
		return data.exists();
	}
	
}
