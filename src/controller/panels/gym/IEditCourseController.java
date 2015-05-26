package controller.panels.gym;

import java.awt.Color;

/**
 * Defines the {@link EditCourseController}
 * @author simone
 *
 */
public interface IEditCourseController {
	
    /**
     * 
     */
    void loadData();
    
    /**
     * 
     */
    void formTable();

    /**
     * @param index the coach's index in gym coaches list to be add
     */
    void addCoachCmd(final int index);

    /**
     * @param index the coach's index in gym coaches list to be remove
     */
    void removeCoachCmd(final int index);

    /**
     * @param courseName the new name of the course to be edit
     * @param courseColor the new color of the course to be edit
     * @param price the new price of the course to be edit 
     * @param maxMembers the new numbers of max members of the course to be edit
     */
    void editCourseCmd(final String courseName, final Color courseColor, final String price, final String maxMembers);
}
