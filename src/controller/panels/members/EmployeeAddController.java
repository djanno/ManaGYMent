package controller.panels.members;

import java.util.Map;

import model.gym.IGym;
import model.gym.members.Employee;
import model.gym.members.IEmployee;
import view.IPrimaryFrame;
import view.panels.members.EmployeePanel;
import view.panels.members.FieldsCommon.EnumFieldsCommon;
import view.panels.members.IEmployeePanel;
import view.panels.members.IFormField;
import controller.panels.members.tables.TableEmployeesController;

/**
 * The controller for {@link EmployeePanel}.
 * 
 * @author Davide Borficchia
 *
 */

public class EmployeeAddController extends BaseController implements IEmployeeAddController {

    private static final String WRONG_SALARY = "Il salario specificato non è un numero valido";

    protected final IEmployeePanel view;
    protected IPrimaryFrame frame;
    protected final IGym gym;
    protected final TableEmployeesController tableEmployeesController;

    /**
     * 
     * Constructor
     * 
     * @param frame
     *            the primary frame
     * @param employeeView
     *            the employee panel
     * @param gym
     *            the gym
     * @param tableEmployeesController
     *            the table employees controller
     */
    public EmployeeAddController(final IPrimaryFrame frame, final IEmployeePanel employeeView, final IGym gym,
            final TableEmployeesController tableEmployeesController) {
        super();
        this.frame = frame;
        this.view = employeeView;
        this.gym = gym;
        this.tableEmployeesController = tableEmployeesController;
        this.view.attachObserver(this);
    }

    @Override
    public void cmdSave(final Map<IFormField, String> mapToPass, final String salario) {
        try {
            final IEmployee employee = createEmployee(mapToPass, salario);
            this.gym.addEmployee(employee);
            tableEmployeesController.createTable(this.gym.getEmployees());
            this.frame.getChild().closeDialog();
        } catch (Exception e) {
            this.frame.displayError(e.getMessage());
        }
    }

    /**
     * 
     * Creates new employee from GUI
     * 
     * @param mapToPass
     *            the common fields of employee
     * @param salary
     *            the employee's salary
     * @return the new employee
     * @throws IllegalArgumentException
     */

    private Employee createEmployee(final Map<IFormField, String> mapToPass, final String salary) throws IllegalArgumentException {
        Double doubleSalary;
        try {
            doubleSalary = Double.parseDouble(salary);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(WRONG_SALARY);
        }

        final Map<IFormField, String> fields = this.getCommonFields(mapToPass);

        return new Employee(fields.get(EnumFieldsCommon.NOME).trim(), fields.get(EnumFieldsCommon.COGNOME).trim(), 
                fields.get(EnumFieldsCommon.CODICE_FISCALE).trim(), fields.get(EnumFieldsCommon.INDIRIZZO).trim(), 
                fields.get(EnumFieldsCommon.TELEFONO).trim(), fields.get(EnumFieldsCommon.EMAIL).trim(), 
                this.gym, doubleSalary);
    }
}
