package controller.panels.profile;

import javax.swing.JOptionPane;

import model.IModel;
import view.PrimaryFrame;
import view.panels.profile.IProfilePanel;

/**
 * The controller for {@link ProfilePanel}.
 * 
 * @author Davide Borficchia
 *
 */
public class ProfilePanelController implements IProfilePanelController {

    private final PrimaryFrame frame;
    private final IProfilePanel view;
    private final IModel model;

    private static final String CHANGED_PASSWORD = "Password modificata correttamente.";
    private static final String CHANGED_EMAIL = "E-mail modificata correttamente.";
    private static final String NO_EMPTY_FIELDS = "Tutti i campi devono essere compilati";
    private static final String NO_EQUAL_PASSWORD = "Le due password devono corrispondere";
    private static final String INVALID_EMAIL = "E' stata inserita un email non valida.";
    private static final String NO_EQUAL_EMAIL = "Le due email devono corrispondere";

    /**
     * Constructor.
     * 
     * @param frame
     *            the primary frame
     * @param view
     *            the profile panel
     * @param model
     *            the model
     */
    public ProfilePanelController(final PrimaryFrame frame, final IProfilePanel view, final IModel model) {
        this.frame = frame;
        this.view = view;
        this.view.attachObserver(this);
        this.model = model;
    }

    @Override
    public void cmdEditPsw(final char[] psw, final char[] confirmPsw) {
        if (psw.length == 0 || confirmPsw.length == 0) {
            this.frame.displayError(NO_EMPTY_FIELDS);
        } else {
            if (this.checkPassword(psw, confirmPsw)) {
                this.model.getUser(this.frame.getPrimaryController().getActiveUser()).setPassword(confirmPsw);
                JOptionPane.showMessageDialog(this.frame, CHANGED_PASSWORD, "Password modificata", JOptionPane.INFORMATION_MESSAGE);
            } else {
                this.frame.displayError(NO_EQUAL_PASSWORD);
            }
        }
    }

    @Override
    public void cmdEditEmail(final String email, final String confirmEmail) {
        if (email.equals("") || confirmEmail.equals("")) {
            this.frame.displayError(NO_EMPTY_FIELDS);
        } else {
            if (email.equals(confirmEmail)) {
                if (email.contains("@gmail.com") || email.contains("@yahoo.com") || email.contains("@yahoo.it")) {
                    this.model.getUser(this.frame.getPrimaryController().getActiveUser()).setEmail(confirmEmail);
                    JOptionPane.showMessageDialog(this.frame, CHANGED_EMAIL, "Email modificata.", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    this.frame.displayError(INVALID_EMAIL);
                }
            } else {
                this.frame.displayError(NO_EQUAL_EMAIL);
            }
        }
    }

    @Override
    public void cmdCancel() {
        this.frame.getChild().closeDialog();
    }

    /**
     * Checks if the password1 is the same as the password2
     * 
     * @return true if the both password are the same, false otherwise
     */
    private boolean checkPassword(final char[] password1, final char[] password2) {
        if (password1.length == password2.length) {
            for (int i = 0; i < password1.length; i++) {
                if (password1[i] != password2[i]) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}