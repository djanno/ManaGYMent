package controller.panels.members;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import model.gym.ICourse;


/**
 * Common controller of EmployeeAddController and SubscriberAddController
 * @author Davide Borficchia
 *
 */

public class BaseController implements IBaseController{

	@Override
	public List<ICourse> convertList(final DefaultListModel<String> list, final List<ICourse> gymCourses){
		final List<ICourse> corsi = new ArrayList<>();
		for(int i = 0; i < list.size(); i++ ){
			for(final ICourse c : gymCourses){
				if (list.getElementAt(i).equals(c.getCourseName())){
					corsi.add(c);
				}
			}	
		}
		return corsi;
	}
}
