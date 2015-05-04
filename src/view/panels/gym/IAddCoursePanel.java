package view.panels.gym;

import controller.panels.gym.IAddCourseObserver;

public interface IAddCoursePanel {
    void attachViewObserver(final IAddCourseObserver observer);
    
    void showError(final String message);
}
