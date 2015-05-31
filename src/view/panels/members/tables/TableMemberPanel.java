package view.panels.members.tables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import utility.UtilityClass;
import view.panels.GenericTable;
import view.panels.members.IFormStrategy;
import controller.panels.members.tables.IAbstractTableMemberController;

/**
 * This class display a not editable table for a generic gym's member
 * 
 * @author simone
 *
 */
public class TableMemberPanel extends GenericTable {

    private static final long serialVersionUID = 1660901668041854994L;
    private final JPanel pNorth;
    private final JPanel pSouth;
    private final JScrollPane tableContainer;
    private final IFormStrategy strategy;
    private final JButton edit;
    private final JButton remove;
    private final JButton add;
    private final JButton pay;
    private final JRadioButton[] buttonSearch;
    private final ButtonGroup bg;
    private final JLabel searchL;
    private final JTextField searchT;
    private final TableRowSorter<TableModel> sorter;
    private IAbstractTableMemberController observer;

    /**
     * Construct a panel that contain a not editable JTable that can be filtered
     * 
     * @param strategyTable
     *            the strategy to create filter option and established table
     *            header
     * @param path
     *            the image background path
     */
    public TableMemberPanel(final IFormStrategy strategyTable, final String path) {
        super(strategyTable.getFields().stream().map(e -> e.getField()).toArray(), path);
        this.setLayout(new BorderLayout());
        this.strategy = strategyTable;

        this.pNorth = new JPanel();
        this.pSouth = new JPanel();

        this.edit = new JButton("Modifica");
        this.edit.setEnabled(false);
        this.remove = new JButton("Cancella");
        this.remove.setEnabled(false);
        this.add = new JButton("Aggiungi");
        this.pay = new JButton("Salda conto");
        this.pay.setEnabled(false);

        this.searchL = new JLabel("Search");
        this.searchT = new JTextField(20);

        this.bg = new ButtonGroup();
        buttonSearch = new JRadioButton[strategy.getFields().size()];
        for (int i = 0; i < strategy.getFields().size(); i++) {
            buttonSearch[i] = new JRadioButton(strategy.getFields().get(i).getField());
        }
        this.buttonSearch[0].setSelected(true);

        this.tableContainer = new JScrollPane(table);
        this.tableContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.setFillsViewportHeight(true);
        this.sorter = new TableRowSorter<TableModel>(table.getModel());
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.setRowSorter(sorter);
        table.setDefaultRenderer(Calendar.class, new TblRenderer());

        buildLayout();

        for (int i = 0; i < buttonSearch.length; i++) {
            buttonSearch[i].setActionCommand(Integer.toString(i));
        }

        this.searchT.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(final DocumentEvent e) {
                newFilter(bg.getSelection().getActionCommand());
            }

            public void insertUpdate(final DocumentEvent e) {
                newFilter(bg.getSelection().getActionCommand());
            }

            public void removeUpdate(final DocumentEvent e) {
                newFilter(bg.getSelection().getActionCommand());
            }
        });

        UtilityClass.setListListenerTable(this.table, this.remove, this.edit, this.pay);

        setHandler();

    }

    public void attachViewObserver(final IAbstractTableMemberController observer) {
        this.observer = observer;
    }

    /**
     * Build JPanel layout
     */
    private void buildLayout() {
        this.add(BorderLayout.CENTER, tableContainer);
        pSouth.setLayout(new FlowLayout());
        for (final JRadioButton radio : buttonSearch) {
            bg.add(radio);
        }
        fillUp(pNorth, add, remove, edit, pay);
        fillUp(pSouth, searchL, searchT);
        for (final JRadioButton radio : buttonSearch) {
            pSouth.add(radio);
        }
        this.add(BorderLayout.SOUTH, pSouth);
        this.add(BorderLayout.NORTH, pNorth);
    }

    /**
     * fill the panel with the component in JComponent[]
     * 
     * @param panel
     *            the panel to fill
     * @param component
     *            the component to add
     */
    private void fillUp(final JPanel panel, final JComponent... component) {
        for (final JComponent comp : component) {
            panel.add(comp);
        }
    };

    /**
     * 
     * @param command
     *            the number of column that you want to filter in String format
     */
    private void newFilter(final String command) {
        final int indices = Integer.parseInt(command);
        final RowFilter<TableModel, Integer> rf = RowFilter.regexFilter(searchT.getText(), indices);
        this.sorter.setRowFilter(rf);
    }

    /**
     * Set the handler for button
     */
    private void setHandler() {
        this.add.addActionListener(e -> this.observer.addMemberCmd());
        this.remove.addActionListener(e -> this.observer.deleteMemberCmd(table.convertRowIndexToModel(table.getSelectedRow())));
        this.edit.addActionListener(e -> this.observer.editMemberCmd(table.convertRowIndexToModel(table.getSelectedRow())));
        this.pay.addActionListener(e -> this.observer.handlePaymentCmd(table.convertRowIndexToModel(table.getSelectedRow())));
    }

    /**
     * Renderer for JTable that color expiration date cell if it is expired
     * 
     * @author simone
     *
     */
    static class TblRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = -5256501248149933809L;

        public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean selected, final boolean focused,
                final int row, final int column) {
            if (value != null) {
                final Calendar c = (Calendar) value;
                final Date newDate = c.getTime();
                final Date dora = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY).getTime();
                if (newDate.before(dora)) {
                    setBackground(Color.RED);
                } else {
                    setBackground(Color.WHITE);
                }
                if (selected) {
                    setForeground(table.getSelectionForeground());
                    setBackground(table.getSelectionBackground());
                }
                final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
                setText(df.format(c.getTime().getTime()).toString());
            }

            setBorder(null);

            setForeground(Color.black);

            return this;

        }
    }

}
