package controller.panels.members.tables;

import java.util.List;

import model.gym.members.IGymMember;

/**
 * Defines the {@link AbstractTableMemberController}.
 * 
 * @author simone
 *
 */
public interface IAbstractTableMemberController {

    /**
     * reset and create from scratch members' table by the program in model
     * depending on the list parameter
     * 
     * @param list
     *            the list of member to show in JTable
     */
    void createTable(final List<? extends IGymMember> list);

    /**
     * Adds member into list present in model
     */
    void addMemberCmd();

    /**
     * Edit member already present in list in the index position
     * 
     * @param index
     */
    void editMemberCmd(final int index);

    /**
     * delete member from the list
     * 
     * @param index
     */
    void deleteMemberCmd(final int index);

    /**
     * set payed the credit/debt towards member
     * 
     * @param index
     *            the index of member to be payed
     */
    void handlePaymentCmd(final int index);

}
