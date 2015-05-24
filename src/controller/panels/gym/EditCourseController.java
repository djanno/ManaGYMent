package controller.panels.gym;

import java.awt.Color;

import javax.swing.JOptionPane;

import model.IModel;
import model.gym.Course;
import model.gym.GymCalendar;
import model.gym.ICourse;
import model.gym.IGymCalendar;
import model.gym.Pair;
import model.gym.Schedule;
import model.gym.GymCalendar.DaysOfWeek;
import model.gym.members.IEmployee;
import view.PrimaryFrame;
import view.panels.gym.EditCoursePanel;
import view.panels.gym.IAddCoursePanel;

public class EditCourseController extends AddCourseController implements
        IEditCourseController {

    private static final String CONFIRM_REMOVE = "Se il coach che si tenta di rimuovere è accoppiato al corso nel calendario la coppia verrà rimossa.\nSei sicuro di voler procedere con la cancellazione?";
    private final ICourse courseToEdit;
    private final ICourse temp;
    private final IGymCalendar tempCalendar;
    
    public EditCourseController(final PrimaryFrame frame, final IModel model,
            final IAddCoursePanel view, final GymPanelController gymPanelController, final ICourse courseToEdit) {
        super(frame, model, view, gymPanelController);
        this.courseToEdit = courseToEdit;
        this.temp = new Course(courseToEdit.getCourseName(),courseToEdit.getCourseColor(), courseToEdit.getCoursePrice(), courseToEdit.getMaxMembers(), courseToEdit.getCoaches(),courseToEdit.getCurrentMembers());
        this.tempCalendar=new GymCalendar();
        for(DaysOfWeek day:DaysOfWeek.values()){
            final Schedule schedule = this.model.getGym(this.frame.getActiveUser()).getProgram().getCalendar().get(day);
            this.tempCalendar.setSchedule(day, new Schedule(schedule.isOpened(), schedule.getOpeningHour().orElse(null), schedule.getClosingHour().orElse(null), schedule.getProgram())); 
        }
        
        
    }

    @Override
    public void loadData() {
        ((EditCoursePanel) this.view).showData(this.temp, this.model.getGym(this.frame.getActiveUser()).getEmployees());
    }

    @Override
    public void formTable() {
        ((EditCoursePanel) this.view).refreshTable();
        for (final IEmployee employee : this.temp.getCoaches()) {
            ((EditCoursePanel) this.view).addDataRow(employee.alternativeToString().split(" "));
        }
    }

    @Override
    public void addCoachCmd(final int index) {
        try {
            final IEmployee employee = model.getGym(this.frame.getActiveUser()).getEmployees().get(index);
            this.temp.addCoach(employee);
            this.formTable();
        } catch (final IllegalArgumentException e) {
            this.frame.displayError(e.getMessage());
        }
    }

    @Override
    public void removeCoachCmd(final int index) {
        final Pair<ICourse, IEmployee> pairToDelete = new Pair<>(this.temp, this.temp.getCoaches().get(index));
        final int option = JOptionPane.showConfirmDialog(this.frame.getChild(), CONFIRM_REMOVE, "Warning", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            this.tempCalendar.getCalendar().forEach((day, sch) -> sch.deletePair(pairToDelete));
            this.temp.removeCoach(index);
            this.formTable();
        }
    }

    @Override
    public void editCourseCmd(final String courseName, final Color courseColor,
            final String price, final String maxMembers) {
        final int indexInList = this.model.getGym(this.frame.getActiveUser()).getCourses().indexOf(this.courseToEdit);
        try {
            this.model.getGym(this.frame.getActiveUser()).removeCourse(indexInList);
            this.checkError(courseName, courseColor, price, maxMembers);
            this.model.getGym(this.frame.getActiveUser()).addCourse(indexInList, temp);
            this.model.getGym(this.frame.getActiveUser()).setCalendar(this.tempCalendar);
            this.frame.getChild().closeDialog();
        } catch (final Exception exc) {
            this.frame.displayError(exc.getMessage());
            this.model.getGym(this.frame.getActiveUser()).addCourse(indexInList, courseToEdit);
        }
        this.gymPanelController.loadCoursesTable();
    }
    
    protected void checkError(final String courseName, final Color courseColor, final String price, final String maxMembers) throws IllegalArgumentException{
        super.checkError(courseName, courseColor, price, maxMembers);
        if(Integer.parseInt(maxMembers)<this.temp.getCurrentMembers().size()){
            throw new IllegalArgumentException("impossibile diminuire i membri del corso perchè al momento gli iscritti sono già " + this.temp.getCurrentMembers().size());
        }
        
    }

}
