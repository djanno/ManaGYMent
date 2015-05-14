package model.gym.members;

import java.util.List;

import model.gym.ICourse;
import model.gym.IGym;

public interface IGymMember {

    Object[] createRow();
    
    String getName();

    String getSurname();

    String getFiscalCode();

    String getAddress();

    String getNumber();

    String getEmail();
    
    List<ICourse> getCourses();

    IGym getGym();
    
    void setEmail(final String email);

    void setAddress(final String address);

    void setNumber(final String number);
    
    void setCourses(final List<ICourse> corsi);
    
    String alternativeToString();

}
