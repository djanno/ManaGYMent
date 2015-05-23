package view.panels.members;

import java.util.Map;


public interface ICommonPanel {
	void setMap(final IFormField field, final String value);
	Map<IFormField, String> getMapToPass();
}
