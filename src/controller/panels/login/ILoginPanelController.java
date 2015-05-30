package controller.panels.login;

/**
 * Defines the {@link LoginPanelController}.
 * 
 * @author Federico Giannoni
 *
 */
public interface ILoginPanelController {

    /**
     * Manages the login procedure.
     * 
     * @param user
     *            the username provided in input.
     * @param psw
     *            the password provided in input.
     */
    void cmdLogin(final String user, final char[] psw);

    /**
     * Builds the signup page and its respective controller.
     */
    void buildSignupPanel();
}
