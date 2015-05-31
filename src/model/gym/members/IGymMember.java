package model.gym.members;

import model.gym.IGym;

/**
 * Interface that defines the behavior of a {@link AbstractGymMember}.
 * @author Federico Giannoni
 * @author Simone Letizi
 *
 */
public interface IGymMember {

    /**
     * Returns the member's name.
     * @return the member's name.
     */
    String getName();

    /**
     * Returns the member's surname.
     * @return the member's surname.
     */
    String getSurname();

    /**
     * Returns the member's fiscal code.
     * @return the member's fiscal code.
     */
    String getFiscalCode();

    /**
     * Returns the member's address.
     * @return the member's address.
     */
    String getAddress();

    /**
     * Returns the member's phone number.
     * @return the member's phone number.
     */
    String getNumber();

    /**
     * Returns the member's email.
     * @return the member's email.
     */
    String getEmail();

    /**
     * Returns the gym of which the member is part of.
     * @return the gym of which the member is part of.
     */
    IGym getGym();

    /**
     * Sets the member's name.
     * @param name the new name.
     */
    void setName(String name);

    /**
     * Sets the member's surname.
     * @param surname the new surname.
     */
    void setSurname(String surname);

    /**
     * Sets the member's fiscal code.
     * @param fiscalCode the new fiscal code.
     */
    void setFiscalCode(String fiscalCode);

    /**
     * Sets the member's email.
     * @param email the new email.
     */
    void setEmail(final String email);

    /**
     * Sets the member's address.
     * @param address the new address.
     */
    void setAddress(final String address);

    /**
     * Sets the member's phone number.
     * @param number the new phone number.
     */
    void setNumber(final String number);

    /**
     * Returns a string representation of the object.
     * @return a string representation of the object.
     */
    String alternativeToString();

    /**
     * Returns a data vector containing all of the relevant data of the {@link AbstractGymMember}. Its implementation
     * depends on what kind of member the gym member is (subsciber or employee).
     * @return a data vector containing all of the relevant data of the member.
     */
    Object[] createRow();
}
