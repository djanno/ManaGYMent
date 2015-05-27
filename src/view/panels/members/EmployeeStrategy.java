package view.panels.members;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * EmployeeStrategy
 * 
 * @author Davide Borficchia
 * 
 **/

public class EmployeeStrategy implements IFormStrategy{

    private FieldsCommon fieldsCommon;
    
    /**
     * Constructor
     */
    public EmployeeStrategy() {
        this.fieldsCommon=new FieldsCommon();
    }
    
    public enum EnumFieldsEmployee implements IFormField{
        
        SALARIO("Salario"),
        CREDITO("Credito");
                
        private String fieldName;
        private Predicate<String> pred;
        
        private EnumFieldsEmployee(final String fieldName){
            this.fieldName = fieldName;
        }

        @Override
        public String getField() {
            return this.fieldName;
        }
        
        @Override
        public Predicate<String> getPred() {
            return this.pred;
        }
       
    }
    
    @Override
    public List<IFormField> getFields() {
    	final List<IFormField> list= new ArrayList<IFormField>(fieldsCommon.getFields());
        list.addAll(Arrays.asList(EnumFieldsEmployee.values()));
        return list;
    }
}
