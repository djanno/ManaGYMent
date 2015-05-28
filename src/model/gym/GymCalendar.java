package model.gym;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GymCalendar implements IGymCalendar, Serializable {

    private static final long serialVersionUID = 1198724186646197873L;

    private Map<DaysOfWeek, Schedule> calendar;

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
    public Map<DaysOfWeek, Schedule> getCalendar() {
        return new HashMap<DaysOfWeek, Schedule>(this.calendar);
    }

    @Override
    public void setSchedule(final DaysOfWeek day, final Schedule schedule) {
        calendar.replace(day, schedule);
    }

    public enum DaysOfWeek implements Serializable {

        LUNEDI("Lunedì"), MARTEDI("Martedì"), MERCOLEDI("Mercoledì"), GIOVEDI("Giovedì"), VENERDI("Venerdi"), SABATO("Sabato"), DOMENICA("Domenica");

        private final String name;

        public String getName() {
            return this.name;
        }

        private DaysOfWeek(final String name) {
            this.name = name;
        }

        public static DaysOfWeek getValueByName(final String name) {
            for (final DaysOfWeek day : DaysOfWeek.values()) {
                if (day.getName().equals(name)) {
                    return day;
                }
            }
            throw new IllegalArgumentException("Nome del giorno inserito non esistente");
        }

        public static List<String> namesOfDayVector() {
            final List<String> names = new ArrayList<String>();
            for (final DaysOfWeek day : DaysOfWeek.values()) {
                names.add(day.getName());
            }
            return names;
        }
    }

    // serve un metodo per modificare la mappa

}
