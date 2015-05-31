package controller.panels.home;

import java.awt.BorderLayout;
import java.util.ArrayList;

import model.gym.GymCalendar.DaysOfWeek;
import model.gym.ICourse;
import model.gym.IGym;
import model.gym.IGymCalendar;
import model.gym.ISchedule;
import view.PrimaryFrame;
import view.panels.home.HomePanel;
import view.panels.home.HomePanel.CoursesWrapper;
import view.panels.home.LegendPanel;
import view.panels.home.SetCalendarPanel;

/**
 * The controller for the {@link HomePanel}.
 * 
 * @author Federico Giannoni
 *
 */
public class HomePanelController implements IHomePanelController {

    private static final String DIALOG_TITLE = "Modifica l'orario della giornata: ";
    private static final int DIALOG_WIDTH = 750;
    private static final int DIALOG_HEIGHT = 420;

    private final IGym gym;
    private final PrimaryFrame frame;
    private final HomePanel view;

    /**
     * The constructor for the controller.
     * 
     * @param gym
     *            the data of the gym to be manipulated.
     * @param frame
     *            the primary frame of the application.
     * @param view
     *            the view.
     */
    public HomePanelController(final IGym gym, final PrimaryFrame frame, final HomePanel view) {
        this.gym = gym;
        this.frame = frame;
        this.view = view;
        this.view.add(new LegendPanel(this.gym.getCourses()), BorderLayout.SOUTH);
        this.view.attachObserver(this);
    }

    @Override
    public void loadCalendar() {
        this.view.refreshTable();
        final IGymCalendar calendar = this.gym.getProgram();

        int minHour = 24;
        int maxHour = 1;
        boolean empty = true;

        ISchedule schedule = null;

        for (final DaysOfWeek day : calendar.getCalendar().keySet()) {
            schedule = calendar.getCalendar().get(day);
            if (schedule.getOpeningHour().isPresent() && schedule.getOpeningHour().get() < minHour) {
                empty = false;
                minHour = schedule.getOpeningHour().get();
            }

            if (schedule.getClosingHour().isPresent() && schedule.getClosingHour().get() > maxHour) {
                empty = false;
                maxHour = schedule.getClosingHour().get();
            }
        }

        Object[] row = new Object[DaysOfWeek.values().length + 1];

        if (empty) {

            row[0] = "--:--";

            for (int i = 1; i < row.length; i++) {
                row[i] = new CoursesWrapper(new ArrayList<ICourse>(), false);
            }

            this.view.addDataRow(row);
        }

        else {
            for (int i = minHour; i < maxHour; i++) {
                row[0] = i + ":00";

                for (final DaysOfWeek day : DaysOfWeek.values()) {
                    row[day.ordinal() + 1] = new CoursesWrapper(calendar.getCalendar().get(day).getCoursesInHour(i), calendar.getCalendar().get(day)
                            .isGymOpenedAt(i));
                }

                this.view.addDataRow(row);
            }
        }
    }

    @Override
    public void cmdEditDaySchedule(final String day) {
        final SetCalendarPanel panel = new SetCalendarPanel(this.view.getBackgroundPath());
        final ISetCalendarController controller = new SetCalendarController(this.gym, this.frame, panel, this, DaysOfWeek.getValueByName(day));
        try {
            controller.loadData();
            this.frame.new DialogWindow(DIALOG_TITLE + day, DIALOG_WIDTH, DIALOG_HEIGHT, this.frame, panel);
        } catch (final Exception e) {
            this.frame.displayError(e.getMessage());
        }
    }

}
