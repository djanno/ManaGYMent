package view.panels.home;

import view.panels.IGenericTable;
import controller.panels.home.IHomePanelController;

public interface IHomePanel extends IGenericTable{

    void attachObserver(final IHomePanelController observer);

    // void loadTableData(final IGymCalendar calendar);
}
