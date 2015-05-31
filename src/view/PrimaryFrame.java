package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.panels.Background;
import controller.IPrimaryFrameController;

/**
 * The primary frame in which all the panels of the application are drawn.
 * @author Federico Giannoni
 *
 */
public final class PrimaryFrame extends JFrame implements IPrimaryFrame, ActionListener {

	private static final long serialVersionUID = 3170409204135126317L;
	
	private static final PrimaryFrame SINGLETON_PRIMARY_FRAME= new PrimaryFrame();
	
    private static final String TITLE = "ManaGYMent";
    private static final String ICON_PATH = "/icon.png";
    private static final String HEADER_PATH = "/header.png";

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final int HOME_INDEX = 0;
    private static final int SUB_PAGE_INDEX = 1;
    private static final int EMPLOYEE_PAGE_INDEX = 2;
    private static final int GYM_PAGE_INDEX = 3;
    private static final int PROFILE_INDEX = 4;
    private static final int EMAIL_INDEX = 5;
    private static final int LOGOUT_INDEX = 6;

    private static final int LOAD_INDEX = 0;
    private static final int SAVE_INDEX = 1;

    private static final int NAVIGATION_MENU_VOICES = 7; // ADD A LOGOUT OPTION.
    private static final int OPTION_MENU_VOICES = 2;

    private IPrimaryFrameController observer;
    private DialogWindow child;

    private final JMenuBar menuBar;
    private final JMenu navigationMenu;
    private final JMenu optionMenu;
    private final JMenuItem[] navigationMenuVoices;
    private final JMenuItem[] optionMenuVoices;
    private JPanel headerPanel;
    private JPanel mainPanel;
    private final JFileChooser fileChooser;

    /**
     * Constructs a default primary frame.
     */
    private PrimaryFrame() {
        super(TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setLocationByPlatform(true);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setIconImage(new ImageIcon(this.getClass().getResource(ICON_PATH)).getImage());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent event) {
                observer.cmdQuit();
            }
        });

        this.menuBar = new JMenuBar();

        this.navigationMenu = new JMenu("Menu");
        this.navigationMenuVoices = new JMenuItem[NAVIGATION_MENU_VOICES];
        this.navigationMenuVoices[HOME_INDEX] = new JMenuItem("Home");
        this.navigationMenuVoices[SUB_PAGE_INDEX] = new JMenuItem("Iscritti");
        this.navigationMenuVoices[EMPLOYEE_PAGE_INDEX] = new JMenuItem("Personale");
        this.navigationMenuVoices[GYM_PAGE_INDEX] = new JMenuItem("La mia palestra");
        this.navigationMenuVoices[PROFILE_INDEX] = new JMenuItem("Profilo");
        this.navigationMenuVoices[EMAIL_INDEX] = new JMenuItem("Email");
        this.navigationMenuVoices[LOGOUT_INDEX] = new JMenuItem("Logout");

        for (int i = 0; i < NAVIGATION_MENU_VOICES; i++) {
            this.navigationMenuVoices[i].addActionListener(this);
            this.navigationMenuVoices[i].setEnabled(false);
            this.navigationMenu.add(navigationMenuVoices[i]);
        }

        this.optionMenu = new JMenu("Options");
        this.optionMenuVoices = new JMenuItem[OPTION_MENU_VOICES];
        this.optionMenuVoices[LOAD_INDEX] = new JMenuItem("Load");
        this.optionMenuVoices[SAVE_INDEX] = new JMenuItem("Save");

        for (int i = 0; i < OPTION_MENU_VOICES; i++) {
            this.optionMenuVoices[i].addActionListener(this);
            this.optionMenu.add(optionMenuVoices[i]);
        }

        this.menuBar.add(navigationMenu);
        this.menuBar.add(optionMenu);
        this.setJMenuBar(this.menuBar);

        this.headerPanel = new Background(HEADER_PATH);
        this.getContentPane().add(this.headerPanel, BorderLayout.NORTH);

        this.fileChooser = new JFileChooser();
        this.fileChooser.setFileFilter(new FileNameExtensionFilter(".gym", "gym"));

    }

    public static PrimaryFrame getPrimaryFrame(){
    	return SINGLETON_PRIMARY_FRAME;
    }
    
    @Override
    public void actionPerformed(final ActionEvent event) {
        final Object source = event.getSource();

        if (source == this.navigationMenuVoices[HOME_INDEX]) {
            this.observer.buildHomePanel();
        }

        else if (source == this.navigationMenuVoices[SUB_PAGE_INDEX]) {
            this.observer.buildSubPagePanel();
        }

        else if (source == this.navigationMenuVoices[EMPLOYEE_PAGE_INDEX]) {
            this.observer.buildEmployeePagePanel();
        }

        else if (source == this.navigationMenuVoices[GYM_PAGE_INDEX]) {
            this.observer.buildGymPagePanel();
        }

        else if (source == this.navigationMenuVoices[PROFILE_INDEX]) {
            this.observer.buildProfilePagePanel();
        }

        else if (source == this.navigationMenuVoices[EMAIL_INDEX]) {
            this.observer.buildEmailPanel();
        }

        else if (source == this.navigationMenuVoices[LOGOUT_INDEX]) {
            this.observer.cmdLogout();
        }

        else if (source == this.optionMenuVoices[LOAD_INDEX]) {
            if (this.fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                this.observer.cmdLoad(this.fileChooser.getSelectedFile().getPath());
            }
        }

        else if (source == this.optionMenuVoices[SAVE_INDEX]) {
            if (this.fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                this.observer.cmdSave(this.fileChooser.getSelectedFile().getPath());
            }
        }

    }

    @Override
    public JPanel getCurrentPanel() {
        return this.mainPanel;
    }

    @Override
    public DialogWindow getChild() {
        return this.child;
    }

    @Override
    public IPrimaryFrameController getPrimaryController() {
        return this.observer;
    }

    @Override
    public void drawHeaderPanel() {
        this.headerPanel = new Background(HEADER_PATH);
        this.headerPanel.setPreferredSize(new Dimension(800, 50));
        this.getContentPane().add(this.headerPanel, BorderLayout.NORTH);
    }

    @Override
    public void setCurrentPanel(final JPanel panel) {
        this.mainPanel = panel;
        this.getContentPane().removeAll();
        this.drawHeaderPanel();
        this.getContentPane().add(this.mainPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
        // revalidate, stando ai tutorial di eclipse, va chiamata ogni volta che
        // si
        // cancella uno o più componenti dal contentPane. Serve a comunicare al
        // layout
        // manager di resettarsi in base alla nuova lista di componenti. Il
        // tutorial dice
        // inoltre di chiamare anche repaint dopo ogni revalidate, per
        // assicurarsi che
        // vengano ripulite tutte le regioni "sporche" del pannello (altrimenti
        // non è detta
        // che vengano ripulite tutte).
    }

    @Override
    public void setNavigationMenuEnabled(final boolean enabled) {
        for (final JMenuItem item : this.navigationMenuVoices) {
            item.setEnabled(enabled);
        }
        this.optionMenuVoices[LOAD_INDEX].setEnabled(!enabled); // when you can
                                                                // navigate, you
                                                                // can not load
                                                                // and
                                                                // viceversa.
    }

    @Override
    public void displayError(final String error) {
        JOptionPane.showMessageDialog(this.getChild() == null ? this : this.getChild(), error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void attachObserver(final IPrimaryFrameController observer) {
        this.observer = observer;
    }

    /**
     * Sets the specified dialog window as the child component of the {@link PrimaryFrame}.
     * @param window the dialog window to be designed as the child component of the frame of the application.
     */
    private void setChild(final DialogWindow window) {
        this.child = window;
    }

    /**
     * A dialog window built on the go on top of the {@link PrimaryFrame}. This window is used mainly to contain
     * panels used to add/edit specific informations.
     * @author Federico Giannoni
     *
     */
    public class DialogWindow extends JDialog {

        private static final long serialVersionUID = -4405596479287846731L;

        private final PrimaryFrame father;

        /**
         * Constructs a dialog window containing the specified panel and sets it as the child component of a designed {@link PrimaryFrame}.
         * Keep in mind that the parent component of the dialog will be disabled until the dialog is closed. This has the purpose to make it
         * impossible for the user of the application to perform operations that are not allowed.
         * @param title the title of the dialog window.
         * @param width the width of the dialog window.
         * @param height the height of the dialog window.
         * @param father the father component of the dialog window.
         * @param panel the panel to be displayed inside the dialog window.
         */
        public DialogWindow(final String title, final int width, final int height, final PrimaryFrame father, final JPanel panel) {
            super();

            this.father = father;
            this.father.setChild(this);
            this.setTitle(title);

            this.setSize(width, height);
            this.setResizable(true);
            this.setLocationByPlatform(true);
            this.setLocationRelativeTo(this.father);

            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(final WindowEvent event) {
                    father.setEnabled(true);
                    DialogWindow.this.dispose();
                }
            });

            this.setIconImage(new ImageIcon(this.getClass().getResource(ICON_PATH)).getImage());

            this.father.setEnabled(false);
            this.setVisible(true);

            this.setLayout(new BorderLayout());
            this.getContentPane().add(panel, BorderLayout.CENTER);

        }

        // costruttore di dialog window che accetta una dimensione.
        // DO_NOTHING_ON_CLOSE come defaultcloseop
        // setEnabled(false) al mainFrame e fare in modo che il dialog sia
        // sempre sopra il frame.
        // windowClosing -> mainFrame.setEnabled(true) e DISPOSE del dialog.

        /**
         * Closes the dialog and re-enables its parent component.
         */
        public void closeDialog() {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

}

// OCCORRE UN BOTTONE LOGOUT, INOLTRE BISOGNA GESTIRE BENE IL DIALOG.
// AL MOMENTO DI CHIUSURA, SI PULISCE IL DIALOG E SI RENDE INVISIBILE.
