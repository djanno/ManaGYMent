package view.panels.gym;

import java.util.List;

import view.panels.IGenericTable;
import model.gym.ICourse;
import model.gym.members.IEmployee;

/**
 * @author simone
 *Defines the {@link EditCoursePanel}
 */
public interface IEditCoursePanel extends IAddCoursePanel, IGenericTable {

    /**
     * @param course
     *          the course to be edit
     * @param employees
     *          the gym's coaches
     */         
    void showData(final ICourse course, final List<IEmployee> employees);

}
