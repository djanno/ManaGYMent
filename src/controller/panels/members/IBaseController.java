package controller.panels.members;

import java.util.List;
import javax.swing.DefaultListModel;
import model.gym.ICourse;

/**
 * Defines the {@link BaseController}.
 * 
 * @author Davide Borficchia
 *
 */

public interface IBaseController {

    /**
     * Convert the courses list of the view to List ICourse
     * 
     * @param list
     *            the DefaultListModel to convert
     * @param gymCourses
     *            the list of gym's courses
     * @return the List ICourse
     */
    List<ICourse> convertList(DefaultListModel<String> list, List<ICourse> gymCourses);

}
