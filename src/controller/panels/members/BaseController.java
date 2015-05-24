package controller.panels.members;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import model.gym.ICourse;

public class BaseController {

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
