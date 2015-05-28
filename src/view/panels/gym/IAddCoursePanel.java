package view.panels.gym;

import controller.panels.gym.IAddCourseController;

/**
 * @author simone 
 * Defines the {@link AddCoursePanel}
 */
public interface IAddCoursePanel {
    /**
     * Changes the current {@link IAddCourseController}.
     * 
     * @param observer
     *            the new {@link IAddCourseController}.
     */
    void attachViewObserver(final IAddCourseController observer);

}
