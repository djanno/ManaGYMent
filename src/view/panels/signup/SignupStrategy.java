package view.panels.signup;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Strategy class to make it so that the {@link SignupPanel} is more easily adjustable and extensible.
 * @author Federico Giannoni
 *
 */
public class SignupStrategy implements ISignupStrategy {

    /**
     * Constructor.
     */
    public SignupStrategy() {
        super();
    }

    /**
     * Enumerator that defines all the field required for the registration of a new {@link User}.
     * @author Federico Giannoni
     *
     */
    public enum SignupField implements ISignupField {

        NAME("nome: ", 20, false, x -> (x.length() > 1 && x.length() < 20), "max 20 caratteri."), SURNAME("cognome: ", 20, false,
                x -> (x.length() > 1 && x.length() < 20), "max 20 caratteri."), GYM_NAME("nome palestra: ", 20, false, x -> (x.length() > 1 && x
                .length() < 20), "max 20 caratteri."), USERNAME("username: ", 20, false, x -> (x.length() > 4 && x.length() < 20),
                "max 20 caratteri."), PASSWORD("password: ", 20, true, x -> (x.length() > 7), "almeno 8 caratteri."), CONFIRM_PASSWORD(
                "conferma password: ", 20, true, x -> (x.length() > 7), "reinserire la propria password."), EMAIL("email: ", 20, false, x -> (Pattern
                .compile(SignupField.EMAIL_PATTERN).matcher(x).matches() && (x.contains("@gmail.com") || x.contains("@yahoo.com") || x
                .contains("@yahoo.it"))), "solo gmail.com, yahoo.com o yahoo.it."), CONFIRM_EMAIL("conferma email: ", 20, false, x -> (Pattern
                .compile(SignupField.EMAIL_PATTERN).matcher(x).matches() && (x.contains("@gmail.com") || x.contains("@yahoo.com") || x
                .contains("@yahoo.it"))), "reinserire la propria email.");

        private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        private final String name;
        private final int length;
        private final boolean hidden;
        private final Predicate<String> predicate;
        private final String tip;

        private SignupField(final String name, final int length, final boolean hidden, final Predicate<String> predicate, final String tip) {
            this.name = name;
            this.length = length;
            this.hidden = hidden;
            this.predicate = predicate;
            this.tip = tip;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isHidden() {
            return this.hidden;
        }

        @Override
        public boolean checkPredicate(final String s) {
            return this.predicate.test(s);
        }

        @Override
        public String getToolTipText() {
            return this.tip;
        }

    }

    @Override
    public List<ISignupField> fields() {
        return Arrays.asList(SignupField.values());
    }

}
