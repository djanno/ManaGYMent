package view.panels.members;

import java.util.function.Predicate;

public interface IFormField {
	String getField();
	Predicate<String> getPred();
}
