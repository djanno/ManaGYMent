package view.panels.gym;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controller.panels.gym.IAddCourseObserver;

public class AddCoursePanel extends JPanel implements IAddCoursePanel{

    private static final long serialVersionUID = -487645402800341192L;
    private final EssentialPanelCourse pCenter;
    private final JButton addCourse;
    private IAddCourseObserver observer;

    public AddCoursePanel() {
        this.pCenter = new EssentialPanelCourse();
        
        this.addCourse = new JButton("Aggiungi Corso");

        this.setLayout(new BorderLayout());
        final JPanel psouth = new JPanel();
        psouth.add(addCourse);

        this.add(pCenter, BorderLayout.CENTER);
        this.add(psouth, BorderLayout.SOUTH);
        
        final Border border = BorderFactory.createLineBorder(Color.BLACK);
        this.setBorder(BorderFactory.createTitledBorder(border, "Aggiungi Corso"));
        this.setOpaque(false);
        
        setHandlers();
    }


    @Override
    public void attachViewObserver(final IAddCourseObserver observer) {
        this.observer = observer;
    }
    
    public void showError(final String message) {
        JOptionPane.showMessageDialog(this, message, "An error occurred", JOptionPane.ERROR_MESSAGE);
    }
    
    private void setHandlers(){
        this.addCourse.addActionListener(e->{
                this.observer.addCourseCmd(this.pCenter.getCourseName(),this.pCenter.getButtonColor(),this.pCenter.getCoursePrice(),this.pCenter.getCourseMaxMember());
        });
                
    }

}
