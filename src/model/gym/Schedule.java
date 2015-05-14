package model.gym;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.IntStream;

import model.gym.members.IEmployee;


public class Schedule implements Serializable {
    
    private static final long serialVersionUID = 2683209797124765098L;
    
    private static final String COURSE_ALREADY_PRESENT_IN_HOUR = "Corso già presente nella fascia oraria selezionata";
    private static final String EMPLOYEE_ALREADY_PRESENT_IN_HOUR = "L'istruttore che si vuole inserire insegna già un altro corso in quella fascia oraria" ;

    private boolean opened;
    private Optional<Integer> openingHour;
    private Optional<Integer> closingHour;
    private Map<Integer, List<Pair<ICourse, IEmployee>>> program;

    // considerare l'opzione Map<ICourse, InnerPair<IEmployee, Set<Integer>>
    // in tale mappa sarebbero salvati corso -> coppia impiegato ore lavorative
    // se si vuole salvare il nome del coach nel calendario.

    public Schedule(final Boolean opened, final Integer openingHour,
            final Integer closingHour,
            final Map<Integer, List<Pair<ICourse, IEmployee>>> program) {
        super();
        this.opened = opened;
        this.openingHour = Optional.ofNullable(openingHour);
        this.closingHour = Optional.ofNullable(closingHour);
        this.program = program;
    }
    
    public Schedule(){
            super();
            this.opened = false;
            this.openingHour = Optional.empty();
            this.closingHour = Optional.empty();
            this.program = new TreeMap<>((a, b) -> b.compareTo(a));
    }
    
    public boolean isOpened(){
            return this.opened;
    }
    
    public Optional<Integer> getOpeningHour(){
            return this.openingHour;
    }
    
    public Optional<Integer> getClosingHour(){
            return this.closingHour;
    }
    
    public List<ICourse> getCoursesInHour(final Integer hour) {
		final List<ICourse> list = new ArrayList<>();
		if(this.closingHour.isPresent() && hour < this.closingHour.get() && this.openingHour.isPresent() && hour >= this.openingHour.get()) {
			for (final Pair<ICourse, IEmployee> pair : this.program.get(hour)) {
				list.add(pair.getX());
			}
		}
		return list;
	}
    
    public boolean isGymOpenedAt(final Integer hour) {
		if (this.opened && this.openingHour.isPresent() && this.openingHour.get() <= hour && this.closingHour.isPresent() && this.closingHour.get() > hour) {
			return true;
		}
		return false;
	}
    
    public void removePairInHour(Pair<ICourse,IEmployee> pair, Integer hour){
        this.program.get(hour).remove(pair);
    }
    
    public void putPairInHour(Pair<ICourse,IEmployee> pair, Integer hourFrom, Integer hourTo) throws IllegalArgumentException{
        isAlreadyPresentInHour(pair, hourFrom, hourTo);
        IntStream.rangeClosed(hourFrom, hourTo-1).forEach(hour->{
                        List<Pair<ICourse,IEmployee>> list = this.program.getOrDefault(hourTo, new LinkedList<Pair<ICourse,IEmployee>>());
                        list.add(pair);
                        this.program.put(hour, list);
        });
    }
    
    public void deletePairsWithCourse(final ICourse course) {
    	final List<Pair<ICourse, IEmployee>> pairsToDelete = new ArrayList<>();
    	for(final List<Pair<ICourse, IEmployee>> list : this.program.values()) {
    		for(final Pair<ICourse, IEmployee> pair : list) {
    			if(pair.getX().equals(course)) {
    				pairsToDelete.add(pair);
    			}
    		}
    	}
    	
    	for(final Pair<ICourse, IEmployee> pair : pairsToDelete) {
    		this.program.keySet().forEach( key -> {
        		this.program.get(key).remove(pair);
        	});
    	}
    }
    
    public Map<Integer, List<Pair<ICourse,IEmployee>>> getProgram(){
            return new TreeMap<Integer, List<Pair<ICourse,IEmployee>>>(this.program);
    }
    
    public void setOpened(final boolean opened){
            this.opened = opened;
            if(!this.opened){
                    this.setOpeningHourAndClosingHour(null, null);
                    this.program = new TreeMap<>();
            }
    }
    
    public void setOpeningHourAndClosingHour(final Integer openingHour, final Integer closingHour) {
		this.openingHour = Optional.ofNullable(openingHour);
		this.closingHour = Optional.ofNullable(closingHour);
		for(Integer i = openingHour; i < closingHour; i++) {
			this.program.putIfAbsent(i, new ArrayList<Pair<ICourse, IEmployee>>());
		}
	}
    
    public void setProgram(final Map<Integer, List<Pair<ICourse,IEmployee>>> program){
            program.forEach((key, value) -> this.program.put(key, value));
    }

    @Override
    public String toString() {
        return "Schedule [opened=" + opened + ", openingHour="
                + openingHour + ", closingHour=" + closingHour
                + ", program=" + program + "]";
    }
    
    private void isAlreadyPresentInHour(Pair<ICourse, IEmployee> pair, Integer hourFrom, Integer hourTo) throws IllegalArgumentException {
        for (int i = hourFrom; i < hourTo; i++) {
            List<Pair<ICourse, IEmployee>> list = this.program.getOrDefault(i, new LinkedList<Pair<ICourse, IEmployee>>());
            for (Pair<ICourse, IEmployee> existingPair : list) {
                if (existingPair.getX().equals(pair.getX()) || existingPair.getY().equals(pair.getY())) {
                    if(existingPair.getX().equals(pair.getX())){
                        throw new IllegalArgumentException(COURSE_ALREADY_PRESENT_IN_HOUR);
                    }else{
                        throw new IllegalArgumentException(EMPLOYEE_ALREADY_PRESENT_IN_HOUR);
                    }
                    
                }
            }

        }
    }
    
}
