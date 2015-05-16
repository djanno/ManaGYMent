package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JTable;

import model.gym.ICourse;
import model.gym.members.IGymMember;

public final class UtilityClass{
    
//    Suppresses default constructor, ensuring non-instantiability
    private UtilityClass(){
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
     
    public static void setListListenerTable(final JTable table,final JButton...enableButton){
        table.getSelectionModel().addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()) {
                final boolean rowSelected = table.getSelectedRow() != -1;
                for(final JButton button:enableButton){
                    button.setEnabled(rowSelected);
                }
            }
        });
    }
    
    public static <X> List<X> defend(List<X> list){
        return new ArrayList<>(list);
    }
}
