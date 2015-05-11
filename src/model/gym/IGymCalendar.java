package model.gym;

import java.util.HashMap;
import java.util.Map;

import model.gym.GymCalendar.DaysOfWeek;

public interface IGymCalendar {

    HashMap<DaysOfWeek, Schedule> getCalendar();

}
