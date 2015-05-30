package controller.panels.gym;

/**
 * Defines the {@link GymPanelController}
 * 
 * @author Federico Giannoni
 *
 */
public interface IGymPanelController {

    /**
     * Manages the procedure that loads the gym's income data from the model
     * into a table.
     */
    void loadIncomeTable();

    /**
     * Manages the procedure that loads the gym's courses data from the model
     * into a table.
     */
    void loadCoursesTable();

    /**
     * Builds a Dialog Window and its respective controller that allow the user
     * to add a new course to his/her gym.
     */
    void cmdAddCourse();

    /**
     * Builds a Dialog Window and its respective controller that allow the user
     * to edit a course of his/her gym.
     * 
     * @param index
     *            the index of the course to be edited (it's both the index of
     *            the course in the table view and in the data model).
     */
    void cmdEditCourse(final int index);

    /**
     * Builds a Dialog Window and its respective controller that allow the user
     * to remove a course from his/her gym.
     * 
     * @param index
     *            the index of the course to be removed (it's both the index of
     *            the course in the table view and in the data model).
     */
    void cmdDeleteCourse(final int index);

}
