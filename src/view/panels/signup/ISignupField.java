package view.panels.signup;

public interface ISignupField {

	String getName();
	
	int getLength();
	
	boolean isHidden();
	
	boolean checkPredicate(final String s);
	
	String getToolTipText();
	
}
