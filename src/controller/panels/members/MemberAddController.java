package controller.panels.members;

import java.util.Map;

import view.panels.members.IFormField;

/**
 * Common controller of EmployeeAddController and SubscriberAddController
 * 
 * @author Davide Borficchia
 *
 */

public abstract class MemberAddController {

    private static final String WRONG_NAME = "Il nome deve essere lungo più di 1 carattere.";
    private static final String WRONG_SURNAME = "Il cognome deve essere lungo più di 1 carattere.";
    private static final String WRONG_CF = "Il codice fiscale deve essere di 15 caratteri esatti.";
    private static final String WRONG_ADDRESS = "L'indirizzo deve essere lungo più di 7 caratteri.";
    private static final String WRONG_TELEPHONE = "Il numero telefonico deve essere composto da soli numeri.";
    private static final String WRONG_EMAIL = "L'E-mail inserita non è valida.";


    /**
     * Checks if all fields are correct
     * @param mapToPass
     * 		the map of fields to check
     * @return the map if there aren't exception
     */
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
