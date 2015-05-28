package view.panels.profile;

import controller.panels.profile.IProfilePanelController;

/**
 * Interface for {@link ProfilePanel}.
 * 
 * @author Davide Borficchia
 *
 */

public interface IProfilePanel {

    /**
     * Attach the observer to this panel.
     * 
     * @param observer
     *            the observer
     */
    void attachObserver(final IProfilePanelController observer);

}
