package view.panels.home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.gym.Course;
import model.gym.ICourse;

/**
 * A panel used to display a legend of all the courses of the {@link Gym} of the logged user.
 * This panel is displayed inside the {@link HomePanel}, at the bottom of the panel.
 * The purpose of the panel is to make the weekly schedule displayed in the {@link HomePanel} readable.
 * @author Federico Giannoni
 * @author Simone Letizi
 *
 */
public class LegendPanel extends JScrollPane {

    private static final long serialVersionUID = -1598523759820579575L;
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
    private static final int BUTTON_FOR_ROW = 6;
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 25;

    private final ICourse closed = new Course("chiuso", Color.BLACK, 0, 0);
    private final ICourse opened = new Course("no corsi", Color.WHITE, 0, 0);
    private final Collection<JButtonCourse> buttons;
    private final List<ICourse> list;
    private final JPanel wrapperPanel;

    /**
     * Constructs a legend panel for the specified courses.
     * @param courses the courses that will be displayed on the legend panel.
     */
    public LegendPanel(final List<ICourse> courses) {
        super();
        this.list = new ArrayList<ICourse>(courses);
        this.list.add(this.closed);
        this.list.add(this.opened);
        this.buttons = new ArrayList<>();
        this.wrapperPanel = new JPanel(new GridBagLayout());
        this.setPreferredSize(new Dimension(800, 80));
        this.setViewportView(drawPanel());
    }

    /**
     * Draws a {@link JPanel} containing a button for each of the courses that the legend panel has to display.
     * These buttons are unclickable and have a purely visual function.
     * @return a panel containing a button for each of the courses that the legend panel has to display.
     */
    private JPanel drawPanel() {
        final GridBagConstraints gbcButtons = new GridBagConstraints();
        final Border bLine = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        final Border bTitled1 = BorderFactory.createTitledBorder(bLine, "Legenda dei corsi", TitledBorder.LEFT, TitledBorder.TOP);
        this.wrapperPanel.setBorder(bTitled1);
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        for (final ICourse course : list) {
            final JButtonCourse legendCourse = new JButtonCourse(course.getCourseName(), course.getCourseColor(), BUTTON_WIDTH, BUTTON_HEIGHT,
                    BUTTON_FONT);
            this.buttons.add(legendCourse);
            this.wrapperPanel.add(legendCourse, gbcButtons);
            gbcButtons.gridx++;
            if (gbcButtons.gridx == BUTTON_FOR_ROW) {
                gbcButtons.gridx = 0;
                gbcButtons.gridy++;
            }
        }
        return wrapperPanel;
    }

    /**
     * A colored button with a purely visual function. These buttons are the ones that will be used inside the
     * {@link LegendPanel} to display the various courses.
     * @author Federico Giannoni
     * @author Simone Letizi
     *
     */
    class JButtonCourse extends JButton {
        private static final long serialVersionUID = 5560195479526786254L;

        /**
         * Construct a button that represent a course.
         * @param courseName the name of the course that the button has to represent.
         * @param courseColor the color of the course that the button has to represent
         * @param width the width of the button.
         * @param height the height of the button.
         * @param f the font for the button.
         */
        public JButtonCourse(final String courseName, final Color courseColor, final int width, final int height, final Font f) {
            super(courseName);
            this.setBackground(courseColor);
            this.setPreferredSize(new Dimension(width, height));
            this.setFont(f);
            this.setEnabled(false);
        }
    }
}
