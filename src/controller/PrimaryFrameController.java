package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.JOptionPane;

import model.IModel;
import view.PrimaryFrame;
import view.panels.email.SendEmailPanel;
import view.panels.gym.GymPanel;
import view.panels.home.HomePanel;
import view.panels.login.LoginPanel;
import view.panels.members.EmployeeStrategy;
import view.panels.members.SubscriberStrategy;
import view.panels.members.tables.TableMemberPanel;
import view.panels.profile.ProfilePanel;
import controller.panels.email.SendEmailPanelController;
import controller.panels.gym.GymPanelController;
import controller.panels.gym.IGymPanelController;
import controller.panels.home.HomePanelController;
import controller.panels.home.IHomePanelController;
import controller.panels.login.LoginPanelController;
import controller.panels.members.tables.IAbstractTableMemberController;
import controller.panels.members.tables.TableEmployeesController;
import controller.panels.members.tables.TableSubscribersController;
import controller.panels.profile.ProfilePanelController;
import exceptions.NoCoursesInGymException;

/**
 * The controller for the {@link PrimaryFrame}.
 * 
 * @author Federico Giannoni
 *
 */
public class PrimaryFrameController implements IPrimaryFrameController {

    private final static String SAVE_FILE_PATH = System.getProperty("user.home") + "/data.gym";
    private final static String EXIT_MSG = "Sei sicuro di voler uscire? (I dati verranno salvati automaticamente)";
    private final static String SAVE_ERROR = "Errore nel salvataggio";
    private final static String LOAD_ERROR = "Errore nel caricamento";
    private final static String BACKGROUND_PATH = "/background.png";

    private IModel model;
    private final PrimaryFrame primaryFrame;

    private String activeUser;

    /**
     * The constructor for the controller.
     * 
     * @param model
     *            the data model to be manipulated.
     * @param frame
     *            the view and also the primary frame of the application.
     */
    public PrimaryFrameController(final IModel model, final PrimaryFrame frame) {
        this.model = model;
        this.primaryFrame = frame;
        this.primaryFrame.attachObserver(this);
    }

    @Override
    public String getActiveUser() {
        return this.activeUser;
    }

    @Override
    public void setActiveUser(final String username) {
        this.activeUser = username;
    }

    @Override
    public void buildLoginPanel() {
        final LoginPanel panel = new LoginPanel(BACKGROUND_PATH);
        new LoginPanelController(this.model, this.primaryFrame, panel);
        this.primaryFrame.setCurrentPanel(panel);
    }

    @Override
    public void buildHomePanel() {
        this.cmdRefreshData();
        final HomePanel panel = new HomePanel(BACKGROUND_PATH);
        final IHomePanelController controller = new HomePanelController(this.model.getGym(this.activeUser), this.primaryFrame, panel);
        this.primaryFrame.setCurrentPanel(panel);
        controller.loadCalendar();
    }

    @Override
    public void buildSubPagePanel() {
        this.cmdRefreshData();
        if (this.model.getGym(this.activeUser).getCourses().isEmpty()) {
            try {
                throw new NoCoursesInGymException();
            } catch (NoCoursesInGymException e) {
                this.primaryFrame.displayError(e.getMessage());
            }
        } else {
            final TableMemberPanel panel = new TableMemberPanel(new SubscriberStrategy(), BACKGROUND_PATH);
            final IAbstractTableMemberController observer = new TableSubscribersController(this.model.getGym(this.activeUser), this.primaryFrame,
                    panel);
            this.primaryFrame.setCurrentPanel(panel);
            observer.createTable(this.model.getGym(this.activeUser).getSubscribers());
        }

    }

    @Override
    public void buildEmployeePagePanel() {
        this.cmdRefreshData();
        final TableMemberPanel panel = new TableMemberPanel(new EmployeeStrategy(), BACKGROUND_PATH);
        final IAbstractTableMemberController observer = new TableEmployeesController(this.model.getGym(this.activeUser), this.primaryFrame, panel);
        this.primaryFrame.setCurrentPanel(panel);
        observer.createTable(this.model.getGym(this.activeUser).getEmployees());
    }

    @Override
    public void buildGymPagePanel() {
        this.cmdRefreshData();
        final GymPanel panel = new GymPanel(BACKGROUND_PATH);
        final IGymPanelController observer = new GymPanelController(this.model.getGym(this.activeUser), this.primaryFrame, panel);
        this.primaryFrame.setCurrentPanel(panel);
        observer.loadIncomeTable();
        observer.loadCoursesTable();
    }

    @Override
    public void buildProfilePagePanel() {
        this.cmdRefreshData();
        final ProfilePanel panel = new ProfilePanel(BACKGROUND_PATH, this.model.getUser(this.activeUser).getName());
        new ProfilePanelController(this.primaryFrame, panel, this.model);
        this.primaryFrame.setCurrentPanel(panel);
    }

    @Override
    public void buildEmailPanel() {
        this.cmdRefreshData();
        final SendEmailPanel panel = new SendEmailPanel(BACKGROUND_PATH);
        new SendEmailPanelController(this.primaryFrame, panel, this.model);
        this.primaryFrame.setCurrentPanel(panel);
    }

    @Override
    public void cmdLogout() {
        final int n = JOptionPane.showConfirmDialog(this.primaryFrame, "Sei sicuro di volerti scollegare?", "Logging out...",
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            this.setActiveUser(null);
            this.primaryFrame.setNavigationMenuEnabled(false);
            this.buildLoginPanel();
        }
    }

    @Override
    public void cmdLoad(final String path) {
        try {
            if (this.isFilePresent(path == null ? SAVE_FILE_PATH : path)) {
                final ObjectInputStream oistream = new ObjectInputStream(new FileInputStream(path == null ? SAVE_FILE_PATH : path));
                this.model = (IModel) oistream.readObject();
                this.primaryFrame.setNavigationMenuEnabled(false);
                this.buildLoginPanel();
                oistream.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            this.primaryFrame.displayError(LOAD_ERROR);
        }

    }

    @Override
    public void cmdSave(final String path) {
        try {
            final ObjectOutputStream oostream = new ObjectOutputStream(new FileOutputStream(path == null ? SAVE_FILE_PATH : path + ".gym"));
            oostream.writeObject(this.model);
            oostream.close();
        } catch (IOException e) {
            this.primaryFrame.displayError(SAVE_ERROR);
        }

    }

    @Override
    public void cmdQuit() {
        final int n = JOptionPane.showConfirmDialog(this.primaryFrame, PrimaryFrameController.EXIT_MSG, "Closing...", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            this.setActiveUser(null);
            this.cmdSave(null);
            System.exit(0);
        }
    }

    @Override
    public void cmdRefreshData() {
        final Thread refresh = new Thread() {

            @Override
            public void run() {
                model.getGym(activeUser).updateEmployeesCredit();
                model.getGym(activeUser).setExpiredSubscribers();
                model.getGym(activeUser).getCourses().forEach(course -> course.removeExpiredMembers());
                model.getGym(activeUser).setIncome(0.0, Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY));
                primaryFrame.setCurrentPanel(primaryFrame.getCurrentPanel());
            }
        };

        refresh.start();
    }

    /**
     * Checks if the specified data file exists.
     * 
     * @param path
     *            the path to the file.
     * @return true if the file exists, false otherwise.
     */
    private boolean isFilePresent(final String path) {
        final File data = new File(path);
        return data.exists();
    }

}
