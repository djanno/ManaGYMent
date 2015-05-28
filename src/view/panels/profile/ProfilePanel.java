package view.panels.profile;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdesktop.xswingx.PromptSupport;

import view.panels.Background;
import controller.panels.profile.IProfilePanelController;

/**
 * ProfilePanel
 * 
 * @author Davide Borficchia
 *
 */

public class ProfilePanel extends Background implements IProfilePanel, ActionListener{

	private static final long serialVersionUID = 5362097461793303394L;
	private static final Font HEADER_FONT = new Font("Arial", Font.BOLD + Font.PLAIN, 20);
	private static final Font FONT = new Font("Georgia", Font.PLAIN, 15);
	private static final String HEADER_TEXT = "Inserire le credenziali da modificare: ";
	
	private IProfilePanelController observer;
	
	private final JLabel header;
	private final JLabel userLoggedLbl;

	private final JLabel pswLbl;
	private final JLabel confirmPswLbl;
	private final JLabel emailLbl;
	private final JLabel confirmEmailLbl;
	
	private final JPasswordField pswField;
	private final JPasswordField confirmPswField;
	private final JTextField emailField;
	private final JTextField confirmEmailField;	
	
	private final JButton changePswButton;
	private final JButton changeEmailButton;
	
	private final JPanel northPanel;
	private final JPanel centerPanel;
	private final JPanel southPanel;
	
	/**
	 * @param path
	 * 		the path of background image
	 * @param userLogged
	 * 		the name of user
	 */
	public ProfilePanel(final String path, final String userLogged) {
		super(path);
		this.northPanel = new JPanel();
		this.centerPanel = new JPanel();
		this.southPanel = new JPanel();
		
		this.northPanel.setPreferredSize(new Dimension(400, 100));
		this.centerPanel.setLayout(new GridBagLayout());
		this.southPanel.setPreferredSize(new Dimension(400, 100));
		
		this.setLayout(new BorderLayout());
		
		this.header = new JLabel(HEADER_TEXT);
		
		this.userLoggedLbl = new JLabel ("Utente loggato: " + userLogged);
		this.pswLbl = new JLabel("password: ");
		this.confirmPswLbl = new JLabel("conferma password: ");
		this.emailLbl = new JLabel("email: ");
		this.confirmEmailLbl = new JLabel("conferma email: ");
		
		this.header.setFont(HEADER_FONT);
		this.userLoggedLbl.setFont(FONT);
		this.pswLbl.setFont(FONT);
		this.confirmPswLbl.setFont(FONT);
		this.emailLbl.setFont(FONT);
		this.confirmEmailLbl.setFont(FONT);
		
		this.pswField = new JPasswordField(20);
		this.confirmPswField = new JPasswordField(20);
		this.emailField = new JTextField(20);
		this.confirmEmailField = new JTextField(20);
		
		this.changePswButton = new JButton ("Cambia password");
		this.changeEmailButton = new JButton("Cambia email");
		
		this.changeEmailButton.addActionListener(this);
		this.changePswButton.addActionListener(this);
		
		this.setPreferredSize(new Dimension(400, 300));
	
        PromptSupport.setPrompt("Solo @gmail o @yahoo", emailField);
        PromptSupport.setPrompt("Solo @gmail o @yahoo", confirmEmailField);

		final Insets emptyInsets = new Insets(0, 0, 0, 0);
		
		this.centerPanel.add(this.header, new GridBagConstraints(0, 0, 4, 1, 0.5, 0.2, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.centerPanel.add(this.userLoggedLbl, new GridBagConstraints(0, 0, 4, 1, 0.35, 0.2, GridBagConstraints.SOUTH, GridBagConstraints.NONE, emptyInsets, 0, 0));
		this.centerPanel.add(this.pswLbl, new GridBagConstraints(0, 1, 1, 1, 0.17, 0.2, GridBagConstraints.LINE_END, GridBagConstraints.NONE, emptyInsets, 0, 0));
		this.centerPanel.add(this.pswField, new GridBagConstraints(1, 1, 1, 1, 0.33, 0.2, GridBagConstraints.LINE_START, GridBagConstraints.NONE, emptyInsets, 0, 0));
		this.centerPanel.add(this.confirmPswLbl, new GridBagConstraints(0, 2, 1, 1, 0.17, 0.2, GridBagConstraints.LINE_END, GridBagConstraints.NONE, emptyInsets, 0, 0));
		this.centerPanel.add(this.confirmPswField, new GridBagConstraints(1, 2, 1, 1, 0.33, 0.2, GridBagConstraints.LINE_START, GridBagConstraints.NONE, emptyInsets, 0, 0));
		this.centerPanel.add(this.changePswButton, new GridBagConstraints(1, 3, 1, 1, 0.33, 0.2, GridBagConstraints.LINE_START, GridBagConstraints.NONE, emptyInsets, 0, 0));
		this.centerPanel.add(this.emailLbl, new GridBagConstraints(2, 1, 1, 1, 0.17, 0.2, GridBagConstraints.LINE_END, GridBagConstraints.NONE, emptyInsets, 0, 0));
		this.centerPanel.add(this.emailField, new GridBagConstraints(3, 1, 1, 1, 0.33, 0.2, GridBagConstraints.LINE_START, GridBagConstraints.NONE, emptyInsets, 0, 0));
		this.centerPanel.add(this.confirmEmailLbl, new GridBagConstraints(2, 2, 1, 1, 0.17, 0.2, GridBagConstraints.LINE_END, GridBagConstraints.NONE, emptyInsets, 0, 0));
		this.centerPanel.add(this.confirmEmailField, new GridBagConstraints(3, 2, 1, 1, 0.33, 0.2, GridBagConstraints.LINE_START, GridBagConstraints.NONE, emptyInsets, 0, 0));	
		this.centerPanel.add(this.changeEmailButton, new GridBagConstraints(3, 3, 1, 1, 0.33, 0.2, GridBagConstraints.LINE_START, GridBagConstraints.NONE, emptyInsets, 0, 0));
	
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public void attachObserver(final IProfilePanelController observer) {
		this.observer = observer;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object source = e.getSource();
		if(source == this.changePswButton) {
			this.observer.cmdEditPsw(this.pswField.getPassword(), this.confirmPswField.getPassword());			
		}
		if(source == this.changeEmailButton){
			this.observer.cmdEditEmail(this.emailField.getText(), this.confirmEmailField.getText());
		}
	}
	
}
