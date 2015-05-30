package model.gym;

import java.util.Map;

import model.gym.GymCalendar.DaysOfWeek;

public interface IGymCalendar {

    Map<DaysOfWeek, ISchedule> getCalendar();

    void setSchedule(final DaysOfWeek day, final ISchedule schedule);

}
