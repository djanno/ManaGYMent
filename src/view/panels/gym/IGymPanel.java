package view.panels.gym;

import view.panels.IGenericTable;
import controller.panels.gym.IGymPanelController;

public interface IGymPanel extends IGenericTable{

    void setHeader(final String header);

    void attachObserver(final IGymPanelController observer);

    GymPanel.IncomePanel getIncomePanel();

}
