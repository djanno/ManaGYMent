package view.panels.signup;

import controller.panels.signup.ISignupPanelController;

public interface ISignupPanel {
    void attachObserver(final ISignupPanelController observer);
}
