package model.gym;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;
import utility.UtilityClass;
//import exceptions.CourseIsFullException;
import exceptions.CourseIsFullException;

public class Course implements ICourse, Serializable {

    private static final long serialVersionUID = 6012814332577508826L;

    private static final String COACH_ALREDY_PRESENT = "Questo coach insegna già in questo corso";

    private String name;
    private double price;
    private final List<ISubscriber> members;
    private final List<IEmployee> coaches;
    private int maxMembers;
    private Color color;

    public Course(final String name, final Color color, final double price, final int maxMembers) {
        super();
        this.name = name;
        this.price = price;
        this.color = color;
        this.maxMembers = maxMembers;
        this.members = new ArrayList<>();
        this.coaches = new ArrayList<>();
    }

    public Course(final String name, final Color color, final double price, final int maxMembers, final List<IEmployee> coaches,
            final List<ISubscriber> members) {
        this(name, color, price, maxMembers);
        this.members.addAll(members);
        this.coaches.addAll(coaches);
    }

    @Override
    public String getCourseName() {
        return this.name;
    }

    @Override
    public double getCoursePrice() {
        return this.price;
    }

    @Override
    public List<ISubscriber> getCurrentMembers() {
        return UtilityClass.defend(this.members);
    }

    @Override
    public List<IEmployee> getCoaches() {
        return UtilityClass.defend(this.coaches);
    }

    @Override
    public int getMaxMembers() {
        return this.maxMembers;
    }

    @Override
    public Color getCourseColor() {
        return this.color;
    }

    @Override
    public void setCoursePrice(final double price) {
        this.price = price;
    }

    @Override
    public void setMaxMembers(final int max) {
        this.maxMembers = max;
    }

    @Override
    public boolean hasCoaches() {
        return !coaches.isEmpty();
    }

    @Override
    public IEmployee getCoachByFiscalCode(final String fiscalCode) {
//        for (final IEmployee employee : this.coaches) {
//            if (employee.getFiscalCode().equalsIgnoreCase(fiscalCode)) {
//                return employee;
//            }
//        }
        return this.coaches.stream().filter(employee->employee.getFiscalCode().equalsIgnoreCase(fiscalCode)).findAny().orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void addMember(final ISubscriber member) throws CourseIsFullException { // CourseIsFullException
                                                                                   // {
        if (this.members.size() == this.maxMembers) {
            throw new CourseIsFullException(); // CourseIsFullException("Impossibile aggiungere altri iscritti al corso. Numero massimo di iscritti al corso raggiunto.");
        } else {
            this.members.add(member);
        }
    }

    @Override
    public void addCoach(final IEmployee coach) {
        // facciamo in modo che gli impiegati possano svolgere solo determinati
        // corsi?
        if (coaches.contains(coach)) {
            throw new IllegalArgumentException(COACH_ALREDY_PRESENT);
        } else {
            this.coaches.add(coach);
        }
    }

    @Override
    public void removeMember(final int indexOfMember) throws IllegalArgumentException {
        // potrebbero essere inutili i controlli nel nostro caso
        if (this.members.get(indexOfMember) == null) {
            throw new IllegalArgumentException("Iscritto non trovato.");
        } else {
            //this.members.get(indexOfMember).removeFromCourse(this);
            this.members.remove(indexOfMember);
        }
    }

    @Override
    public void removeMember(final ISubscriber member) throws IllegalArgumentException {
        // potrebbero essere inutili i controlli nel nostro caso
        if (!this.members.contains(member)) {
            throw new IllegalArgumentException("Iscritto non trovato.");
        } else {
            //this.members.get(this.members.indexOf(member)).removeFromCourse(this);
            this.members.remove(member);
        }
    }

    @Override
    public void removeExpiredMembers() {
        this.members.removeAll(this.members.stream().filter(member -> member.isExpired()).collect(Collectors.toList()));
    }

    @Override
    public void removeCoach(final int indexOfCoach) throws IllegalArgumentException {
        // potrebbero essere inutili i controlli nel nostro caso
        if (this.coaches.get(indexOfCoach) == null) {
            throw new IllegalArgumentException("Coach non trovato.");
        } else {
            this.coaches.remove(indexOfCoach);
        }
    }

    @Override
    public void removeCoach(final IEmployee coach) throws IllegalArgumentException {
        // potrebbero essere inutili i controlli nel nostro caso
        if (!this.coaches.contains(coach)) {
            throw new IllegalArgumentException("Coach non trovato.");
        } else {
            this.coaches.remove(coach);
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Course) {
            final Course course = (Course) obj;
            if (this.name.equals(course.getCourseName()) && this.color.equals(course.getCourseColor()) && this.price == course.getCoursePrice()
                    && this.maxMembers == course.getMaxMembers() && this.coaches.equals(course.getCoaches())
                    && this.members.equals(course.getCurrentMembers())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setCourseName(String name) {
        this.name = name;
    }

    @Override
    public void setCourseColor(Color color) {
        this.color = color;
    }

    // gli ultimi 2 controlli non sono necessari, la rimozione di un iscritto da
    // un corso
    // viene fatta quando scade l'iscrizione. Per un coach dalla pagina del
    // coach.

}
