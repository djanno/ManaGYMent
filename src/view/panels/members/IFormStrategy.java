package view.panels.members;

import java.util.List;

/**
 * Interface for {@link EmployeeStrategy, @linkSubscriberStrategy}.
 * 
 * @author Davide Borficchia
 *
 */

public interface IFormStrategy {

    /**
     * 
     * Gets the fields of enumerator
     * 
     * @return the list of fields
     */
    List<IFormField> getFields();
}
