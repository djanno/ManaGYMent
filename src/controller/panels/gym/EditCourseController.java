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
        IEditCourseController {

    private final ICourse courseToEdit;

    public EditCourseController(final PrimaryFrame frame, final IModel model,
            final IAddCoursePanel view, final GymPanelController gymPanelController, final ICourse courseToEdit) {
        super(frame, model, view, gymPanelController);
        this.courseToEdit = courseToEdit;
    }

    @Override
    public void loadData() {
        ((EditCoursePanel) this.view).showData(this.courseToEdit, this.model.getGym(this.frame.getActiveUser()).getEmployees());
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
            final IEmployee employee = model.getGym(this.frame.getActiveUser()).getEmployees().get(index);
            this.courseToEdit.addCoach(employee);
            this.formTable();
        } catch (final IllegalArgumentException e) {
            this.view.displayError(e.getMessage());
        }
    }

    @Override
    public void removeCoachCmd(final int index) {
        this.model.getGym(this.frame.getActiveUser()).getCourses().stream()
                .filter(c -> c.equals(this.courseToEdit))
                .findAny()
                .ifPresent(c -> c.removeCoach(index));
        this.formTable();
    }

    @Override
    public void editCourseCmd(final String courseName, final Color courseColor,
            final String price, final String maxMembers) {
        final int indexInList = this.model.getGym(this.frame.getActiveUser()).getCourses().indexOf(this.courseToEdit);
        try {
            this.model.getGym(this.frame.getActiveUser()).removeCourse(indexInList);
            super.checkError(courseName, courseColor, price, maxMembers);
            final ICourse newCourse = new Course(courseName, courseColor, Double.parseDouble(price), Integer.parseInt(maxMembers));
            for (final IEmployee employee : this.courseToEdit.getCoaches()) {
                newCourse.addCoach(employee);
            }
            this.model.getGym(this.frame.getActiveUser()).addCourse(indexInList, newCourse);
            this.frame.getChild().closeDialog();
        } catch (final Exception exc) {
            this.view.displayError(exc.getMessage());
            this.model.getGym(this.frame.getActiveUser()).addCourse(indexInList, this.courseToEdit);
        }
        this.gymPanelController.loadCoursesTable();
    }

}
