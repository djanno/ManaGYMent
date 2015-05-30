package view.panels.gym;

import view.panels.IGenericTable;
import controller.panels.gym.IGymPanelController;

/**
 * Interface that defines the behavior of the {@link GymPanel}.
 * @author Federico Giannoni
 *
 */
public interface IGymPanel extends IGenericTable{

    /**
     * Sets the specified string as the header title of the panel. In the application, the string is supposed
     * to be the name of the gym of the user whom is currently logged in.
     * @param header the title of the panel.
     */
    void setHeader(final String header);

    /**
     * Attaches the specified observer to the {@link GymPanel}.
     * @param observer the observer to be attached to the panel.
     */
    void attachObserver(final IGymPanelController observer);

    /**
     * Returns the {@link GymPanel.IncomePanel} visualized in the {@link GymPanel}.
     * @return the {@link GymPanel.IncomePanel} visualized in the {@link GymPanel}.
     */
    GymPanel.IncomePanel getIncomePanel();

}
