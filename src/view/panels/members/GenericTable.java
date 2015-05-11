package view.panels.members;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import view.Background;

public class GenericTable extends Background{

    private static final long serialVersionUID = 3319757418741439152L;
    protected final JTable table;
    private static final Object[][] INIT_DATA = new Object[][]{};
    private final Object[] headers;

    public GenericTable(final Object[] headers, final String path) {
        super(path);
        this.headers=headers;
        this.table = new JTable(new ManagymentTableModel(INIT_DATA, this.headers));
    }
    
    public void addDataRow(final Object[] row) {
        ((ManagymentTableModel)this.table.getModel()).addRow(row);
    }

    public void refreshTable() {
        ((ManagymentTableModel)this.table.getModel()).getDataVector().clear();
        ((ManagymentTableModel) this.table.getModel()).fireTableDataChanged();
    }
        
        public class ManagymentTableModel extends DefaultTableModel {

            private static final long serialVersionUID = 8016046212790379885L;

                public ManagymentTableModel(final Object[][] data, final Object[] headers) {
                        super(data, headers);
                }
                
                @Override
                public Class<?> getColumnClass(final int columnIndex) {
                        if(this.getRowCount()>0) {
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

