package view.panels.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jdesktop.xswingx.PromptSupport;
import view.panels.Background;
import controller.panels.home.ISendEmailPanelController;

/**
 * SendEmailPanel
 * 
 * @author Davide Borficchia
 *
 */

public class SendEmailPanel extends Background implements ActionListener, ISendEmailPanel{

    /**
     * 
     */
    private static final long serialVersionUID = -8526955210614702783L;

    private final JButton btnSend;
    private final JCheckBox chkEmployee;
    private final JCheckBox chkSubscriber;
    private final JCheckBox chkExSubsriber;
    private final JTextArea txtMessage;
    private final JTextField txtObject;
    private final JScrollPane scroll;
    
    private ISendEmailPanelController observer;

    /**
     * 
     * Constructor
     * 
     * @param path
     * 		the path of background image
     */
    public SendEmailPanel(final String path){
    	super(path);
        this.chkEmployee = new JCheckBox();
        this.chkSubscriber = new JCheckBox();
        this.chkExSubsriber = new JCheckBox();
        this.btnSend = new JButton("Invia");
        
        this.txtMessage = new JTextArea();
        this.txtObject = new JTextField();
        this.scroll = new JScrollPane(txtMessage);
       
        this.setLayout(new BorderLayout());
         
        final JPanel pEast = new Background(this.getBackgroundPath());
        pEast.setLayout(new GridBagLayout());
        final Insets insets = new Insets(4, 10, 10, 10);
        final Insets insetsG = new Insets(4, 10, 4, 10);
        
        final JPanel pCenter = new Background(this.getBackgroundPath()); 
        pCenter.setLayout(new GridBagLayout());
        
        this.txtMessage.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));

        PromptSupport.setPrompt("Oggetto", txtObject);
        PromptSupport.setPrompt("Testo", txtMessage);

        this.add(pEast, BorderLayout.EAST);
        this.add(pCenter, BorderLayout.CENTER);
        
        final GridBagConstraints gbcTextFObject= new GridBagConstraints(0, 0, 1, 1, 1, 0.1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insetsG, 1, 1);
        pCenter.add(txtObject,gbcTextFObject);
        
        final GridBagConstraints gbcMessage= new GridBagConstraints(0, 1, 1, 1, 1, 0.9, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets, 1, 1);
        pCenter.add(scroll, gbcMessage);
          
        final JLabel lblSendTo=new JLabel("Invia a:");
        final GridBagConstraints gbcSendToLbl=new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets, 1, 1);
        pEast.add(lblSendTo,gbcSendToLbl);
        
        final JLabel lblEmployee = new JLabel("Impegati:");
        final GridBagConstraints gbcEmployeeLbl = new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets, 1, 1);
        pEast.add(lblEmployee,gbcEmployeeLbl);
        
        final JLabel lblCurrSubscriver = new JLabel("Iscritti correnti:");
        final GridBagConstraints gbcCurrSubscriverLbl = new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets, 1, 1);
        pEast.add(lblCurrSubscriver, gbcCurrSubscriverLbl);
        
        final JLabel lblPastSubscriver = new JLabel("Iscritti passati:");
        final GridBagConstraints gbcPastSubscriverLbl=new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets, 1, 1);
        pEast.add(lblPastSubscriver,gbcPastSubscriverLbl);
        
        final GridBagConstraints gbcEmployeeCheck = new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets, 1, 1);
        pEast.add(chkEmployee, gbcEmployeeCheck);

        final GridBagConstraints gbcCurrSubscriverCheck = new GridBagConstraints(1, 2, 1, 1, 0.0 , 0.0 , GridBagConstraints.CENTER, GridBagConstraints.NONE, insets, 1, 1);
        pEast.add(chkSubscriber, gbcCurrSubscriverCheck);

        final GridBagConstraints gbcPastSubscriverCheck = new GridBagConstraints(1, 3, 1, 1, 0.0 , 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets, 1, 1);
        pEast.add(chkExSubsriber, gbcPastSubscriverCheck);

        final GridBagConstraints gbcSendButton = new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets, 1, 1);
        pEast.add(btnSend, gbcSendButton);
        
        this.btnSend.addActionListener(this);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		final Object source = e.getSource();
		if (source.equals(this.btnSend)){
			final char[] pass = insertPassword();
			if (pass != null){
				this.observer.cmdSend(txtObject.getText(), txtMessage.getText(), chkEmployee.isSelected(), chkSubscriber.isSelected(), chkExSubsriber.isSelected(), pass);
			}
		}
	}
	
	@Override
	public void attachObserver(final ISendEmailPanelController observer) {
		this.observer = observer;		
	}
	
	@Override
	public void showMessage(final String message){
		JOptionPane.showMessageDialog(this, message, "Informazione", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * 
	 * Open an OptionPane to insert the email's password
	 * 
	 * @return password insert in the OptionPane, if click on "cancel" return null
	 */
	private char[] insertPassword(){
		final JPanel panel = new JPanel();
		final JLabel label = new JLabel("Inserisci password:");
		final JPasswordField pass = new JPasswordField(20);
		panel.add(label);
		panel.add(pass);
		
		final String[] options = new String[] {"OK", "Cancel"};
		final int option = JOptionPane.showOptionDialog(null, panel, "Inserisci password",
		                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		                         null, options, options[1]);
		if(option == 0)
		{
		   return  pass.getPassword(); 
		}
		return null;
	}
}