package model.gym;

import java.util.Map;

import model.gym.GymCalendar.DaysOfWeek;

public interface IGymCalendar {

    Map<DaysOfWeek, Schedule> getCalendar();

}
