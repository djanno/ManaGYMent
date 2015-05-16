package view.panels.gym;

import controller.panels.gym.IGymPanelController;

public interface IGymPanel {
	
	void setHeader(final String header);

	void attachObserver(final IGymPanelController observer);

	IncomePanel getIncomePanel();
	
}
