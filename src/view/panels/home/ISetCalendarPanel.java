package view.panels.home;

import java.util.List;

import model.gym.GymCalendar.DaysOfWeek;
import model.gym.ICourse;
import model.gym.Schedule;
import model.gym.members.IEmployee;
import controller.panels.home.ISetCalendarObserver;

public interface ISetCalendarPanel {
    
    void loadFields(final DaysOfWeek day, final Schedule schedule, final List<ICourse> gymCourses, final List<IEmployee> gymCoaches );
    
    void attachViewObserver(final ISetCalendarObserver observer);
}
