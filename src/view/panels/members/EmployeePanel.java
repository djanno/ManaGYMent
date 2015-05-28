package view.panels.members;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.gym.members.IEmployee;
import view.panels.members.FieldsCommon.EnumFieldsCommon;
import controller.panels.members.IEmployeeAddController;

/**
 * EmployeePanel
 * 
 * @author Davide Borficchia
 *
 */

public class EmployeePanel extends JPanel implements ActionListener, IEmployeePanel {

    /**
	 * 
	 */
    public static final long serialVersionUID = 1L;
    private IEmployeeAddController observer;
    final private JLabel salaryLbl;
    final private JTextField salaryField;
    final private JButton btnSave;
    final private CommonPanel commonPanel;
    final private JPanel employeePanel;

    /**
     * Constructor
     */
    public EmployeePanel() {
        this.salaryLbl = new JLabel("Salario");
        this.salaryField = new JTextField(5);
        this.btnSave = new JButton("Salva");
        this.commonPanel = new CommonPanel();
        this.employeePanel = new JPanel();
        this.employeePanel.setLayout(new GridBagLayout());

        this.setLayout(new GridBagLayout());
        this.setLayout(new BorderLayout());
        this.add(commonPanel, BorderLayout.NORTH);

        this.employeePanel.add(this.salaryLbl, new GridBagConstraints(0, 0, 1, 1, 0.22, 0.25, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(0, 4, 0, 0), 0, 0));
        this.employeePanel.add(this.salaryField, new GridBagConstraints(1, 0, 2, 1, 0.78, 0.25, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, new Insets(0, 48, 0, 0), 0, 0));
        this.employeePanel.add(this.btnSave, new GridBagConstraints(1, 1, 1, 1, 0.30, 0.25, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 48, 0, 0), 0, 0));

        this.add(employeePanel, BorderLayout.CENTER);

        this.btnSave.addActionListener(this);
    }

    @Override
    public void showData(final IEmployee employee) {
        this.commonPanel.setMap(EnumFieldsCommon.NOME, employee.getName());
        this.commonPanel.setMap(EnumFieldsCommon.COGNOME, employee.getSurname());
        this.commonPanel.setMap(EnumFieldsCommon.CODICE_FISCALE, employee.getFiscalCode());
        this.commonPanel.setMap(EnumFieldsCommon.INDIRIZZO, employee.getAddress());
        this.commonPanel.setMap(EnumFieldsCommon.TELEFONO, employee.getNumber());
        this.commonPanel.setMap(EnumFieldsCommon.EMAIL, employee.getEmail());
        this.salaryField.setText(String.valueOf(employee.getSalary()));
    }

    @Override
    public CommonPanel getCommonPanel() {
        return this.commonPanel;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        final Object source = arg0.getSource();
        if (source.equals(this.btnSave)) {
            this.observer.cmdSave(commonPanel.getMapToPass(), salaryField.getText());
        }
    }

    @Override
    public void attachObserver(IEmployeeAddController observer) {
        this.observer = observer;
    }
}