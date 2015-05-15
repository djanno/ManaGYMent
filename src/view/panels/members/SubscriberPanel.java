package view.panels.members;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.toedter.calendar.JDateChooser;
import model.gym.ICourse;
import model.gym.members.ISubscriber;
import view.panels.UtilitiesPanels;
import view.panels.members.FieldsCommon.EnumFieldsCommon;
import controller.panels.members.ISubscriberAddController;

public class SubscriberPanel extends JPanel implements ActionListener, ISubscriberPanel{
	
	public static final long serialVersionUID = 1L;
	
	private DefaultListModel<String> listCourses;
	private ISubscriberAddController observer;
	
	private final JLabel dataILbl; 
	private final JDateChooser calSubscription;
	private final JLabel dataSLbl; 
	private final JDateChooser calExpiration;
	private final JLabel coursesLbl; 
	private final JComboBox<String> coursesCmb; 
	private final JButton btnAdd; 
	private final JLabel lblRegistered; 
	private JList<String> coursesList ;
	private JScrollPane Registeredscr;
	private final JButton btnRemove;
	private final JButton btnSave; 
	private final CommonPanel commonPanel;
	private final JPanel subPanel; 

	public SubscriberPanel() {
		super();		
		this.listCourses = new DefaultListModel<String>();
		this.dataILbl = new JLabel("Data iscrizione");
		this.calSubscription = new JDateChooser();
		this.dataSLbl = new JLabel("Data scadenza");
		this.calExpiration = new JDateChooser();
		this.coursesLbl = new JLabel("Corsi");
		this.coursesCmb = new JComboBox<String>();
		this.btnAdd = new JButton("Aggiungi");
		this.lblRegistered = new JLabel("Iscritto a");
		this.btnRemove = new JButton("Rimuovi");
		this.btnSave = new JButton("Salva");
		this.coursesList = new JList<String>(this.listCourses);
		this.Registeredscr = new JScrollPane(coursesList) ;
		this.Registeredscr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.Registeredscr.setPreferredSize(new Dimension (100, 69));		
		this.commonPanel = new CommonPanel();
		this.subPanel = new JPanel();
		
		this.subPanel.setLayout(new GridBagLayout());
		this.setLayout(new BorderLayout());
		this.add(commonPanel, BorderLayout.NORTH);

		this.subPanel.add(this.dataILbl, new GridBagConstraints(0, 0, 1, 1, 0.35, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 4, 0, 0), 0, 0));
		this.subPanel.add(this.calSubscription, new GridBagConstraints(1, 0, 2, 1, 0.30, 0.25, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 0), 0, 0));
		this.subPanel.add(this.dataSLbl, new GridBagConstraints(0, 1, 1, 1, 0.35, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 4, 0, 0), 0, 0));
		this.subPanel.add(this.calExpiration, new GridBagConstraints(1, 1, 2, 1, 0.30, 0.25, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 0), 0, 0));
		this.subPanel.add(this.coursesLbl, new GridBagConstraints(0, 2, 1, 1, 0.35, 0.25, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 4, 0, 0), 0, 0));
		this.subPanel.add(this.coursesCmb, new GridBagConstraints(1, 2, 1, 1, 0.30, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 0), 0, 0));
		this.subPanel.add(this.btnAdd, new GridBagConstraints(2, 2, 1, 1, 0.30, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 0), 0, 0));
		this.subPanel.add(this.lblRegistered, new GridBagConstraints(0, 3, 1, 1, 0.35, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 4, 0, 0), 0, 0));
		this.subPanel.add(this.Registeredscr, new GridBagConstraints(1, 3, 1, 1, 0.30, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 0), 0, 0));
		this.subPanel.add(this.btnRemove, new GridBagConstraints(2, 3, 1, 1, 0.30, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 0), 0, 0));
		this.subPanel.add(this.btnSave, new GridBagConstraints(1, 4, 1, 1, 0.30, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 0), 0, 0));
		
		this.add(subPanel, BorderLayout.CENTER);
		
		this.btnAdd.addActionListener(this);
		this.btnRemove.addActionListener(this);
		this.btnSave.addActionListener(this);
	}
	
	@Override
	public CommonPanel getCommonPanel(){
		return this.commonPanel;
	}
	
	public void showData(ISubscriber subscriber){
		this.commonPanel.setMap(EnumFieldsCommon.NOME, subscriber.getName());
		this.commonPanel.setMap(EnumFieldsCommon.COGNOME, subscriber.getSurname());
		this.commonPanel.setMap(EnumFieldsCommon.CODICE_FISCALE, subscriber.getFiscalCode());
		this.commonPanel.setMap(EnumFieldsCommon.INDIRIZZO, subscriber.getAddress());
		this.commonPanel.setMap(EnumFieldsCommon.TELEFONO, subscriber.getNumber());
		this.commonPanel.setMap(EnumFieldsCommon.EMAIL, subscriber.getEmail());
		this.calSubscription.setCalendar(subscriber.getSubscriptionDate());
		this.calExpiration.setCalendar(subscriber.getExpirationDate());
		for (final ICourse c : subscriber.getCourses()){
			this.listCourses.addElement(c.getCourseName());
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		final Object source = arg0.getSource();
		if (source.equals(this.btnAdd)){
			if (!listCourses.contains((String) this.coursesCmb.getSelectedItem())){
				this.listCourses.addElement((String) this.coursesCmb.getSelectedItem());
			}
		}else if(source.equals(this.btnRemove)) {
			if (coursesList.getSelectedIndex() >= 0){
				this.listCourses.removeElementAt(coursesList.getSelectedIndex());
			}
		}else if(source.equals(this.btnSave)){
			this.observer.cmdSave(commonPanel.getMapToPass(), calSubscription.getDate(), calExpiration.getDate(), listCourses);
		}
	}

	@Override
	public void attachObserver(ISubscriberAddController observer) {
		this.observer = observer;
	}
		
	public void showMessage(String m){
		JOptionPane.showMessageDialog(this, m, "avviso", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showException(String e){
		JOptionPane.showMessageDialog(this, e, "Errore", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void setComboBox(List<ICourse> list) {
		this.coursesCmb.setModel(new DefaultComboBoxModel<String>(UtilitiesPanels.createComboBoxValues(list)));		
	}
}
