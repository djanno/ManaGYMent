package view.panels.gym;

import java.awt.Color;

/**
 * @author simone
 * Defines the {@link AddCoursePanel}
 */
public interface IEssentialPanelCourse {

    /**
     * @param courseName
     *          the name of the course
     */
    void setCourseName(String courseName);

    /**
     * @param price
     *          the price of the course
     */
    void setCoursePrice(double price);

    /**
     * @param color
     *          the color of the course
     */
    void setCourseColor(Color color);

    /**
     * @param maxMember
     *          the max number of member of the course
     */
    void setCourseMaxMember(int maxMember);

    /**
     * @return
     *          the number of max member assigned to a course
     */
    String getCourseMaxMember();

    /**
     * @return
     *          the price assigned to a course
     */
    String getCoursePrice();

    /**
     * @return
     *          the name assigned to a course
     */
    String getCourseName();

    /**
     * @return
     *          the color assigned to a course
     */
    Color getButtonColor();

}
