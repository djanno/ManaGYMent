package controller.panels.gym;

import java.awt.Color;
import model.IModel;
import model.gym.Course;
import model.gym.ICourse;
import model.gym.members.IEmployee;
import view.PrimaryFrame;
import view.panels.gym.EditCoursePanel;
import view.panels.gym.IAddCoursePanel;

public class EditCourseController extends AddCourseController implements
        IEditCourseObserver {

    private final ICourse courseToEdit;

    public EditCourseController(final PrimaryFrame frame, final IModel model,
            final IAddCoursePanel view, final ICourse courseToEdit) {
        super(frame, model, view);
        this.courseToEdit = courseToEdit;
    }

    @Override
    public void loadData() {
        ((EditCoursePanel) this.view).showData(this.courseToEdit, this.model.getUser(this.frame.getActiveUser()).getGym().getEmployees());
    }

    @Override
    public void formTable() {
        ((EditCoursePanel) this.view).refreshTable();
        for (final IEmployee employee : this.courseToEdit.getCoaches()) {
            ((EditCoursePanel) this.view).addDataRow(employee.alternativeToString().split(" "));
        }
    }

    @Override
    public void addCoachCmd(final int index) {
        try {
            final IEmployee employee = model.getUser(this.frame.getActiveUser()).getGym().getEmployees().get(index);
            this.courseToEdit.addCoach(employee);
            this.formTable();
        } catch (final IllegalArgumentException e) {
            this.view.showError(e.getMessage());
        }
    }

    @Override
    public void removeCoachCmd(final int index) {
        this.model.getUser(this.frame.getActiveUser()).getGym().getCourses().stream()
                .filter(c -> c.equals(this.courseToEdit))
                .findAny()
                .ifPresent(c -> c.removeCoach(index));
        this.formTable();
    }

    @Override
    public void editCourseCmd(final String courseName, final Color courseColor,
            final String price, final String maxMembers) {
        final int indexInList = this.model.getUser(this.frame.getActiveUser()).getGym().getCourses().indexOf(this.courseToEdit);
        try {
            this.model.getUser(this.frame.getActiveUser()).getGym().removeCourse(this.courseToEdit);
            super.checkError(courseName, courseColor, price, maxMembers);
            final ICourse newCourse = new Course(courseName, courseColor, Double.parseDouble(price), Integer.parseInt(maxMembers));
            this.courseToEdit.getCoaches().forEach(empl->newCourse.addCoach(empl));
            this.model.getUser(this.frame.getActiveUser()).getGym().addCourse(indexInList, newCourse);
        } catch (final Exception exc) {
            this.view.showError(exc.getMessage());
            this.model.getUser(this.frame.getActiveUser()).getGym().addCourse(indexInList, this.courseToEdit);
        }
    }

}
