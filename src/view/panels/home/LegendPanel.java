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

    private JPanel drawPanel() {
        final GridBagConstraints gbcButtons = new GridBagConstraints();
        final Border bLine = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        final Border bTitled1 = BorderFactory.createTitledBorder(bLine, "Legenda dei corsi", TitledBorder.LEFT, TitledBorder.TOP);
        this.wrapperPanel.setBorder(bTitled1);
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        for (final ICourse course : list) {
            final JButtonCourse legendCourse = new JButtonCourse(course.getCourseName(),course.getCourseColor(), BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_FONT);
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
       
    class JButtonCourse extends JButton{
        private static final long serialVersionUID = 5560195479526786254L;
        
        public JButtonCourse(final String courseName, final Color courseColor, final int width, final int height, final Font f) {
            super(courseName);
            this.setBackground(courseColor);
            this.setPreferredSize(new Dimension(width, height));
            this.setFont(f);
            this.setEnabled(false);
        }
    }
}    
    
