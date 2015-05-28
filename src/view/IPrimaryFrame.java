package view;

import javax.swing.JPanel;

import view.PrimaryFrame.DialogWindow;
import controller.IPrimaryFrameController;

public interface IPrimaryFrame {

    JPanel getCurrentPanel();

    String getActiveUser();

    DialogWindow getChild();

    IPrimaryFrameController getPrimaryController();

    void drawHeaderPanel();

    void setCurrentPanel(final JPanel panel);

    void setActiveUser(final String username);

    void setNavigationMenuEnabled(final boolean enabled);

    void displayError(final String error);

    void attachObserver(final IPrimaryFrameController observer);

}
