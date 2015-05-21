package view.panels.members;

import java.util.Map;


public interface ICommonPanel {
	void setMap(IFormField field, String value);
	Map<IFormField, String> getMapToPass();
}
