package view.panels.login;

import controller.panels.login.ILoginPanelController;

/**
 * Interface that defines the behavior of a {@link LoginPanel}.
 * @author Federico Giannoni
 *
 */
public interface ILoginPanel {

    /**
     * Attaches the specified observer to the {@link LoginPanel}.
     * @param controller the observer to be attached to the panel.
     */
    void attachObserver(final ILoginPanelController controller);

}
