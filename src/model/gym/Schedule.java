package model.gym;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import model.gym.members.IEmployee;
import utility.UtilityClass;

/**
 * A schedule of a {@link IGym} for a specific day. It's part of a {@link IGymCalendar}.
 * @author Federico Giannoni
 * @author Simone Letizi
 *
 */
public class Schedule implements Serializable, ISchedule {

    private static final long serialVersionUID = 2683209797124765098L;

    private static final String COURSE_ALREADY_PRESENT_IN_HOUR = "Corso gi� presente nella fascia oraria selezionata";
    private static final String EMPLOYEE_ALREADY_PRESENT_IN_HOUR = "L'istruttore che si vuole inserire insegna gi� un altro corso in quella fascia oraria";

    private boolean opened;
    private Integer openingHour;
    private Integer closingHour;
    private Map<Integer, List<Pair<ICourse, IEmployee>>> program;

    // considerare l'opzione Map<ICourse, InnerPair<IEmployee, Set<Integer>>
    // in tale mappa sarebbero salvati corso -> coppia impiegato ore lavorative
    // se si vuole salvare il nome del coach nel calendario.

    /**
     * Constructs a specific schedule with the parameters provided in input.
     * @param opened whether the gym is opened or not.
     * @param openingHour the opening hour of the gym.
     * @param closingHour the closing hour of the gym.
     * @param program the program of the gym.
     */
    public Schedule(final Boolean opened, final Integer openingHour, final Integer closingHour,
            final Map<Integer, List<Pair<ICourse, IEmployee>>> program) {
        super();
        this.opened = opened;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.program = program;
    }

    /**
     * Constructs a default schedule (gym closed).
     */
    public Schedule() {
        super();
        this.opened = false;
        this.openingHour = null;
        this.closingHour = null;
        this.program = new TreeMap<>();
    }

    @Override
    public boolean isOpened() {
        return this.opened;
    }
    
    @Override
    public Optional<Integer> getOpeningHour() {
        return Optional.ofNullable(this.openingHour);
    }
    
    @Override
    public Optional<Integer> getClosingHour() {
        return Optional.ofNullable(this.closingHour);
    }

    @Override
    public Map<Integer, List<Pair<ICourse, IEmployee>>> getProgram() {
        return this.program.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> UtilityClass.defend(e.getValue()), (e1, e2) -> e2, TreeMap::new));
    }

    @Override
    public List<ICourse> getCoursesInHour(final Integer hour) {
        final List<ICourse> list = new ArrayList<>();
        if (this.closingHour != null && hour < this.closingHour && this.openingHour != null && hour >= this.openingHour) {
            for (final Pair<ICourse, IEmployee> pair : this.program.getOrDefault(hour, new ArrayList<Pair<ICourse, IEmployee>>())) {
                list.add(pair.getX());
            }
        }
        return list;
    }

    @Override
    public void setOpened(final boolean opened) {
        this.opened = opened;
        if (!this.opened) {
            this.setOpeningHourAndClosingHour(null, null);
            this.program = new TreeMap<>();
        }
    }

    @Override
    public void setOpeningHourAndClosingHour(final Integer openingHour, final Integer closingHour) {
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        /*
         * for(Integer i = openingHour; i < closingHour; i++) {
         * this.program.putIfAbsent(i, new ArrayList<Pair<ICourse,
         * IEmployee>>()); }
         */
    }

    @Override
    public void setProgram(final Map<Integer, List<Pair<ICourse, IEmployee>>> program) {
        program.forEach((key, value) -> this.program.put(key, value));
    }

    @Override
    public boolean isGymOpenedAt(final Integer hour) {
        return this.opened && this.openingHour != null && this.openingHour <= hour && this.closingHour != null && this.closingHour > hour;
    }
    
    @Override
    public void removePairInHour(final Pair<ICourse, IEmployee> pair, final Integer hour) {
        this.program.get(hour).remove(pair);
        if (this.program.get(hour).isEmpty()) {
            this.program.remove(hour, this.program.get(hour));
        }
    }

    @Override
    public void putPairInHour(final Pair<ICourse, IEmployee> pair, final Integer hourFrom, final Integer hourTo) throws IllegalArgumentException {
        this.isAlreadyPresentInHour(pair, hourFrom, hourTo);
        IntStream.rangeClosed(hourFrom, hourTo - 1).forEach(hour -> {
            final List<Pair<ICourse, IEmployee>> list = this.program.getOrDefault(hour, new LinkedList<Pair<ICourse, IEmployee>>());
            list.add(pair);
            this.program.put(hour, list);
        });
    }

    @Override
    public void deletePair(final Pair<ICourse, IEmployee> pair) {
        // final List<Pair<ICourse, IEmployee>> pairsToDelete = new
        // ArrayList<>();
        /*
         * for(final List<Pair<ICourse, IEmployee>> list :
         * this.program.values()) { for(final Pair<ICourse, IEmployee> pair :
         * list) { if(pair.getX().equals(course)) { pairsToDelete.add(pair); } }
         * }
         */
        // this.program.values().stream()forEach(action);
        this.program.keySet().stream().collect(Collectors.toList()).forEach(hour -> removePairInHour(pair, hour));
        if (this.program.keySet().isEmpty()) {
            this.setOpened(false);
        }

    }

    @Override
    public String toString() {
        return "Schedule [opened=" + opened + ", openingHour=" + openingHour + ", closingHour=" + closingHour + ", program=" + program + "]";
    }

    /**
     * Checks whether or not a specified pair is already present in the specified time interval and if so, throws an
     * {@link IllegalArgumentException}.
     * @param pair the pair.
     * @param hourFrom the lower limit of the time interval.
     * @param hourTo the top limit of the time interval.
     * @throws IllegalArgumentException if the specified pair is present in the specified time interval.
     */
    private void isAlreadyPresentInHour(final Pair<ICourse, IEmployee> pair, final Integer hourFrom, final Integer hourTo) throws IllegalArgumentException {
        for (int i = hourFrom; i < hourTo; i++) {
            final List<Pair<ICourse, IEmployee>> list = this.program.getOrDefault(i, new LinkedList<Pair<ICourse, IEmployee>>());
            for (final Pair<ICourse, IEmployee> existingPair : list) {
                if (existingPair.getX().equals(pair.getX()) || existingPair.getY().equals(pair.getY())) {
                    if (existingPair.getX().equals(pair.getX())) {
                        throw new IllegalArgumentException(COURSE_ALREADY_PRESENT_IN_HOUR);
                    } else {
                        throw new IllegalArgumentException(EMPLOYEE_ALREADY_PRESENT_IN_HOUR);
                    }
                }
            }
        }
    }

    /**
     * A generic pair class.
     * @author Federico Giannoni
     * @author Simone Letizi
     *
     * @param <X>
     * @param <Y>
     */
    public static class Pair<X, Y> implements Serializable {

        private static final long serialVersionUID = 6209827569491880779L;

        private final X x;
        private final Y y;

        /**
         * Constructs a new pair that wraps the objects provided in input.
         * @param x the first element of the pair.
         * @param y the second element of the pair.
         */
        public Pair(final X x, final Y y) {
            super();
            this.x = x;
            this.y = y;
        }

        /**
         * Returns the first element of the pair.
         * @return the first element of the pair.
         */
        public X getX() {
            return x;
        }

        /**
         * Returns the second element of the pair.
         * @return the second element of the pair.
         */
        public Y getY() {
            return y;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((x == null) ? 0 : x.hashCode());
            result = prime * result + ((y == null) ? 0 : y.hashCode());
            return result;
        }

        @SuppressWarnings("rawtypes")
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }

            final Pair other = (Pair) obj;
            if (this.x == null && other.x != null) {
                return false;
            }

            if (!this.x.equals(other.x)) {
                return false;
            }

            if (y == null && other.y != null) {
                return false;
            }

            if (!this.y.equals(other.y)) {
                return false;
            }

            return true;
        }

    }
}
