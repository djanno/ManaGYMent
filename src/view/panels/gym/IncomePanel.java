package view.panels.gym;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import view.panels.GenericTable;
import controller.panels.gym.IIncomePanelController;

public class IncomePanel extends GenericTable implements IIncomePanel{
	
	private IIncomePanelController observer;
	private final JScrollPane scrollPane; //mettilo come variabile locale non  come campo
	
	private static final long serialVersionUID = 1L;
	private static final String[] COLUMN_NAMES = {"Mese", "Guadagno"};
	
	public IncomePanel(final String path){
		super(COLUMN_NAMES, path);
		this.setLayout(new BorderLayout());
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.setRowSelectionAllowed(true);
		this.table.setColumnSelectionAllowed(false);
		this.scrollPane = new JScrollPane(this.table);
		this.add(this.scrollPane, BorderLayout.NORTH);
	}

	@Override
	public void attachObserver(IIncomePanelController observer) {
		this.observer = observer;		
	}

}
