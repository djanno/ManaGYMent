package view.panels.gym;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import view.panels.Background;
import view.panels.GenericTable;
import controller.panels.gym.IGymPanelController;

public class GymPanel extends GenericTable implements IGymPanel, ActionListener {

	private static final long serialVersionUID = -3885713318419080990L;

	private static final String[] COLUMNS = {"Nome", "Colore", "Prezzo"};
	private static final Font HEADER_FONT = new Font("Arial", Font.BOLD + Font.PLAIN, 20);
	//private final JTable table;
	//private Object[][] data;
	
	private IGymPanelController observer;
	
	private final Background top;
	
	private final JButton addBtn = new JButton("Aggiungi");
	private final JButton editBtn = new JButton("Dettagli");
	private final JButton delBtn = new JButton("Elimina");
	
	public GymPanel(final String path) {
		super(COLUMNS, path);
		
		this.setLayout(new GridBagLayout());
		
		this.top = new Background(path);
		this.top.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		final JPanel bottom = new JPanel(new BorderLayout());

		//this.table = new JTable(new CoursesManagerTableModel(this.data, GymPanel.COLUMNS));
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.setRowSelectionAllowed(true);
		this.table.setColumnSelectionAllowed(false);
		this.table.setDefaultRenderer(Color.class, new CourseColorRenderer());
		
		this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(final ListSelectionEvent e) {
				
				if(!e.getValueIsAdjusting()) {
					final boolean rowSelected = table.getSelectedRow() != -1;
					editBtn.setEnabled(rowSelected);
					delBtn.setEnabled(rowSelected);
				}
				
			}
			
		});

		final JScrollPane scrollpane = new JScrollPane(this.table);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		bottom.add(scrollpane, BorderLayout.CENTER);
		
		final Background btnPanel = new Background(path);
		btnPanel.setLayout(new GridLayout(0, 1));

		this.editBtn.addActionListener(this);
		this.addBtn.addActionListener(this);
		this.delBtn.addActionListener(this);
		this.editBtn.setEnabled(false);
		this.delBtn.setEnabled(false);
		
		btnPanel.add(this.addBtn);
		btnPanel.add(this.editBtn);
		btnPanel.add(this.delBtn);
		
		bottom.add(btnPanel, BorderLayout.EAST);
		
		this.add(this.top, new GridBagConstraints(0, 0, 1, 1, 1, 0.15, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 10, 0), 0, 0));
		this.add(new JPanel(), new GridBagConstraints(0, 1, 1, 1, 1, 0.6, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 10, 0), 0, 0));
		this.add(bottom, new GridBagConstraints(0, 2, 1, 1, 1, 0.25, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 0, 0), 0, 0));
	}
	
	public void setHeader(final String header) {
		final JLabel label = new JLabel(header);
		label.setFont(HEADER_FONT);
		this.top.add(label);
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object source = e.getSource();
		
		if(source == this.addBtn) {
			this.observer.cmdAddCourse();
		}
		
		else if(source == this.editBtn) {
			this.observer.cmdEditCourse(this.table.getSelectedRow());
		}
		
		else if(source == this.delBtn) {
			this.observer.cmdDeleteCourse(this.table.getSelectedRow());
		}
	}
	
	@Override
	public void attachObserver(final IGymPanelController observer) {
		this.observer = observer;
	}

	public class CourseColorRenderer extends JLabel implements TableCellRenderer {
		
		private static final long serialVersionUID = -4877549038078530092L;

		public CourseColorRenderer() {
			super();
			this.setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(final JTable table, final Object color,
				final boolean isSelected, final boolean hasFocus, final int row, final int col) {
			
			final Color lblColor = (Color) color;
			this.setBackground(lblColor);
			
			if(isSelected) {
				this.setBorder(BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground()));
			}
			else {
				this.setBorder(BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground()));
			}
			return this;
		}
	}
}
