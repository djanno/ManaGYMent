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
import javax.swing.JTextField;

import model.gym.ICourse;
import model.gym.members.IEmployee;
import view.panels.UtilitiesPanels;
import view.panels.members.FieldsCommon.EnumFieldsCommon;
import controller.panels.members.IEmployeeAddController;

public class EmployeePanel extends JPanel implements ActionListener, IEmployeePanel{
		
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	private DefaultListModel<String> listCourses;
	private IEmployeeAddController observer;
	
	final private JLabel salaryLbl;
	final private JTextField salaryField;
	
	final private JLabel coursesLbl; 
	public JComboBox<String> coursesCmb; 
	final private JButton btnAdd; 
	
	final private JLabel lblInsegna; 
	private JList<String> jCourses;
	private JScrollPane scrInsegna;
	final private JButton btnRemove; 
	
	final private JButton btnSave; 
	
	final private CommonPanel commonPanel;
	final private JPanel employeePanel; 
	
	public EmployeePanel (){ //final IModel model, final String userLogged
//		this.model = model;
//		this.userLogged = userLogged;
//		this.observer = new EmployeeEditController(this, this.model, this.userLogged, 1); //DA RIMUOVERE
		
		this.salaryLbl = new JLabel("Salario");
		this.salaryField = new JTextField(5);
		this.coursesLbl = new JLabel("Corsi");
		this.coursesCmb = new JComboBox<String>();
		this.btnAdd = new JButton("Aggiungi");
		this.lblInsegna = new JLabel("Insegna");
		this.btnRemove = new JButton("Rimuovi");
		this.btnSave = new JButton("Salva");
		this.commonPanel = new CommonPanel();
		this.employeePanel = new JPanel();
		this.employeePanel.setLayout(new GridBagLayout());		
		
		this.setLayout(new GridBagLayout());
		this.setLayout(new BorderLayout());
		this.add(commonPanel, BorderLayout.NORTH);
		
		this.listCourses = new DefaultListModel<String>();
		this.jCourses = new JList<String>(this.listCourses);
		this.scrInsegna = new JScrollPane(jCourses);
		this.scrInsegna.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.scrInsegna.setPreferredSize(new Dimension (100, 69));
		
		this.employeePanel.add(this.salaryLbl, new GridBagConstraints(0, 0, 1, 1, 0.35, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 4, 0, 0), 0, 0));
		this.employeePanel.add(this.salaryField, new GridBagConstraints(1, 0, 2, 1, 0.30, 0.25, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(0,	48, 0, 0), 0, 0));
		this.employeePanel.add(this.coursesLbl, new GridBagConstraints(0, 1, 1, 1, 0.35, 0.25, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 4, 0, 0), 0, 0));
		this.employeePanel.add(this.coursesCmb, new GridBagConstraints(1, 1, 1, 1, 0.30, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 48, 0, 0), 0, 0));
		this.employeePanel.add(this.btnAdd, new GridBagConstraints(2, 1, 1, 1, 0.30, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 0), 0, 0));
		this.employeePanel.add(this.lblInsegna, new GridBagConstraints(0, 2, 1, 1, 0.35, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 4, 0, 0), 0, 0));
		this.employeePanel.add(this.scrInsegna, new GridBagConstraints(1, 2, 1, 1, 0.30, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 48, 0, 0), 0, 0));
		this.employeePanel.add(this.btnRemove, new GridBagConstraints(2, 2, 1, 1, 0.30, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 0), 0, 0));
		this.employeePanel.add(this.btnSave, new GridBagConstraints(1, 3, 1, 1, 0.30, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 48, 0, 0), 0, 0));
		
		this.add(employeePanel, BorderLayout.CENTER);
		
//		this.observer.loadData(); //DA RIMUOVERE
		
		this.btnAdd.addActionListener(this);
		this.btnRemove.addActionListener(this);
		this.btnSave.addActionListener(this);
	}
	
	/*public EmployeePanel (final IModel model, final String userLogged, final int index){ 
		this(model, userLogged);
		this.observer.loadData(index);
	}*/
	
	@Override
	public void setComboBox(List<ICourse> list) {
		this.coursesCmb.setModel(new DefaultComboBoxModel<String>(UtilitiesPanels.createComboBoxValues(list)));
	}
	
	public void showData(IEmployee employee, List<ICourse> gymCourses){
		this.commonPanel.setMap(EnumFieldsCommon.NOME, employee.getName());
		this.commonPanel.setMap(EnumFieldsCommon.COGNOME, employee.getSurname());
		this.commonPanel.setMap(EnumFieldsCommon.CODICE_FISCALE, employee.getFiscalCode());
		this.commonPanel.setMap(EnumFieldsCommon.INDIRIZZO, employee.getAddress());
		this.commonPanel.setMap(EnumFieldsCommon.TELEFONO, employee.getNumber());
		this.commonPanel.setMap(EnumFieldsCommon.EMAIL, employee.getEmail());
		this.salaryField.setText(String.valueOf(employee.getSalary()));
		for (final ICourse c : employee.getCourses()){
			this.listCourses.addElement(c.getCourseName());
		}
	}
	
	@Override
	public CommonPanel getCommonPanel(){
		return this.commonPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		final Object source = arg0.getSource();
		if (source.equals(this.btnAdd)){
			if (!listCourses.contains((String) this.coursesCmb.getSelectedItem())){
				this.listCourses.addElement((String) this.coursesCmb.getSelectedItem());
			}
		}else if(source.equals(this.btnRemove)) {
			if (this.jCourses.getSelectedIndex() >= 0){
				this.listCourses.removeElementAt(jCourses.getSelectedIndex());
			}
		}else if(source.equals(this.btnSave)){
			this.observer.cmdSave(commonPanel.getMapToPass(), salaryField.getText() , listCourses); 
		}
	}
	
	@Override
	public void attachObserver(IEmployeeAddController observer) {
		this.observer = observer;
	}
	
	public void showMessage(String m){
		JOptionPane.showMessageDialog(this, m, "avviso", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showException(String e){
		JOptionPane.showMessageDialog(this, e, "Errore", JOptionPane.ERROR_MESSAGE);
	}

}
