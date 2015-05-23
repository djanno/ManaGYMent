package view.panels.signup;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class SignupStrategy implements ISignupStrategy {

	public SignupStrategy() {
		super();
	}
	
	public enum SignupField implements ISignupField {
		
		NAME ("nome: ", 20, false, x->(x.length() > 1 && x.length() < 20), "Inserire il proprio nome (max 20 caratteri)."),
		SURNAME ("cognome: ", 20, false, x->(x.length() > 1 && x.length() < 20), "Inserire il proprio cognome (max 20 caratteri)."),
		GYM_NAME ("nome palestra: ", 20, false,  x->(x.length() > 1 && x.length() < 20), "Inserire il nome della palestra (max 20 caratteri)."),
		USERNAME ("username: ", 20, false, x->(x.length() > 4 && x.length() < 20), "Inserire il proprio username (max 20 caratteri)."),
		PASSWORD ("password: ", 20, true, x->(x.length() > 7), "Inserire la propria password (almeno 7 caratteri)."),
		CONFIRM_PASSWORD ("conferma password: ", 20, true, x->(x.length() > 7), "Reinserire la propria password."),
		EMAIL ("email: ", 20, false, x->(Pattern.compile(SignupField.EMAIL_PATTERN).matcher(x).matches() && (x.contains("@gmail.com") || x.contains("@yahoo.com") || x.contains("@yahoo.it"))), "Inserire la propria email (solo gmail.com, yahoo.com o yahoo.it)."),
		CONFIRM_EMAIL ("conferma email: ", 20, false, x->(Pattern.compile(SignupField.EMAIL_PATTERN).matcher(x).matches() && (x.contains("@gmail.com") || x.contains("@yahoo.com") || x.contains("@yahoo.it"))), "Reinserire la propria email.");
		
		
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
