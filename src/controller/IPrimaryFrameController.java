package controller;

/**
 * Defines the {@link PrimaryFrameController}.
 * 
 * @author Federico Giannoni
 *
 */
public interface IPrimaryFrameController {

    /**
     * Returns the username of the user that is currently logged in.
     * 
     * @return the username of the user that is currently logged in.
     */
    String getActiveUser();

    /**
     * Saves the username of the user that is currently logged in.
     * 
     * @param username
     *            the username of the user that is currently logged in.
     */
    void setActiveUser(final String username);

    /**
     * Builds the login page and its respective controller.
     */
    void buildLoginPanel();

    /**
     * Builds the home page and its respective controller.
     */
    void buildHomePanel();

    /**
     * Builds the subscribers management page and its respective controller.
     */
    void buildSubPagePanel();

    /**
     * Builds the employees management page and its respective controller.
     */
    void buildEmployeePagePanel();

    /**
     * Builds the gym management page and its respective controller.
     */
    void buildGymPagePanel();

    /**
     * Builds the user's profile management page and its respective controller.
     */
    void buildProfilePagePanel();

    /**
     * Builds the email page and its respective controller.
     */
    void buildEmailPanel();

    /**
     * Manages the logout procedure.
     */
    void cmdLogout();

    /**
     * Loads the model's data from a .gym file (binary file).
     * 
     * @param path
     *            the path that leads to the binary file from which the data
     *            will be loaded. If null, the data will be loaded from the home
     *            directory (if a file .gym is present).
     */
    void cmdLoad(final String path);

    /**
     * Saves the model's data into a .gym file (binary file).
     * 
     * @param path
     *            the path that leads to the binary file in which the data will
     *            be stored. If null, the data will be stored in a .gym file in
     *            the home directory (if such file is not present in the home
     *            directory, it will be generated automatically).
     */
    void cmdSave(final String path);

    /**
     * Manages the closing procedure of the application.
     */
    void cmdQuit();

    /**
     * Refresh the model's data that is influenced by the passage of time.
     */
    void cmdRefreshData();

}
