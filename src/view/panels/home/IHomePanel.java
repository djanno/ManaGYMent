package view.panels.home;

import controller.panels.home.IHomePanelController;

public interface IHomePanel {

    void attachObserver(final IHomePanelController observer);

    // void loadTableData(final IGymCalendar calendar);
}
