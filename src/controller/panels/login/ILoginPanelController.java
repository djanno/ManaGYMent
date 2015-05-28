package controller.panels.login;

public interface ILoginPanelController {

    void cmdLogin(final String user, final char[] psw);

    void buildSignupPanel();
}
