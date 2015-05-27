package model.gym.members;

import model.gym.IGym;

public interface IGymMember {

    Object[] createRow();
    
    String getName();

    String getSurname();

    String getFiscalCode();

    String getAddress();

    String getNumber();

    String getEmail();

    IGym getGym();
    
    void setName(final String name);
    
    void setSurname(final String surname);
    
    void setFiscalCode(final String fiscalCode);
    
    void setEmail(final String email);

    void setAddress(final String address);

    void setNumber(final String number);
    
    String alternativeToString();	
    
}
