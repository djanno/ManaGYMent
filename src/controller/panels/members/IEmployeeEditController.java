package controller.panels.members;

import java.util.Map;
import view.panels.members.IFormField;

public interface IEmployeeEditController {
	void cmdSave(final Map<IFormField,String> mapToPass, String salario);
	void loadData(); //int index
}
