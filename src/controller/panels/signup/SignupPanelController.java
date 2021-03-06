package controller.panels.signup;

import java.util.Map;

import javax.swing.JOptionPane;

import model.IModel;
import model.User;
import model.gym.Gym;
import model.gym.IGym;
import view.PrimaryFrame;
import view.panels.signup.ISignupField;
import view.panels.signup.SignupPanel;
import view.panels.signup.SignupStrategy.SignupField;
import exceptions.UserAlreadyExistsException;

/**
 * The controller for the {@link SignupPanel}.
 * 
 * @author Federico Giannoni
 *
 */
public class SignupPanelController implements ISignupPanelController {

    private static final String REGISTRATION_SUCCESSFUL = "Utente registrato correttamente";
    private static final String REGISTRATION_FAILED = "I dati inseriti non sono validi.";

    private final SignupPanel view;
    private final PrimaryFrame frame;
    private final IModel model;

    /**
     * The constructor for the controller.
     * 
     * @param frame
     *            the primary frame of the application.
     * @param model
     *            the data model to be manipulated.
     * @param view
     *            the view.
     */
    public SignupPanelController(final PrimaryFrame frame, final IModel model, final SignupPanel view) {
        this.view = view;
        this.model = model;
        this.frame = frame;
        this.view.attachObserver(this);
    }

    @Override
    public void cmdSignup(final Map<ISignupField, String> mapToPass) {
        try {
            if (mapToPass.entrySet().stream().allMatch(value -> value.getKey().checkPredicate(value.getValue()))
                    && mapToPass.get(SignupField.PASSWORD).equals(mapToPass.get(SignupField.CONFIRM_PASSWORD))
                    && mapToPass.get(SignupField.EMAIL).equals(mapToPass.get(SignupField.CONFIRM_EMAIL))) {

                final String name = mapToPass.get(SignupField.NAME).trim();
                final String surname = mapToPass.get(SignupField.SURNAME).trim();
                final String username = mapToPass.get(SignupField.USERNAME).trim();
                final char[] psw = mapToPass.get(SignupField.PASSWORD).trim().toCharArray();
                final IGym gym = new Gym(mapToPass.get(SignupField.GYM_NAME).trim());
                final String email = mapToPass.get(SignupField.EMAIL).trim();

                try {
                    this.model.addUser(new User(name, surname, username, psw, gym, email));
                    JOptionPane.showMessageDialog(this.frame, REGISTRATION_SUCCESSFUL);
                    this.frame.getPrimaryController().buildLoginPanel();
                } catch (UserAlreadyExistsException e) {
                    this.frame.displayError(e.getMessage());
                }

            } else {
                throw new IllegalArgumentException(REGISTRATION_FAILED);
            }

        } catch (IllegalArgumentException e) {
            this.frame.displayError(e.getMessage());
        }
    }

    @Override
    public void cmdCancel() {
        this.frame.getPrimaryController().buildLoginPanel();
    }

}
