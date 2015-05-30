package model.gym;

import java.util.Map;

import model.gym.GymCalendar.DaysOfWeek;

/**
 * Interface that defines the behavior of a {@link GymCalendar}.
 * @author Federico Giannoni
 * @author Simone Letizi
 *
 */
public interface IGymCalendar {

    /**
     * Returns the calendar of the {@link Gym}.
     * @return the calendar of the gym.
     */
    Map<DaysOfWeek, ISchedule> getCalendar();

    /**
     * Sets the current {@link ISchedule} for the specified day.
     * @param day the day.
     * @param schedule the new schedule.
     */
    void setSchedule(final DaysOfWeek day, final ISchedule schedule);

}
