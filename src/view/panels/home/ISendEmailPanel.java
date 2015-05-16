package view.panels.home;

import controller.panels.home.ISendEmailPanelController;

public interface ISendEmailPanel {

	public void attachObserver(ISendEmailPanelController observer);

	void showMessage(String s);
	
}
