package view.panels.home;

import controller.panels.home.ISendEmailPanelController;

public interface ISendEmailPanel {

	public void attachObserver(final ISendEmailPanelController observer);

	void showMessage(final String s);
	
}
