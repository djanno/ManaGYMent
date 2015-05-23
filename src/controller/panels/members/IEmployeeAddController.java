package controller.panels.members;

import java.util.List;
import java.util.Map;
import model.gym.ICourse;
import view.panels.members.IFormField;

public interface IEmployeeAddController {
	
	void cmdSave(final Map<IFormField,String> mapToPass, String salario);
	
	List<ICourse> loadCourses();
	
}
