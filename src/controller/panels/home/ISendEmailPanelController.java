package controller.panels.home;

public interface ISendEmailPanelController {
	void cmdSend(final String oggetto, final String corpo, final boolean subscriber, final boolean employee, 
			final boolean exSubscriber, char[] password);
}
