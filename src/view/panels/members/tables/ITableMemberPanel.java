package view.panels.members.tables;

import view.panels.IGenericTable;
import controller.panels.members.tables.IAbstractTableMemberController;

/**
 * Defines the {@link TableMemberPanel}
 * 
 * @author simone
 *
 */
public interface ITableMemberPanel extends IGenericTable{
    /**
     * Changes the current {@link IAbstractTableMemberController}.
     * 
     * @param observer
     *            the new {@link IAbstractTableMemberController}.
     */
    void attachViewObserver(final IAbstractTableMemberController observer);
}
