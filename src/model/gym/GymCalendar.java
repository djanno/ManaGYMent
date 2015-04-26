package model.gym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GymCalendar implements IGymCalendar {
	
    private final Map<DaysOfWeek, Schedule> calendar;

    public GymCalendar(){
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
    public HashMap<DaysOfWeek, Schedule> getCalendar(){
    	return new HashMap<DaysOfWeek, Schedule>(this.calendar); 
    }
    
    public void setSchedule(DaysOfWeek day,Schedule schedule){
        calendar.put(day,schedule);
    }
    
    
    public enum DaysOfWeek {        
        LUNEDI("Lunedì"),
        MARTEDI("Martedì"),
        MERCOLEDI("Mercoledì"),
        GIOVEDI("Giovedì"),
        VENERDI("Venerdi"),
        SABATO("Sabato"),
        DOMENICA("Domenica");
    
        private final String name;
        
        public String getName(){
            return this.name;
        }
        
        private DaysOfWeek(String name){
            this.name=name;
        }
        
        public static DaysOfWeek getValueByName(String name){
            for(DaysOfWeek day:DaysOfWeek.values()){
                if(day.getName().equals(name)){
                    return day;
                }
            }
            throw new IllegalArgumentException("Nome del giorno inserito non esistente");
        }
        
        public static List<String> namesOfDayVector(){
            final List<String> names = new ArrayList<String>();
            for(final DaysOfWeek day : DaysOfWeek.values()){
                names.add(day.getName());
            }
            return names;
        }
    }

    //serve un metodo per modificare la mappa
    
    
}
