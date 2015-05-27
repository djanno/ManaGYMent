package view.panels.gym;

import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.NONE;
import static java.awt.GridBagConstraints.NORTH;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import model.gym.ICourse;
import model.gym.members.IEmployee;
import utility.UtilityClass;
import view.panels.Background;
import view.panels.GenericTable;
import controller.panels.gym.EditCourseController;
import controller.panels.gym.IAddCourseController;

public class EditCoursePanel extends GenericTable implements IEditCoursePanel{
    
    private static final long serialVersionUID = -899228555149893619L;
    
    private final JButton confirm;
    private final JButton addCoach;
    private final JButton removeCoach;
    private final JComboBox<String> coaches;
    private final JScrollPane panelList;
    private IAddCourseController observer;
    private final EssentialPanelCourse principalPanel;
    
    public EditCoursePanel(final String path) {
        super(new Object[]{"NOME","COGNOME","ID"}, path);
        this.confirm=new JButton("Conferma");
        this.addCoach=new JButton("Aggiungi Coach");
        this.removeCoach=new JButton("Rimuovi coach");
        this.removeCoach.setEnabled(false);
        this.addCoach.setEnabled(false);
        
        this.principalPanel=new EssentialPanelCourse(path);
        
        this.coaches=new JComboBox<String>();
        this.coaches.setPreferredSize(new Dimension(250,20));
        
        this.panelList=new JScrollPane(table);
        
        this.panelList.setPreferredSize(new Dimension(250, 100));
        final Border border = BorderFactory.createLineBorder(Color.BLACK);
        this.panelList.setBorder(BorderFactory.createTitledBorder(border,"Insegnanti del corso"));
        
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.getColumnModel().getColumn(0).setPreferredWidth(60);
        this.table.getColumnModel().getColumn(1).setPreferredWidth(60);
        this.table.getColumnModel().getColumn(2).setPreferredWidth(15);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbcPanelPrinc=new GridBagConstraints();
        
        this.add(principalPanel,gbcPanelPrinc);
        
        gbcPanelPrinc.gridx++;
        this.add(createSecondPanel(),gbcPanelPrinc);
        
        this.setHandlers();
        
        UtilityClass.setListListenerTable(this.table, this.removeCoach);
    }
    
    public void showData(final ICourse course,final List<IEmployee> employees){
        this.principalPanel.setCourseName(course.getCourseName());
        this.principalPanel.setCourseColor(course.getCourseColor());
        this.principalPanel.setCoursePrice(course.getCoursePrice());
        this.principalPanel.setCourseMaxMember(course.getMaxMembers());
        this.coaches.setModel(new DefaultComboBoxModel<String>(UtilityClass.createComboBoxValues(employees)));
        this.addCoach.setEnabled(coaches.getModel().getSize()>0);
        ((EditCourseController)this.observer).formTable();
    }
    
    
    private Background createSecondPanel(){
        final Background secondPanel=new Background(this.getBackgroundPath());
        secondPanel.setLayout(new GridBagLayout());
        final Insets insets=new Insets(6, 8, 6, 8);
        
        final JLabel lblCoaches=new JLabel("Insegnanti:");
        final GridBagConstraints gbclblCoaches= new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,CENTER,NONE,insets,1,1);
        secondPanel.add(lblCoaches,gbclblCoaches);
         
        final GridBagConstraints gbcComboCoaches= new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,CENTER,NONE,insets,1,1);
        secondPanel.add(this.coaches,gbcComboCoaches);
         
        final GridBagConstraints gbcAddBtn= new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,CENTER,NONE,insets,1,1);
        secondPanel.add(this.addCoach,gbcAddBtn);
         
        final GridBagConstraints gbcListPan= new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,CENTER,NONE,insets,1,1);
        secondPanel.add(this.panelList,gbcListPan);
         
        final GridBagConstraints gbcSaveBtn= new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,CENTER,NONE,insets,1,1);
        secondPanel.add(this.confirm,gbcSaveBtn);
         
        final GridBagConstraints gbcRemoveBtn= new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,NORTH,NONE,insets,1,1);
        secondPanel.add(this.removeCoach,gbcRemoveBtn);
        
        return secondPanel;
    }
    
    @Override
    public void attachViewObserver(final IAddCourseController observer) {
        this.observer=observer;
    }
    
    private void setHandlers(){
        addCoach.addActionListener(e->((EditCourseController) this.observer).editCourseCmd(principalPanel.getCourseName(),this.principalPanel.getButtonColor(),this.principalPanel.getCoursePrice(),this.principalPanel.getCourseMaxMember()));
        removeCoach.addActionListener(e-> ((EditCourseController) this.observer).addCoachCmd(coaches.getSelectedIndex()));
        confirm.addActionListener(e->((EditCourseController) this.observer).removeCoachCmd(this.table.getSelectedRow()));
    }
    
}
