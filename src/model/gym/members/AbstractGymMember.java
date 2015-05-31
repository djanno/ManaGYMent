package model.gym.members;

import java.io.Serializable;
import model.gym.IGym;

/**
 * A generic gym member.
 * @author Federico Giannoni
 * @author Simone Letizi
 *
 */
public abstract class AbstractGymMember implements IGymMember, Serializable {

    private static final long serialVersionUID = 5354356285190012285L;

    private String name;
    private String surname;
    private String fiscalCode;
    private String address;
    private String phoneNumber;
    private String email;
    private final IGym gym;

    /**
     * Constructs a new gym member with the data provided in input.
     * @param name the name of the new member.
     * @param surname the surname of the new member.
     * @param fiscalCode the fiscal code of the new member.
     * @param address the address of the new member.
     * @param phoneNumber the phone number of the new member.
     * @param email the email of the new member.
     * @param gym the gym of the new member.
     */
    public AbstractGymMember(final String name, final String surname, final String fiscalCode, final String address, final String phoneNumber,
            final String email, final IGym gym) {
        super();
        this.name = name;
        this.surname = surname;
        this.fiscalCode = fiscalCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gym = gym;
    }

    @Override
    public abstract Object[] createRow();

    @Override
    public String alternativeToString() {
        return this.getName() + " " + this.getSurname() + " " + this.getFiscalCode();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public String getFiscalCode() {
        return this.fiscalCode;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public String getNumber() {
        return this.phoneNumber;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public IGym getGym() {
        return this.gym;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public void setSurname(final String surname) {
        this.surname = surname;
    }

    @Override
    public void setFiscalCode(final String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    @Override
    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public void setAddress(final String address) {
        this.address = address;
    }

    @Override
    public void setNumber(final String number) {
        this.phoneNumber = number;
    }
}
