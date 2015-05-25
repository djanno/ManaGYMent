package view.panels.login;

import controller.panels.login.ILoginPanelController;

public interface ILoginPanel {

	void attachObserver(final ILoginPanelController controller);
	
}
