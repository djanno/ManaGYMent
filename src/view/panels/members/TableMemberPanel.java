package view.panels.members;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import view.panels.GenericTable;
import view.panels.UtilitiesPanels;
import controller.panels.members.AbstractTableMemberController;

public class TableMemberPanel extends GenericTable implements IAbstractTablePanel{
    
    private static final long serialVersionUID = 1660901668041854994L;
    private final JPanel pNorth;
    private final JPanel pSouth;
    private final JScrollPane tableContainer;
    private final IFormStrategy strategy;
    private final JButton edit;
    private final JButton remove;
    private final JButton add;
    private final JRadioButton[] buttonSearch;
    private final ButtonGroup bg;
    private final JLabel searchL;
    private final JTextField searchT;
    private final TableRowSorter<TableModel> sorter;
    private AbstractTableMemberController observer;/* ci va l'interfaccia sia qui che nel metodo*/
    

    public TableMemberPanel(final IFormStrategy strategyTable, final String path) {     
        super(strategyTable.getFields().stream().map(e->e.getField()).toArray(),path);
        this.setLayout(new BorderLayout());
        this.strategy=strategyTable;
        
        this.pNorth=new JPanel();
        this.pSouth=new JPanel();
        
        this.edit=new JButton("Modifica");
        this.edit.setEnabled(false);
        this.remove=new JButton("Cancella");
        this.remove.setEnabled(false);
        this.add=new JButton("Aggiungi");
        
        this.searchL = new JLabel("Search");
        this.searchT = new JTextField(20);
        
        this.bg = new ButtonGroup();
        buttonSearch= new JRadioButton[strategy.getFields().size()];        
        for(int i=0;i<strategy.getFields().size();i++){
            buttonSearch[i]=new JRadioButton(strategy.getFields().get(i).getField());
        }
        this.buttonSearch[0].setSelected(true);

        this.tableContainer=new JScrollPane(table);
        this.tableContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.setFillsViewportHeight(true);
        this.sorter=new TableRowSorter<TableModel>(table.getModel());
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.setRowSorter(sorter);
        
        buildLayout();
        
        for(int i=0;i<buttonSearch.length;i++){
            buttonSearch[i].setActionCommand(Integer.toString(i));
        }
        
        this.searchT.getDocument().addDocumentListener(
                new DocumentListener() {
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
        
        UtilitiesPanels.setListListenerTable((DefaultTableModel)table.getModel(), table, remove, edit);
        
        setHandler();
       
    }

    
    public void attachViewObserver(final AbstractTableMemberController observer){
        this.observer=observer;
    }
    
    
    private void buildLayout() {
        this.add(BorderLayout.CENTER, tableContainer);
        pSouth.setLayout(new FlowLayout());
        for(final JRadioButton radio:buttonSearch){
            bg.add(radio);
        }
        riempi(pNorth,add,remove,edit);
        riempi(pSouth, searchL, searchT);
        for(final JRadioButton radio:buttonSearch){
            pSouth.add(radio);
        }
        this.add(BorderLayout.SOUTH, pSouth);  
        this.add(BorderLayout.NORTH, pNorth);
    }
    
    private void riempi(final JPanel p,final JComponent... c) {
        for (final JComponent comp : c) {
            p.add(comp);
        }
    };
    
    private void newFilter(final String command) {
        final int indices=Integer.parseInt(command);
        final RowFilter<TableModel,Integer> rf = RowFilter.regexFilter(searchT.getText(),indices);
        this.sorter.setRowFilter(rf);
    }
    
    private void setHandler(){
        this.add.addActionListener(e->this.observer.addMemberCmd());
        this.remove.addActionListener(e->this.observer.deleteMemberCmd(table.convertRowIndexToModel(table.getSelectedRow())));
        this.edit.addActionListener(e->this.observer.editMemberCmd(table.convertRowIndexToModel(table.getSelectedRow())));
    }

}
