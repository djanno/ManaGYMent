package view.panels.members;

import java.util.function.Predicate;

/**
 * Interface for {@link EnumFieldsCommon}.
 * 
 * @author Davide Borficchia
 *
 */

public interface IFormField {

    /**
     * Gets the field's name
     * 
     * @return the field's name
     */
    String getField();

    /**
     * 
     * Gets the field's predicate
     * 
     * @return the predicate
     */
    Predicate<String> getPred();
}
