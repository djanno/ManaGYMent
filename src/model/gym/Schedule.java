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


public class Schedule implements Serializable,ISchedule {
    
    private static final long serialVersionUID = 2683209797124765098L;
    
    private static final String COURSE_ALREADY_PRESENT_IN_HOUR = "Corso gi� presente nella fascia oraria selezionata";
    private static final String EMPLOYEE_ALREADY_PRESENT_IN_HOUR = "L'istruttore che si vuole inserire insegna gi� un altro corso in quella fascia oraria" ;

    private boolean opened;
    private Integer openingHour;
    private Integer closingHour;
    private Map<Integer, List<Pair<ICourse, IEmployee>>> program;

    // considerare l'opzione Map<ICourse, InnerPair<IEmployee, Set<Integer>>
    // in tale mappa sarebbero salvati corso -> coppia impiegato ore lavorative
    // se si vuole salvare il nome del coach nel calendario.

    public Schedule(final Boolean opened, final Integer openingHour,
            final Integer closingHour,
            final Map<Integer, List<Pair<ICourse, IEmployee>>> program) {
        super();
        this.opened = opened;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.program = program;
    }
    
    public Schedule(){
            super();
            this.opened = false;
            this.openingHour = null;
            this.closingHour = null;
            this.program = new TreeMap<>();
    }
    
    public boolean isOpened(){
            return this.opened;
    }
    
    public Optional<Integer> getOpeningHour(){
            return Optional.ofNullable(this.openingHour);
    }
    
    public Optional<Integer> getClosingHour(){
            return Optional.ofNullable(this.closingHour);
    }
    
    public Map<Integer, List<Pair<ICourse,IEmployee>>> getProgram(){
        return this.program.entrySet().stream().collect(Collectors.toMap(e->e.getKey(),e-> UtilityClass.defend(e.getValue()), (e1,e2)-> e2 ,TreeMap::new));
    }
    
    public List<ICourse> getCoursesInHour(final Integer hour) {
		final List<ICourse> list = new ArrayList<>();
		if(this.closingHour!=null && hour < this.closingHour && this.openingHour!=null && hour >= this.openingHour) {
			for (final Pair<ICourse, IEmployee> pair : this.program.getOrDefault(hour, new ArrayList<Pair<ICourse, IEmployee>>())) {
				list.add(pair.getX());
			}
		}
		return list;
	}
    
    public void setOpened(final boolean opened){
            this.opened = opened;
            if(!this.opened){
                    this.setOpeningHourAndClosingHour(null, null);
                    this.program = new TreeMap<>();
            }
    }
    
    public void setOpeningHourAndClosingHour(final Integer openingHour, final Integer closingHour) {
		this.openingHour = openingHour;
		this.closingHour = closingHour;
		/*for(Integer i = openingHour; i < closingHour; i++) {
			this.program.putIfAbsent(i, new ArrayList<Pair<ICourse, IEmployee>>());
		}*/
	}
    
    public void setProgram(final Map<Integer, List<Pair<ICourse,IEmployee>>> program){
            program.forEach((key, value) -> this.program.put(key, value));
    }
    
    public boolean isGymOpenedAt(final Integer hour) {
		if (this.opened && this.openingHour != null && this.openingHour <= hour && this.closingHour != null && this.closingHour > hour) {
			return true;
		}
		return false;
	}
    
    public void removePairInHour(Pair<ICourse,IEmployee> pair, Integer hour){
        this.program.get(hour).remove(pair);
        if(this.program.get(hour).isEmpty()) {
        	this.program.remove(hour, this.program.get(hour));
        }
    }
    
    public void putPairInHour(Pair<ICourse,IEmployee> pair, Integer hourFrom, Integer hourTo) throws IllegalArgumentException{
        this.isAlreadyPresentInHour(pair, hourFrom, hourTo);
        IntStream.rangeClosed(hourFrom, hourTo-1).forEach(hour->{
        	List<Pair<ICourse,IEmployee>> list = this.program.getOrDefault(hour, new LinkedList<Pair<ICourse,IEmployee>>());
                        list.add(pair);
                        this.program.put(hour, list);
        });
    }
    
    public void deletePair(final Pair<ICourse, IEmployee> pair) {
//    	final List<Pair<ICourse, IEmployee>> pairsToDelete = new ArrayList<>();
    	/*for(final List<Pair<ICourse, IEmployee>> list : this.program.values()) {
    		for(final Pair<ICourse, IEmployee> pair : list) {
    			if(pair.getX().equals(course)) {
    				pairsToDelete.add(pair);
    			}
    		}
    	}*/
//    	this.program.values().stream()forEach(action);
    	this.program.keySet().stream().collect(Collectors.toList()).forEach(hour -> removePairInHour(pair, hour));
    	if(this.program.keySet().isEmpty()) {
    		this.setOpened(false);
    	}
    	
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
    
    public static class Pair<X,Y> implements Serializable {
        

        private static final long serialVersionUID = 6209827569491880779L;

        private final X x;
        private final Y y;

        public Pair(final X x, final Y y) {
            super();
            this.x = x;
            this.y = y;
        }

        public X getX() {
            return x;
        }

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
