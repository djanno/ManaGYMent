package model.gym;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The calendar of a {@link Gym} containing the {@link Schedule} for each day of the week.
 * @author Federico Giannoni
 * @author Simone Letizi
 *
 */
public class GymCalendar implements IGymCalendar, Serializable {

    private static final long serialVersionUID = 1198724186646197873L;

    private Map<DaysOfWeek, ISchedule> calendar;

    /**
     * Constructs a default gym calendar.
     */
    public GymCalendar() {
        super();
        this.calendar = new HashMap<>();
        this.calendar.put(DaysOfWeek.LUNEDI, new Schedule());
        this.calendar.put(DaysOfWeek.MARTEDI, new Schedule());
        this.calendar.put(DaysOfWeek.MERCOLEDI, new Schedule());
        this.calendar.put(DaysOfWeek.GIOVEDI, new Schedule());
        this.calendar.put(DaysOfWeek.VENERDI, new Schedule());
        this.calendar.put(DaysOfWeek.SABATO, new Schedule());
        this.calendar.put(DaysOfWeek.DOMENICA, new Schedule());
    }

    @Override
    public Map<DaysOfWeek, ISchedule> getCalendar() {
        return new HashMap<DaysOfWeek, ISchedule>(this.calendar);
    }

    @Override
    public void setSchedule(final DaysOfWeek day, final ISchedule schedule) {
        calendar.replace(day, schedule);
    }

    /**
     * Enumerator whom values represents the days of the week.
     * @author Federico Giannoni
     * @author Simone Letizi
     *
     */
    public enum DaysOfWeek implements Serializable {

        LUNEDI("Lunedì"), MARTEDI("Martedì"), MERCOLEDI("Mercoledì"), GIOVEDI("Giovedì"), VENERDI("Venerdi"), SABATO("Sabato"), DOMENICA("Domenica");

        private final String name;

        /**
         * Returns the name of the day.
         * @return the name of the day.
         */
        public String getName() {
            return this.name;
        }

        /**
         * Constructor.
         * @param name the name of the day.
         */
        private DaysOfWeek(final String name) {
            this.name = name;
        }

        /**
         * Returns the enumerator value corresponding to the name provided in input.
         * @param name the name.
         * @return the enumerator value corresponding to the  name provided in input.
         */
        public static DaysOfWeek getValueByName(final String name) {
            for (final DaysOfWeek day : DaysOfWeek.values()) {
                if (day.getName().equals(name)) {
                    return day;
                }
            }
            throw new IllegalArgumentException("Nome del giorno inserito non esistente");
        }

        /**
         * Returns a list containing the names of each day of the week.
         * @return a list containing the names of each day of the week.
         */
        public static List<String> namesOfDayVector() {
            final List<String> names = new ArrayList<String>();
            for (final DaysOfWeek day : DaysOfWeek.values()) {
                names.add(day.getName());
            }
            return names;
        }
    }
}
