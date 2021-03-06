package view.panels.members;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * SubscriberStrategy
 * 
 * @author Davide Borficchia
 * 
 **/

public class SubscriberStrategy implements IFormStrategy {

    private FieldsCommon fieldsCommon;

    /**
     * Constructor
     */
    public SubscriberStrategy() {
        this.fieldsCommon = new FieldsCommon();
    }

    public enum EnumFieldsSubscriber implements IFormField {

        DATA_SCAD("Data scadenza"), FEE("Somma da pagare");

        private String fieldName;
        private Predicate<String> pred;

        private EnumFieldsSubscriber(final String fieldName) {
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
        final List<IFormField> list = new ArrayList<IFormField>(fieldsCommon.getFields());
        list.addAll(Arrays.asList(EnumFieldsSubscriber.values()));
        return list;
    }
}
