package view.panels.gym;

import java.awt.Color;

public interface IEssentialPanelCourse {
    
    void setCourseName(String courseName); 
    
    void setCoursePrice(double price);
    
    void setCourseColor(Color color);
    
    void setCourseMaxMember(int maxMember);
    
    String getCourseMaxMember();
    
    String getCoursePrice();
    
    String getCourseName();
    
    Color getButtonColor();
    
}
