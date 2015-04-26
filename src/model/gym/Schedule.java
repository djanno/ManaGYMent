package model.gym;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.IntStream;

import model.gym.members.IEmployee;


public class Schedule {
    
    private boolean opened;
    private Optional<Integer> openingHour;
    private Optional<Integer> closingHour;
    private Map<Integer, List<Pair<ICourse,IEmployee>>> program;
    //considerare l'opzione Map<ICourse, InnerPair<IEmployee, Set<Integer>>
    //in tale mappa sarebbero salvati corso -> coppia impiegato ore lavorative
    //se si vuole salvare il nome del coach nel calendario.
    
    public Schedule(final Boolean opened, final Integer openingHour, final Integer closingHour, final Map<Integer,List<Pair<ICourse,IEmployee>>> program2){
            super();
            this.opened = opened;
            this.openingHour = Optional.ofNullable(openingHour);
            this.closingHour = Optional.ofNullable(closingHour);
            this.program = program2;
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
    
    public void removePairInHour(Pair<ICourse,IEmployee> pair, Integer hour){
        this.program.get(hour).remove(pair);
    }
    
    public void putPairInHour(Pair<ICourse,IEmployee> pair, Integer hourFrom, Integer hourTo) throws IllegalArgumentException{
        isAlreadyPresentInHour(pair, hourFrom, hourTo);
        IntStream.rangeClosed(hourFrom, hourTo-1).forEach(hour->{
//                        List<Pair<ICourse,IEmployee>> list = this.program.getOrDefault(hourTo, new LinkedList<Pair<ICourse,IEmployee>>());
            List<Pair<ICourse,IEmployee>> list=this.program.get(hour);
                if(list==null){
                    list=new LinkedList<Pair<ICourse,IEmployee>>();
                }
                        list.add(pair);
                        this.program.put(hour, list);
        });
    }
    
    public Map<Integer, List<Pair<ICourse,IEmployee>>> getProgram(){
            return new TreeMap<Integer, List<Pair<ICourse,IEmployee>>>(this.program);
    }
    
    public void setOpened(final boolean opened){
            this.opened = opened;
            if(!this.opened){
                    this.setOpeningHour(null);
                    this.setClosingHour(null);
                    this.program = new TreeMap<>();
            }
    }
    
    public void setOpeningHour(final Integer openingHour){
            //è giusto non fare controlli perchè da noi non è modificabile se opened = false?
            this.openingHour = Optional.ofNullable(openingHour);
    }
    
    public void setClosingHour(final Integer closingHour){
            this.closingHour = Optional.ofNullable(closingHour);
    }
    
    public void setProgram(final Map<Integer, List<Pair<ICourse,IEmployee>>> program){
            this.program = program;
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
                    throw new IllegalArgumentException("Corso già presente nella fascia oraria selezionata");
                }
            }

        }

    }
}
