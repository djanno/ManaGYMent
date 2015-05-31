package view;

import javax.swing.JPanel;

import view.PrimaryFrame.DialogWindow;
import controller.IPrimaryFrameController;

/**
 * Interface that defines the behavior of the primary frame of the application.
 * 
 * @author Federico Giannoni
 *
 */
public interface IPrimaryFrame {

    /**
     * Returns the panel that is currently being displayed in the frame.
     * 
     * @return the panel that is currently being displayed in the frame.
     */
    JPanel getCurrentPanel();

    /**
     * Returns the child component of the primary frame.
     * 
     * @return the child component of the primary frame, or null, if the frame
     *         does not have a child component.
     */
    DialogWindow getChild();

    /**
     * Returns the controller for the primary frame.
     * 
     * @return the controller for the primary frame.
     */
    IPrimaryFrameController getPrimaryController();

    /**
     * Draws the header panel in the primary frame, above the central panel.
     * This panel has a purely graphical purpose.
     */
    void drawHeaderPanel();

    /**
     * Empties the primary frame and re-paints it with the new panel specified
     * in input (and the header panel above that).
     * 
     * @param panel
     *            the new panel to be drawn in the primary frame.
     */
    void setCurrentPanel(final JPanel panel);

    /**
     * Enables/disables the navigation menu of the application.
     * 
     * @param enabled
     *            a boolean that determines whether the navigation menu has to
     *            be enabled (true), or disabled (false).
     */
    void setNavigationMenuEnabled(final boolean enabled);

    /**
     * Uses the primary frame as parent component for a JOptionPane used to
     * display all the exceptions thrown by the application.
     * 
     * @param error
     *            the exception message that has to be displayed in the
     *            JOptionPane.
     */
    void displayError(final String error);

    /**
     * Attaches the specified observer to the {@link PrimaryFrame}. Such
     * observer will serve as the controller for the frame.
     * 
     * @param observer
     *            the observer that has to be attached to the frame.
     */
    void attachObserver(final IPrimaryFrameController observer);

}
