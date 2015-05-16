package controller.panels.members;

import java.util.Map;

import javax.swing.DefaultListModel;

import view.panels.members.IFormField;

public interface IEmployeeEditController {
	void cmdSave(final Map<IFormField,String> mapToPass, String salario, DefaultListModel<String> list);
	void loadData(); //int index
}
