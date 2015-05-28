package controller.panels.gym;

import java.awt.Color;

/**
 * Defines the {@link AddCourseController}.
 * 
 * @author simone
 *
 */
public interface IAddCourseController {

    /**
     * Adds a new course
     * 
     * @param courseName
     *            the course name
     * @param courseColor
     *            the course color
     * @param price
     *            the course price
     * @param maxMembers
     *            the number of max members
     */
    void addCourseCmd(final String courseName, final Color courseColor, final String price, final String maxMembers);
}
