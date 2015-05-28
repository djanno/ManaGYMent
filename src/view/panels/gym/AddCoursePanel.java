package view.panels.gym;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import view.panels.Background;
import controller.panels.gym.IAddCourseController;

/**
 * This class display a window to adds a new course  
 * @author simone
 *
 */
public class AddCoursePanel extends Background implements IAddCoursePanel {

    private static final long serialVersionUID = -487645402800341192L;
    private final EssentialPanelCourse pCenter;
    private final JButton addCourse;
    private IAddCourseController observer;

    /**
     * Construct a panel for add the information of new course 
     * @param path
     *          the background image path
     */
    public AddCoursePanel(final String path) {
        super(path);
        this.pCenter = new EssentialPanelCourse(path);

        this.addCourse = new JButton("Aggiungi Corso");

        this.setLayout(new BorderLayout());
        final JPanel psouth = new JPanel();
        psouth.add(addCourse);

        this.add(pCenter, BorderLayout.CENTER);
        this.add(psouth, BorderLayout.SOUTH);

        final Border border = BorderFactory.createLineBorder(Color.BLACK);
        this.setBorder(BorderFactory.createTitledBorder(border, "Aggiungi Corso"));
        this.setOpaque(false);

        this.setHandlers();
    }

    @Override
    public void attachViewObserver(final IAddCourseController observer) {
        this.observer = observer;
    }

    private void setHandlers() {
        this.addCourse.addActionListener(e -> {
            this.observer.addCourseCmd(this.pCenter.getCourseName(), this.pCenter.getButtonColor(), this.pCenter.getCoursePrice(),
                    this.pCenter.getCourseMaxMember());
        });

    }

}
