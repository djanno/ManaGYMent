package model.gym.members;

import java.util.List;

import model.gym.ICourse;
import model.gym.IGym;

public abstract class AbstractGymMember implements IGymMember {
	
    private final String name;
    private final String surname;
    private final String fiscalCode;
    private String address;
    private String phoneNumber;
    private String email;
    private final IGym gym;
    private List<ICourse> corsi;

    //le eccezioni andranno catturate nel controller. Possibilità di utilizzo strategy.
    public AbstractGymMember(final String name, final String surname, final String fiscalCode, final String address, final String phoneNumber, final String email, final IGym gym, final List<ICourse> corsi){
        super();
        this.name = name;
        this.surname = surname;
        this.fiscalCode = fiscalCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gym = gym;
        this.corsi = corsi;
    }

//    aggiunto
    public abstract Object[] createRow();
 
    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getSurname(){
        return this.surname;
    }

    @Override
    public String getFiscalCode(){
        return this.fiscalCode;
    }

    @Override
    public String getAddress(){
        return this.address;
    }

    @Override
    public String getNumber(){
        return this.phoneNumber;
    }

    @Override
    public String getEmail(){
        return this.email;
    }
    
    @Override
    public IGym getGym(){
    	return this.gym;
    }
    
    @Override
    public List <ICourse> getCourses(){
        return this.corsi;
    }

    @Override
    public void setEmail(final String email){
    	this.email = email;
    }

    @Override
    public void setAddress(final String address){
    	this.address = address;
    }

    @Override
    public void setNumber(final String number){
    	this.phoneNumber = number;
    }
    
    @Override
    public void setCourses(final List<ICourse> corsi){
        this.corsi = corsi;
    }

}
