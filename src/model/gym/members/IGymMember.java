package model.gym.members;

import model.gym.IGym;

public interface IGymMember {
  
    String getName();

    String getSurname();

    String getFiscalCode();

    String getAddress();

    String getNumber();

    String getEmail();

    IGym getGym();
    
    void setName(String name);
    
    void setSurname(String surname);
    
    void setFiscalCode(String fiscalCode);
    
    void setEmail(final String email);

    void setAddress(final String address);

    void setNumber(final String number);
    
    String alternativeToString();
    
    Object[] createRow();
}
