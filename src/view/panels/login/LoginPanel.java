package view.panels.login;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import view.panels.Background;
import controller.panels.login.ILoginPanelController;

public class LoginPanel extends Background implements ILoginPanel, ActionListener, KeyListener {

    private static final long serialVersionUID = 3301612740714431369L;

    private static final String WELCOME_MSG = "WELCOME TO ManaGYMent";
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD + Font.PLAIN, 20);
    private static final Font BODY_FONT = new Font("Georgia", Font.PLAIN, 15);

    private ILoginPanelController observer;

    private final JButton loginButton;
    private final JButton signupButton;
    private final JLabel header;
    private final JLabel idLabel;
    private final JLabel pswLabel;
    private final JTextField idField;
    private final JPasswordField pswField;

    public LoginPanel(final String path) {
        super(path);
        this.setLayout(new GridBagLayout());

        this.loginButton = new JButton("Accedi");
        this.signupButton = new JButton("Registrati");
        this.header = new JLabel(WELCOME_MSG);
        this.idLabel = new JLabel("username: ");
        this.pswLabel = new JLabel("password: ");

        this.header.setFont(HEADER_FONT);
        this.pswLabel.setFont(BODY_FONT);
        this.idLabel.setFont(BODY_FONT);

        this.idField = new JTextField(20);
        this.pswField = new JPasswordField(20);

        this.loginButton.addActionListener(this);
        this.signupButton.addActionListener(this);
        this.idField.addKeyListener(this);
        this.pswField.addKeyListener(this);

        final Insets emptyInsets = new Insets(0, 0, 0, 0);

        this.add(this.header, new GridBagConstraints(0, 0, 2, 1, 0, 0.35, GridBagConstraints.CENTER, GridBagConstraints.NONE, emptyInsets, 0, 0));
        this.add(this.idLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0.25, GridBagConstraints.LINE_END, GridBagConstraints.NONE, emptyInsets, 0, 0));
        this.add(this.idField, new GridBagConstraints(1, 1, 1, 1, 0, 0.25, GridBagConstraints.LINE_START, GridBagConstraints.NONE, emptyInsets, 0, 0));
        this.add(this.pswLabel,
                new GridBagConstraints(0, 2, 1, 1, 0, 0.25, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, emptyInsets, 0, 0));
        this.add(this.pswField, new GridBagConstraints(1, 2, 1, 1, 0, 0.25, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
                emptyInsets, 0, 0));
        this.add(this.loginButton, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.LAST_LINE_END, GridBagConstraints.NONE, emptyInsets,
                0, 10));
        this.add(this.signupButton, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.LAST_LINE_END, GridBagConstraints.NONE, emptyInsets,
                0, 10));
        this.add(new JLabel(), new GridBagConstraints(0, 3, 2, 1, 0, 0.3, GridBagConstraints.CENTER, GridBagConstraints.NONE, emptyInsets, 0, 0));

    }

    @Override
    public void attachObserver(final ILoginPanelController observer) {
        this.observer = observer;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final Object source = e.getSource();
        if (source == this.loginButton) {
            this.observer.cmdLogin(this.idField.getText(), this.pswField.getPassword());
        }
        if (source == this.signupButton) {
            this.observer.buildSignupPanel();
        }

    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.loginButton.doClick();
        }

    }

    @Override
    public void keyReleased(final KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(final KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
