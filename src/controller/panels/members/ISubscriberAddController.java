package controller.panels.members;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import model.gym.ICourse;
import view.panels.members.IFormField;

public interface ISubscriberAddController {
	List<ICourse> loadCourses();
	void cmdSave(final Map<IFormField, String> mapToPass, final Date subscriptionDate, final Date expirationDate, final DefaultListModel<String> list);
}
