package view.panels;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * This class model a concept of a generic table (substantially is wrapper for JTable) 
 * @author simone
 *
 */
public class GenericTable extends Background implements IGenericTable{

    private static final long serialVersionUID = 3319757418741439152L;

    private static final Object[][] INIT_DATA = new Object[][] {};

    protected final JTable table;

    /**
     * @param headers
     *          the table's headers
     * @param path
     *          the image background path
     */
    public GenericTable(final Object[] headers, final String path) {
        super(path);
        this.table = new JTable(new ManagymentTableModel(INIT_DATA, headers));
    }

    public void addDataRow(final Object[] row) {
        ((ManagymentTableModel) this.table.getModel()).addRow(row);
    }

    public void refreshTable() {
        ((ManagymentTableModel) this.table.getModel()).getDataVector().clear();
        ((ManagymentTableModel) this.table.getModel()).fireTableDataChanged();
    }

    /**
     * @author simone
     * The table model of a wrapped table 
     */
    public class ManagymentTableModel extends DefaultTableModel {

        private static final long serialVersionUID = -1466761511787501273L;

        public ManagymentTableModel(final Object[][] data, final Object[] headers) {
            super(data, headers);
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            if (this.getRowCount() > 0) {
                return this.getValueAt(0, columnIndex).getClass();
            }

            return Object.class;

        }

        @Override
        public boolean isCellEditable(final int row, final int column) {
            return false;
        }

    }

}
