package model.gym;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.gym.members.IEmployee;

public interface ISchedule {

    boolean isOpened();

    Optional<Integer> getOpeningHour();

    Optional<Integer> getClosingHour();

    Map<Integer, List<Pair<ICourse, IEmployee>>> getProgram();
    
    List<ICourse> getCoursesInHour(Integer hour);

    void setOpened(boolean opened);

    void setOpeningHourAndClosingHour(Integer openingHour, Integer closingHour);
    
    boolean isGymOpenedAt(Integer hour);

    void removePairInHour(Pair<ICourse, IEmployee> pair, Integer hour);

    void putPairInHour(Pair<ICourse, IEmployee> pair, Integer hourFrom,
            Integer hourTo) throws IllegalArgumentException;

    void deletePair(Pair<ICourse, IEmployee> pair);

}
