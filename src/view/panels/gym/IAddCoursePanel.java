package view.panels.gym;

import controller.panels.gym.IAddCourseController;

public interface IAddCoursePanel {
	
    void attachViewObserver(final IAddCourseController observer);
    
}
