package view.panels.home;

import view.panels.IGenericTable;
import controller.panels.home.IHomePanelController;

/**
 * Interface that defines the behavior of a {@link HomePanel}.
 * @author Federico Giannoni
 *
 */
public interface IHomePanel extends IGenericTable{

    /**
     * Attaches the specified observer to the {@link HomePanel}.
     * @param observer the observer to be attached to the panel.
     */
    void attachObserver(final IHomePanelController observer);

}
