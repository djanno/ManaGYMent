package controller.panels.gym;

import java.awt.Color;

import model.IModel;
import model.gym.Course;
import model.gym.ICourse;
import view.PrimaryFrame;
import view.panels.gym.IAddCoursePanel;

public class AddCourseController implements IAddCourseController{

    
    private static final String NO_INSERT_STRING ="I campi Prezzo e Membri massimi non devono contenere stringhe ";
    private static final String NO_NEGATIVE_VALUES = "Inserire solo numeri positivi";
    private static final String NO_SELECT_COLOR = "Selezionare il colore per il corso che si desidera aggiungere! (NO BIANCO O NERO)"; 
    private static final String NO_EMPTY_FIELDS= "Riempire i campi vuoti";
    private static final String NAME_ALREADY_EXISTS= "Il nome del corso inserito esiste già";
    private static final String COLOR_ALREADY_EXISTS= "Il colore scelto è già stato assegnato ad un corso";
    protected IModel model;
    protected IAddCoursePanel view;
    protected PrimaryFrame frame;
    protected GymPanelController gymPanelController;
    
    public AddCourseController(final PrimaryFrame frame, final IModel model, final IAddCoursePanel view, final GymPanelController gymPanelController) {
        super();
        this.model = model;
        this.view = view;
        this.frame = frame;
        this.gymPanelController = gymPanelController;
        this.view.attachViewObserver(this);
    }   


    @Override
    public void addCourseCmd(final String courseName,final Color courseColor, final String price,final  String maxMembers) throws IllegalArgumentException{
            try{
                this.checkError(courseName, courseColor, price, maxMembers);
                final ICourse course=new Course(courseName,  courseColor, Integer.parseInt(price), Integer.parseInt(maxMembers));
                this.model.getUser(this.frame.getActiveUser()).getGym().addCourse(course);
                this.gymPanelController.loadCoursesTable();
                this.frame.getChild().closeDialog();
            }catch(Exception exc){
                this.view.displayError(exc.getMessage());
            }
    }
    
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
           this.view.displayError(NO_INSERT_STRING);
        }
    }
    
    
    private void checkName(final String enteredName)throws IllegalArgumentException{
      final boolean alreadyExist=this.model.getUser(this.frame.getActiveUser()).getGym().getCourses().stream().anyMatch(c->enteredName.equalsIgnoreCase(c.getCourseName()));
      if(alreadyExist){
          throw new IllegalArgumentException(NAME_ALREADY_EXISTS);
      }
    } 
    
    private void checkColor(final Color color) throws IllegalArgumentException{
        if(color.equals(Color.WHITE) || color.equals(Color.BLACK)){
            throw new IllegalArgumentException(NO_SELECT_COLOR);
        }else{
          final boolean alreadyExist=this.model.getUser(this.frame.getActiveUser()).getGym().getCourses().stream().anyMatch(c->c.getCourseColor().equals(color));
          if(alreadyExist){
              throw new IllegalArgumentException(COLOR_ALREADY_EXISTS);
          }
        }
    }

}
