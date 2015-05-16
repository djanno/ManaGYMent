package controller.panels.members;

import java.util.List;

import javax.swing.DefaultListModel;

import model.gym.ICourse;

public interface IBaseController {
	List<ICourse> convertList(DefaultListModel<String> list, List<ICourse> gymCourses);
}
