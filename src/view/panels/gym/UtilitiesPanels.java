package view.panels.gym;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import model.gym.ICourse;
import model.gym.members.IGymMember;

public final class UtilitiesPanels{
    
//    Suppresses default constructor, ensuring non-instantiability
    private UtilitiesPanels(){
    };
    
    public static <X> String[] createComboBoxValues(final List<X> list){
        String[] comboBoxValues=new String[list.size()];
        if(!list.isEmpty()){
            if(list.get(0) instanceof ICourse){
                comboBoxValues=list.stream().map(x->((ICourse)x).getCourseName()).collect(Collectors.toList()).toArray(comboBoxValues);
            }else if(list.get(0) instanceof IGymMember){
                comboBoxValues=list.stream().map(x->((IGymMember)x).alternativeToString()).collect(Collectors.toList()).toArray(new String[list.size()]);
            }  
        }
        return comboBoxValues;    
    }
     
    public static void setListListenerTable(final DefaultTableModel tableModel,final JTable table,final JButton...enableButton){
        final ListSelectionModel listSelectionModel = table.getSelectionModel();
        listSelectionModel.addListSelectionListener(e->{
            for(final JButton button:enableButton){
                button.setEnabled(!listSelectionModel.isSelectionEmpty());
            }
        });
     }   
    
}
