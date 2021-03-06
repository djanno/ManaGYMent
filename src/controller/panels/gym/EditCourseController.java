package controller.panels.gym;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import model.gym.Course;
import model.gym.GymCalendar;
import model.gym.GymCalendar.DaysOfWeek;
import model.gym.ICourse;
import model.gym.IGym;
import model.gym.IGymCalendar;
import model.gym.ISchedule;
import model.gym.Schedule;
import model.gym.Schedule.Pair;
import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;
import view.PrimaryFrame;
import view.panels.gym.EditCoursePanel;
import view.panels.gym.IAddCoursePanel;

/**
 * the {@link EditCoursePanel} observer, used for edit a course already present
 * in gym
 * 
 * @author simone
 *
 */
public class EditCourseController extends AddCourseController implements IEditCourseController {

    private static final String CONFIRM_REMOVE = "Se il coach che si tenta di rimuovere è accoppiato al corso nel calendario la coppia verrà rimossa.\nSei sicuro di voler procedere con la cancellazione?";
    private final ICourse courseToEdit;
    private final ICourse temp;
    private final IGymCalendar tempCalendar;

    /**
     * Constructor
     * 
     * @param frame
     *            the application's frame
     * @param gym
     *            the gym
     * @param view
     *            the view
     * @param gymPanelController
     *            the controller of panel that open EditCoursePanel JDialog
     * @param courseToEdit
     *            course to be edit
     */
    public EditCourseController(final PrimaryFrame frame, final IGym gym, final IAddCoursePanel view, final GymPanelController gymPanelController,
            final ICourse courseToEdit) {
        super(frame, gym, view, gymPanelController);
        this.courseToEdit = courseToEdit;
        this.temp = new Course(courseToEdit.getCourseName(), courseToEdit.getCourseColor(), courseToEdit.getCoursePrice(),
                courseToEdit.getMaxMembers(), courseToEdit.getCoaches(), courseToEdit.getCurrentMembers());
        this.tempCalendar = new GymCalendar();
        this.swapCoursesInCalendar(this.courseToEdit, this.temp, this.tempCalendar);

    }

    @Override
    public void loadData() {
        ((EditCoursePanel) this.view).showData(this.temp, this.gym.getEmployees());
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
            final IEmployee employee = this.gym.getEmployees().get(index);
            this.temp.addCoach(employee);
            this.formTable();
        } catch (final IllegalArgumentException e) {
            this.frame.displayError(e.getMessage());
        }
    }

    @Override
    public void removeCoachCmd(final int index) {
        final int option = JOptionPane.showConfirmDialog(this.frame.getChild(), CONFIRM_REMOVE, "Warning", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            final Schedule.Pair<ICourse, IEmployee> pairToDelete = new Schedule.Pair<>(this.temp, this.temp.getCoaches().get(index));
            this.tempCalendar.getCalendar().forEach((day, sch) -> sch.deletePair(pairToDelete));
            this.temp.removeCoach(index);
            this.formTable();
        }
    }

    @Override
    public void editCourseCmd(final String courseName, final Color courseColor, final String price, final String maxMembers) {
        final int indexInList = this.gym.getCourses().indexOf(this.courseToEdit);
        final List<ISubscriber> membershipsToEdit = this.gym.getSubscribers().stream()
                .filter(subscriber -> subscriber.getCourses().contains(this.courseToEdit)).collect(Collectors.toList());
        final IGymCalendar backupCalendar = new GymCalendar();
        
        try {
            this.swapCoursesInCalendar(this.temp, this.courseToEdit, backupCalendar);
            this.gym.removeCourse(indexInList);
            this.checkError(courseName, courseColor, price, maxMembers);
            temp.setCourseName(courseName);
            temp.setCourseColor(courseColor);
            temp.setCoursePrice(Double.parseDouble(price));
            temp.setMaxMembers(Integer.parseInt(maxMembers));
            this.gym.addCourse(indexInList, temp);
            membershipsToEdit.forEach(subscriber -> subscriber.addToCourse(this.temp));
            this.gym.setCalendar(this.tempCalendar);
            this.frame.getChild().closeDialog();
        } catch (final Exception exc) {
            this.frame.displayError(exc.getMessage());
            this.gym.addCourse(indexInList, courseToEdit);
            this.gym.setCalendar(backupCalendar);
            membershipsToEdit.forEach(subscriber -> subscriber.addToCourse(this.courseToEdit));
        }
        this.gymPanelController.loadCoursesTable();
    }

    /*
     * @see
     * controller.panels.gym.AddCourseController#checkError(java.lang.String,
     * java.awt.Color, java.lang.String, java.lang.String)
     */
    protected void checkError(final String courseName, final Color courseColor, final String price, final String maxMembers)
            throws IllegalArgumentException {
        super.checkError(courseName, courseColor, price, maxMembers);
        if (Integer.parseInt(maxMembers) < this.temp.getCurrentMembers().size()) {
            throw new IllegalArgumentException("impossibile diminuire i membri del corso perchè al momento gli iscritti sono già "
                    + this.temp.getCurrentMembers().size());
        }

    }
    
    /**
     * Swaps the courses in the provided gym calendar.
     * @param oldCourse the course to be swapped out.
     * @param newCourse the course to be swapped in.
     * @param calendar the calendar in which the courses will be swapped.
     */
    private void swapCoursesInCalendar(final ICourse oldCourse, final ICourse newCourse, final IGymCalendar calendar) {
        for (final DaysOfWeek day : DaysOfWeek.values()) {
            final ISchedule schedule = this.gym.getProgram().getCalendar().get(day);
            final ISchedule tempSchedule = new Schedule(schedule.isOpened(), schedule.getOpeningHour().orElse(null), schedule.getClosingHour()
                    .orElse(null), schedule.getProgram());
            
            tempSchedule.getProgram().forEach((hour, pairs) -> pairs.forEach(pair -> {
                if(pair.getX().equals(oldCourse)) {
                        tempSchedule.removePairInHour(pair, hour);
                        tempSchedule.putPairInHour(new Pair<ICourse, IEmployee>(newCourse, pair.getY()), hour, hour + 1);
                }
            }));
            
            calendar.setSchedule(day, tempSchedule);
        }
    }
}
