package model.gym;

import java.util.Map;

import model.gym.GymCalendar.DaysOfWeek;
import model.gym.members.IEmployee;

public interface IGymCalendar {

    Map<DaysOfWeek, Schedule> getCalendar();
    
    void setSchedule(final DaysOfWeek day, final Schedule schedule);

}
