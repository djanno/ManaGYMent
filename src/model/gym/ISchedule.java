package model.gym;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.gym.Schedule.Pair;
import model.gym.members.IEmployee;

/**
 * Interface that defines the behavior of a {@link Schedule}.
 * @author Federico Giannoni
 * @author Simone Letizi
 *
 */
public interface ISchedule {

    /**
     * Returns if the gym is opened or not.
     * @return true if the gym is opened, false otherwise.
     */
    boolean isOpened();

    /**
     * Returns the opening hour of the gym.
     * @return the opening hour of the gym, or an empty {@link Optional} if the gym is closed.
     */
    Optional<Integer> getOpeningHour();

    /**
     * Returns the closing hour of the gym.
     * @return the closing hour of the gym or an empty {@link Optional} if the gym's closed.
     */
    Optional<Integer> getClosingHour();

    /**
     * Returns the program of which the schedule consists of.
     * @return the program of which the schedule onsists of.
     */
    Map<Integer, List<Pair<ICourse, IEmployee>>> getProgram();

    /**
     * Returns the list of courses that will take place in the specified hour of the schedule.
     * @param hour the hour in which the courses will take place.
     * @return the courses that will take place in the hour provided in input.
     */
    List<ICourse> getCoursesInHour(final Integer hour);

    /**
     * Sets the gym to opened/closed.
     * @param opened specifies whether the gym has to be opened (true) or closed (false).
     */
    void setOpened(final boolean opened);

    /**
     * Sets the opening and closing hour for the schedule.
     * @param openingHour the new opening hour.
     * @param closingHour the new closing hour.
     */
    void setOpeningHourAndClosingHour(final Integer openingHour, final Integer closingHour);

    /**
     * Sets the current program of the schedule.
     * @param program the new program.
     */
    void setProgram(final Map<Integer, List<Pair<ICourse, IEmployee>>> program);
    
    /**
     * Checks if the gym is opened or not at the specified hour.
     * @param hour the hour.
     * @return true if the gym is opened at the specified hour, false otherwise.
     */
    boolean isGymOpenedAt(final Integer hour);

    /**
     * Removes a {@link ICourse} - {@link IEmployee} {@link Pair} from the schedule in the specified hour.
     * @param pair the pair to be removed.
     * @param hour the hour in which the specified pair has to be removed.
     */
    void removePairInHour(final Pair<ICourse, IEmployee> pair, final Integer hour);

    /**
     * Puts a {@link ICourse} - {@link IEmployee} {@link Pair} in the schedule in a time interval.
     * @param pair the pair to be added to the schedule.
     * @param hourFrom the lower limit of the interval in which the pair will be added.
     * @param hourTo the top limit of the interval in which the pair will be added.
     * @throws IllegalArgumentException if the specified pair is already present in the interval.
     */
    void putPairInHour(final Pair<ICourse, IEmployee> pair, final Integer hourFrom, final Integer hourTo) throws IllegalArgumentException;

    /**
     * Deletes all the occurrences of the specified pair from the schedule.
     * @param pair the pair to be deleted from the schedule.
     */
    void deletePair(final Pair<ICourse, IEmployee> pair);

}
