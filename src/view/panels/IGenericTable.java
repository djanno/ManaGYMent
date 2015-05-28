package view.panels;

/**
 * @author simone
 * Defines the {@link GenericTable}
 */
public interface IGenericTable {
    
    /**
     * Adds a row in the table
     * @param row
     */
    void addDataRow(final Object[] row);
    
    /**
     * Reset the table
     */
    void refreshTable();
    
    
}
