package controller.panels.gym;

import java.awt.Color;

public interface IAddCourseObserver {
    void addCourseCmd(final String courseName,final Color courseColor, final String price,final  String maxMembers) throws IllegalArgumentException;
}
