package controller.panels.gym;

import java.awt.Color;

import model.gym.Course;
import model.gym.ICourse;
import model.gym.IGym;
import view.PrimaryFrame;
import view.panels.gym.AddCoursePanel;
import view.panels.gym.IAddCoursePanel;

/**
 * the {@link AddCoursePanel} observer, used for create and add course in gym
 * @author simone
 * 
 */
public class AddCourseController implements IAddCourseController{

    
    private static final String NO_INSERT_STRING ="I campi Prezzo e Membri massimi non devono contenere stringhe ";
    private static final String NO_NEGATIVE_VALUES = "Inserire solo numeri positivi";
    private static final String NO_SELECT_COLOR = "Selezionare il colore per il corso che si desidera aggiungere! (NO BIANCO O NERO)"; 
    private static final String NO_EMPTY_FIELDS= "Riempire i campi vuoti";
    private static final String NAME_ALREADY_EXISTS= "Il nome del corso inserito esiste già";
    private static final String COLOR_ALREADY_EXISTS= "Il colore scelto è già stato assegnato ad un corso";
    protected IGym gym;
    protected IAddCoursePanel view;
    protected PrimaryFrame frame;
    protected GymPanelController gymPanelController;
    
    /**
     * Constructor
     * 
     * @param frame 
     *          the application's frame.
     * @param gym
     *          the gym
     * @param view 
     *          the view
     * @param gymPanelController 
     *          the controller of panel that open AddCoursePanel JDialog
     */
    public AddCourseController(final PrimaryFrame frame, final IGym gym, final IAddCoursePanel view, final GymPanelController gymPanelController) {
        super();
        this.gym = gym;
        this.view = view;
        this.frame = frame;
        this.gymPanelController = gymPanelController;
        this.view.attachViewObserver(this);
    }   


    @Override
    public void addCourseCmd(final String courseName,final Color courseColor, final String price, final  String maxMembers){
            try{
                this.checkError(courseName, courseColor, price, maxMembers);
                final ICourse course=new Course(courseName,  courseColor, Double.parseDouble(price), Integer.parseInt(maxMembers));
                this.gym.addCourse(course);
                this.gymPanelController.loadCoursesTable();
                this.frame.getChild().closeDialog();
            }catch(Exception exc){
                this.frame.displayError(exc.getMessage());
            }
    }
    
    
    /**
     * check if the data entered is correct
     * 
     * @param courseName the name to be checked
     * @param courseColor the color to be checked
     * @param price the price to be checked
     * @param maxMembers the number of max member to be checked
     * @throws IllegalArgumentException if the fields are empty, if name is already existing or if color is already associated to a course
     * @throws NumberFormatException if there are conversion errors
     */
    protected void checkError(final String courseName, final Color courseColor, final String price, final String maxMembers) throws IllegalArgumentException, NumberFormatException{
        if (price.isEmpty() || maxMembers.isEmpty() || courseName.isEmpty()) {
            throw new IllegalArgumentException(NO_EMPTY_FIELDS);
        }

        if (price.charAt(0)=='-' || maxMembers.charAt(0)=='-' || courseName.charAt(0)=='-') {
                throw new NumberFormatException(NO_NEGATIVE_VALUES);
        }
        checkName(courseName);
        checkColor(courseColor);
        try {
            Double.parseDouble(price);
            Integer.parseInt(maxMembers);
        } catch (final NumberFormatException parseErr) {
           throw new NumberFormatException(NO_INSERT_STRING);
        }
    }
    
    
    /**
     * check if the name entered is valid
     * 
     * @param enteredName the name to be checked
     * @throws IllegalArgumentException
     */
    private void checkName(final String enteredName)throws IllegalArgumentException{
      final boolean alreadyExist=this.gym.getCourses().stream().anyMatch(c->enteredName.equalsIgnoreCase(c.getCourseName()));
      if(alreadyExist){
          throw new IllegalArgumentException(NAME_ALREADY_EXISTS);
      }
    } 
    
    /**
     * check if the color entered is valid
     * 
     * @param color the color to be checked
     * @throws IllegalArgumentException
     */
    private void checkColor(final Color color) throws IllegalArgumentException{
        if(color.equals(Color.WHITE) || color.equals(Color.BLACK)){
            throw new IllegalArgumentException(NO_SELECT_COLOR);
        }else{
          final boolean alreadyExist=this.gym.getCourses().stream().anyMatch(c->c.getCourseColor().equals(color));
          if(alreadyExist){
              throw new IllegalArgumentException(COLOR_ALREADY_EXISTS);
          }
        }
    }

}
