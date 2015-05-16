package controller.panels.members;

import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;

import model.gym.ICourse;
import view.panels.members.IFormField;

public interface IEmployeeAddController {
	
	void cmdSave(final Map<IFormField,String> mapToPass, String salario, DefaultListModel<String> list);
	
	List<ICourse> loadCourses();
	
}
