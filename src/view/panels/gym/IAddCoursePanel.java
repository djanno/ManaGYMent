package view.panels.gym;

import view.panels.IBackground;
import controller.panels.gym.IAddCourseController;

public interface IAddCoursePanel extends IBackground {
	
    void attachViewObserver(final IAddCourseController observer);
    
}
