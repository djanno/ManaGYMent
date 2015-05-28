package controller.panels.members;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;

import model.gym.ICourse;
import view.panels.members.IFormField;

/**
 * Common controller of EmployeeAddController and SubscriberAddController
 * 
 * @author Davide Borficchia
 *
 */

public class BaseController implements IBaseController {

    private static final String WRONG_NAME = "Il nome deve essere lungo più di 1 carattere.";
    private static final String WRONG_SURNAME = "Il cognome deve essere lungo più di 1 carattere.";
    private static final String WRONG_CF = "Il codice fiscale deve essere di 15 caratteri esatti.";
    private static final String WRONG_ADDRESS = "L'indirizzo deve essere lungo più di 7 caratteri.";
    private static final String WRONG_TELEPHONE = "Il numero telefonico deve essere composto da soli numeri.";
    private static final String WRONG_EMAIL = "L'E-mail inserita non è valida.";

    @Override
    public List<ICourse> convertList(final DefaultListModel<String> list, final List<ICourse> gymCourses) {
        final List<ICourse> corsi = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (final ICourse c : gymCourses) {
                if (list.getElementAt(i).equals(c.getCourseName())) {
                    corsi.add(c);
                }
            }
        }
        return corsi;
    }

    protected Map<IFormField, String> getCommonFields(final Map<IFormField, String> mapToPass) {

        for (final IFormField f : mapToPass.keySet()) {
            if (!f.getPred().test(mapToPass.get(f))) {
                if (f.getField().equals("Nome")) {
                    throw new IllegalArgumentException(WRONG_NAME);
                }
                if (f.getField().equals("Cognome")) {
                    throw new IllegalArgumentException(WRONG_SURNAME);
                }
                if (f.getField().equals("Codice fiscale")) {
                    throw new IllegalArgumentException(WRONG_CF);
                }
                if (f.getField().equals("Indirizzo")) {
                    throw new IllegalArgumentException(WRONG_ADDRESS);
                }
                if (f.getField().equals("Telefono")) {
                    throw new IllegalArgumentException(WRONG_TELEPHONE);
                }
                if (f.getField().equals("E-Mail")) {
                    throw new IllegalArgumentException(WRONG_EMAIL);
                }
            }
        }

        return mapToPass;

        // else throw new
        // IllegalArgumentException("I dati inseriti non sono corretti");

    }
}
