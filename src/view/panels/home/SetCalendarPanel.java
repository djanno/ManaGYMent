package view.panels.home;

import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.LINE_START;
import static java.awt.GridBagConstraints.NONE;
import static java.awt.GridBagConstraints.NORTH;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import model.gym.GymCalendar.DaysOfWeek;
import model.gym.ICourse;
import model.gym.Schedule;
import model.gym.members.IEmployee;
import utility.UtilityClass;
import view.panels.GenericTable;
import controller.panels.home.ISetCalendarController;

public class SetCalendarPanel extends GenericTable implements ISetCalendarPanel{

    private static final String PROGRAM_OF_A_DAY = "Programma del giorno";
    
    private static final long serialVersionUID = -231578880920299316L;
    private final JLabel lblDay;
    private final JCheckBox isOpened;
    private final JSpinner gymOpenFrom;
    private final JSpinner gymOpenTo;
    private final JComboBox<String> courses;
    private final JSpinner courseHourFrom;
    private final JSpinner courseHourTo;
    private final JComboBox<String> coachesPerCourse;
    private final JButton add;
    private final JButton remove;
    private final JButton end;
    private final JScrollPane scroll;
    private ISetCalendarController observer;

    public SetCalendarPanel(final String path) {
        super(new Object[]{"DA","A","NOME CORSO","COACH","CF DEL COACH"},path);
        this.lblDay = new JLabel();
        this.lblDay.setFont(new Font("Serif", Font.PLAIN, 25));
        this.lblDay.setForeground(Color.RED);
        this.isOpened = new JCheckBox();
        
        this.gymOpenFrom = new JSpinner(new SpinnerNumberModel(8, 1, 24, 1));
        this.gymOpenTo = new JSpinner(new SpinnerNumberModel(8, 1, 24, 1));
        this.courseHourFrom = new JSpinner(new SpinnerNumberModel(8, 1, 23, 1));
        this.courseHourTo = new JSpinner(new SpinnerNumberModel(8, 1, 24, 1));

        
        this.courses = new JComboBox<String>();
        this.coachesPerCourse = new JComboBox<String>();
        this.add = new JButton("Aggiungi corso");
        this.remove = new JButton("Rimuovi");
        this.remove.setEnabled(false);
        this.end = new JButton("Fine");
       
        this.scroll=new JScrollPane(this.table);
//        this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.scroll.setPreferredSize(new Dimension(400, 200));
        final Border border = BorderFactory.createLineBorder(Color.BLACK);
        this.scroll.setBorder(BorderFactory.createTitledBorder(border,PROGRAM_OF_A_DAY));
        
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.getColumnModel().getColumn(0).setPreferredWidth(5);
        this.table.getColumnModel().getColumn(1).setPreferredWidth(5);
        this.table.getColumnModel().getColumn(2).setPreferredWidth(80);
        this.table.getColumnModel().getColumn(3).setPreferredWidth(80);
        this.table.getColumnModel().getColumn(4).setPreferredWidth(80);

        final Insets insetsGeneral = new Insets(5, 5, 5, 5);
        
        setEditableSpinnerText(courseHourTo,courseHourFrom,gymOpenFrom,gymOpenTo);
        
        final GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);

        final GridBagConstraints gbconstLblDay = new GridBagConstraints(0,0,8,1,0.0,0.0,CENTER,NONE,insetsGeneral,1,1);
        this.add(lblDay, gbconstLblDay);
        
        final JLabel open = new JLabel("Aperta");
        final GridBagConstraints gbconstLblOpen = new GridBagConstraints(2,1,1,1,0.0,0.0,CENTER,NONE,insetsGeneral,1,1); 
        this.add(open, gbconstLblOpen);

        final GridBagConstraints gbconstCheckOpen = new GridBagConstraints(3,1,1,1,0.0,0.0,LINE_START,NONE,insetsGeneral,1,1);
        this.add(isOpened, gbconstCheckOpen);

        final JLabel lblFrom = new JLabel("apertura:");
        final GridBagConstraints gbconstLblFrom = new GridBagConstraints(4,1,1,1,0.0,0.0,CENTER,NONE,insetsGeneral,1,1);
        this.add(lblFrom, gbconstLblFrom);

        final GridBagConstraints gbconstCheckFrom = new GridBagConstraints(5,1,1,1,0.0,0.0,LINE_START,NONE,insetsGeneral,1,1);
        this.add(gymOpenFrom, gbconstCheckFrom);

        final JLabel lblTo = new JLabel("chiusura:");
        final GridBagConstraints gbconstLblTo = new GridBagConstraints(6,1,1,1,0.0,0.0,CENTER,NONE,insetsGeneral,1,1);
        this.add(lblTo, gbconstLblTo);

        final GridBagConstraints gbconstCheckTo = new GridBagConstraints(7,1,1,1,0.0,0.0,LINE_START,NONE,insetsGeneral,1,1);
        this.add(gymOpenTo, gbconstCheckTo);

        final JLabel lblCourses = new JLabel("Corsi");
        final GridBagConstraints gbconstLblCourses = new GridBagConstraints(0,2,1,1,0.0,0.0,CENTER,NONE,insetsGeneral,1,1);
        this.add(lblCourses, gbconstLblCourses);

        final GridBagConstraints gbconstComboCourses = new GridBagConstraints(1,2,1,1,0.0,0.0,LINE_START,NONE,insetsGeneral,1,1);
        this.add(courses, gbconstComboCourses);

        final JLabel lblFrom2 = new JLabel("Da:");
        final GridBagConstraints gbconstLblFromC = new GridBagConstraints(2,2,1,1,0.0,0.0,CENTER,NONE,insetsGeneral,1,1);
        this.add(lblFrom2, gbconstLblFromC);

        final GridBagConstraints gbconstCheckFromC = new GridBagConstraints(3,2,1,1,0.0,0.0,LINE_START,NONE,insetsGeneral,1,1);
        this.add(courseHourFrom, gbconstCheckFromC);

        final JLabel lblTo2 = new JLabel("A:");
        final GridBagConstraints gbconstLblToC = new GridBagConstraints(4,2,1,1,0.0,0.0,CENTER,NONE,insetsGeneral,1,1);
        this.add(lblTo2, gbconstLblToC);
        
        final JLabel lblWith=new JLabel("Con");
        final GridBagConstraints gbcLblWith = new GridBagConstraints(6,2,1,1,0.0,0.0,CENTER,NONE,insetsGeneral,1,1);
        this.add(lblWith,gbcLblWith);
        
        final GridBagConstraints gbcComboCoaches = new GridBagConstraints(7,2,1,1,0.0,0.0,CENTER,NONE,insetsGeneral,1,1);
        this.add(coachesPerCourse,gbcComboCoaches);
        
        final GridBagConstraints gbconstCheckToC = new GridBagConstraints(5,2,1,1,0.0,0.0,LINE_START,NONE,insetsGeneral,1,1);
        this.add(courseHourTo, gbconstCheckToC);

        final GridBagConstraints gbconstBAdd = new GridBagConstraints(7,3,1,1,0.0,0.0,NORTH,NONE,insetsGeneral,1,1);
        this.add(createVerticalPanel(add,remove), gbconstBAdd);

        final GridBagConstraints gbconstArea = new GridBagConstraints(0,3,7,1,0.0,0.0,CENTER,NONE,insetsGeneral,1,1);
        this.add(scroll, gbconstArea);

        final GridBagConstraints gbconstBSave = new GridBagConstraints(0,4,8,1,0.0,0.0,CENTER,NONE,insetsGeneral,20,10);
        this.add(end, gbconstBSave);

        disenableIfNotOpen();
        
        setHandlers();
      
    }
    
    private void setEditableSpinnerText(final JSpinner...jSpinners){
        for(final JSpinner spinner :jSpinners){
            ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);        
        }
    }
    
    private JPanel createVerticalPanel(final JComponent... components){
        final JPanel panel=new JPanel(new GridBagLayout());
        final GridBagConstraints gbc=new GridBagConstraints();
        for(final JComponent comp:components){
            panel.add(comp);
            gbc.gridy++;
        }
        return panel;
    }
        

    @Override
    public void loadFields(final DaysOfWeek day,final Schedule schedule,final List<ICourse> gymCourses,final List<IEmployee> gymCoaches ) {
        this.lblDay.setText(day.getName());
        this.isOpened.setSelected(schedule.isOpened());
        this.observer.formTable();
        this.courses.setModel(new DefaultComboBoxModel<String>(UtilityClass.createComboBoxValues(gymCourses))); 
        this.coachesPerCourse.setModel(new DefaultComboBoxModel<String>(UtilityClass.createComboBoxValues(gymCoaches)));
        this.gymOpenFrom.setValue(schedule.getOpeningHour().orElse(8));
        this.gymOpenTo.setValue(schedule.getClosingHour().orElse(22));
        this.courseHourFrom.setValue(schedule.getOpeningHour().orElse(8));
        this.courseHourTo.setValue(schedule.getOpeningHour().orElse(8)+1);
    }
    
    @Override
    public void attachViewObserver(final ISetCalendarController observer) {
        this.observer = observer;
    }
    
    private void disenableIfNotOpen() {
        final boolean isSelected = isOpened.isSelected();
        this.gymOpenFrom.setEnabled(isSelected);
        this.gymOpenTo.setEnabled(isSelected);
        this.courses.setEnabled(isSelected);
        this.courseHourFrom.setEnabled(isSelected);
        this.courseHourTo.setEnabled(isSelected);
        this.coachesPerCourse.setEnabled(isSelected);
        this.table.setEnabled(isSelected);
        this.add.setEnabled(isSelected);
    }
     
    
    private void setHandlers(){
        this.add.addActionListener(e->this.observer.addPairInHoursCmd((String)this.courses.getSelectedItem(),(String)this.coachesPerCourse.getSelectedItem(),(Integer)this.courseHourFrom.getValue(),
                (Integer) this.courseHourTo.getValue(), (Integer)this.gymOpenFrom.getValue(), (Integer)this.gymOpenTo.getValue()));
 
        this.remove.addActionListener(e->{
            final int selectedRow=this.table.getSelectedRow();
            this.observer.removePairInHourCmd((Integer)((DefaultTableModel)this.table.getModel()).getValueAt(selectedRow, 0),
                    (String)((DefaultTableModel)this.table.getModel()).getValueAt(selectedRow, 2),
                    (String)((DefaultTableModel)this.table.getModel()).getValueAt(selectedRow, 4));
        });
        
        this.end.addActionListener(e->this.observer.endCmd(this.isOpened.isSelected(), (Integer)this.gymOpenFrom.getValue(), (Integer) this.gymOpenTo.getValue()));
        this.isOpened.addItemListener(e->disenableIfNotOpen());
        this.courses.addItemListener(e->this.coachesPerCourse.setModel(new DefaultComboBoxModel<String>(UtilityClass.createComboBoxValues(this.observer.loadCoachesByCourseName((String)this.courses.getSelectedItem())))));
        UtilityClass.setListListenerTable(this.table, this.remove);

    }

}
