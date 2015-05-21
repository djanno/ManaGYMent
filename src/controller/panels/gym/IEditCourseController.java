package controller.panels.gym;

import java.awt.Color;

public interface IEditCourseController {
	
    void loadData();
    
    void formTable();

    void addCoachCmd(final int index);

    void removeCoachCmd(final int index);

    void editCourseCmd(final String courseName, final Color courseColor, final String price, final String maxMembers);
}
