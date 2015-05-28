package view.panels.signup;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.jdesktop.xswingx.PromptSupport;

import view.panels.Background;
import controller.panels.signup.ISignupPanelController;

public class SignupPanel extends Background implements ISignupPanel, ActionListener, KeyListener {

    private static final long serialVersionUID = 5362097461793303394L;
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD + Font.PLAIN, 20);
    private static final Font FONT = new Font("Georgia", Font.PLAIN, 15);
    private static final String HEADER_TEXT = "Inserire le credenziali richieste: ";

    private ISignupPanelController observer;

    private final Map<ISignupField, JTextComponent> map = new HashMap<>();
    private final ISignupStrategy strategy = new SignupStrategy();

    private final JLabel header;
    private final JButton signupButton;
    private final JButton cancelButton;

    public SignupPanel(final String path) {
        super(path);

        this.setLayout(new GridBagLayout());

        this.header = new JLabel(HEADER_TEXT);
        this.header.setFont(HEADER_FONT);

        final Insets emptyInsets = new Insets(0, 0, 0, 0);

        this.add(this.header, new GridBagConstraints(0, 0, 2, 1, 1, 0.2, GridBagConstraints.CENTER, GridBagConstraints.NONE, emptyInsets, 0, 0));

        final GridBagConstraints cnst = new GridBagConstraints(0, 1, 1, 1, 0.5, 0.028, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                emptyInsets, 0, 0);

        this.strategy.fields().forEach(value -> {
            final JLabel label = new JLabel(value.getName());
            label.setFont(FONT);
            cnst.gridx = 0;
            this.add(label, cnst);

            if (value.isHidden()) {
                this.map.put(value, new JPasswordField(value.getLength()));
            } else {
                this.map.put(value, new JTextField(value.getLength()));
            }

            PromptSupport.setPrompt(value.getToolTipText(), this.map.get(value));
            this.map.get(value).addKeyListener(this);

            cnst.gridx++;
            this.add(this.map.get(value), cnst);
            cnst.gridy++;
        });

        this.signupButton = new JButton("Iscriviti");
        this.cancelButton = new JButton("Indietro");

        this.signupButton.addActionListener(this);
        this.cancelButton.addActionListener(this);

        cnst.gridy++;
        this.add(this.signupButton, new GridBagConstraints(0, cnst.gridy, 1, 1, 0.5, 0.3, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                emptyInsets, 0, 10));
        this.add(this.cancelButton, new GridBagConstraints(1, cnst.gridy, 1, 1, 0.5, 0.3, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                emptyInsets, 0, 10));
    }

    @Override
    public void attachObserver(final ISignupPanelController observer) {
        this.observer = observer;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final Object source = e.getSource();
        if (source == this.signupButton) {
            final Map<ISignupField, String> mapToPass = this.strategy.fields().stream()
                    .collect(Collectors.toMap(field -> field, field -> this.map.get(field).getText()));
            this.observer.cmdSignup(mapToPass);
        }
        if (source == this.cancelButton) {
            this.observer.cmdCancel();
        }
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.signupButton.doClick();
        }
    }

    @Override
    public void keyReleased(final KeyEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyTyped(final KeyEvent arg0) {
        // TODO Auto-generated method stub
    }

}