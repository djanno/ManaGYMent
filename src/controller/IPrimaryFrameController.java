package controller;

public interface IPrimaryFrameController {
	
	void buildLoginPanel();
	
	void buildHomePanel();
	
	void buildSubPagePanel();
	
	void buildEmployeePagePanel();
	
	void buildGymPagePanel();
	
	void buildProfilePagePanel();
	
	void buildEmailPanel();
	
	void cmdLogout();
	
	void cmdLoad(final String path);
	
	void cmdSave(final String path);
	
	void cmdQuit();

	void cmdRefreshData();

}
