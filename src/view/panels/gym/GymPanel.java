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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

import utility.UtilityClass;
import view.panels.Background;
import view.panels.GenericTable;
import controller.panels.gym.IGymPanelController;

/**
 * The view that displays the data of the logged user's gym.
 * @author Federico Giannoni
 *
 */
public class GymPanel extends GenericTable implements IGymPanel {

    private static final long serialVersionUID = -3885713318419080990L;

    private static final String[] COLUMNS = { "Corso", "Colore", "Prezzo (abbonamento giornaliero)" };
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD + Font.PLAIN, 20);

    private IGymPanelController observer;

    private final Background top;
    private final IncomePanel incomePanel;

    private final JButton addBtn = new JButton("Aggiungi");
    private final JButton editBtn = new JButton("Dettagli");
    private final JButton delBtn = new JButton("Elimina");

    /**
     * Constructs a panel.
     * @param path the path to the image to be used as the background of the panel.
     */
    public GymPanel(final String path) {
        super(COLUMNS, path);

        this.setLayout(new GridBagLayout());

        this.top = new Background(path);
        this.top.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.incomePanel = new IncomePanel(path);

        final JPanel bottom = new JPanel(new BorderLayout());

        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.setRowSelectionAllowed(true);
        this.table.setColumnSelectionAllowed(false);
        this.table.setDefaultRenderer(Color.class, new CourseColorRenderer());

        UtilityClass.setListListenerTable(this.table, this.delBtn, this.editBtn);

        final JScrollPane scrollpane = new JScrollPane(this.table);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        bottom.add(scrollpane, BorderLayout.CENTER);

        final Background btnPanel = new Background(path);
        btnPanel.setLayout(new GridLayout(0, 1));

        this.editBtn.setEnabled(false);
        this.delBtn.setEnabled(false);

        btnPanel.add(this.addBtn);
        btnPanel.add(this.editBtn);
        btnPanel.add(this.delBtn);

        bottom.add(btnPanel, BorderLayout.EAST);

        this.add(this.top, new GridBagConstraints(0, 0, 1, 1, 1, 0.15, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 10, 0),
                0, 0));
        this.add(this.incomePanel, new GridBagConstraints(0, 1, 1, 1, 1, 0.6, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0,
                10, 0), 0, 0));
        this.add(bottom, new GridBagConstraints(0, 2, 1, 1, 1, 0.25, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 0, 0), 0,
                0));

        this.setHandlers();
    }

    public void setHeader(final String header) {
        final JLabel label = new JLabel(header);
        label.setFont(HEADER_FONT);
        this.top.add(label);
    }

    @Override
    public IncomePanel getIncomePanel() {
        return this.incomePanel;
    }

    @Override
    public void attachObserver(final IGymPanelController observer) {
        this.observer = observer;
    }

    /**
     * Adds the action listeners to the panel and defines their behavior.
     */
    private void setHandlers() {
        this.addBtn.addActionListener(e -> this.observer.cmdAddCourse());
        this.editBtn.addActionListener(e -> this.observer.cmdEditCourse(this.table.getSelectedRow()));
        this.delBtn.addActionListener(e -> this.observer.cmdDeleteCourse(this.table.getSelectedRow()));
    }

    /**
     * The view that displays the data regarding the income of the logged user's gym.
     * @author Federico Giannoni
     * @author Davide Borficchia
     */
    public class IncomePanel extends GenericTable {

        private static final long serialVersionUID = 1L;

        public IncomePanel(final String path) {
            super(new String[] { "Mese", "Guadagno" }, path);
            this.setLayout(new BorderLayout());
            this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            this.table.getTableHeader().setReorderingAllowed(false);
            this.table.setRowSelectionAllowed(true);
            this.table.setColumnSelectionAllowed(false);
            this.add(new JScrollPane(this.table), BorderLayout.NORTH);
        }

    }

    /**
     * A renderer used to display the Color data type in the JTable containing courses, that is contained in
     * the {@link GymPanel}.
     * @author Federico Giannoni
     *
     */
    class CourseColorRenderer extends JLabel implements TableCellRenderer {

        private static final long serialVersionUID = -4877549038078530092L;

        public CourseColorRenderer() {
            super();
            this.setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(final JTable table, final Object color, final boolean isSelected, final boolean hasFocus,
                final int row, final int col) {

            final Color lblColor = (Color) color;
            this.setBackground(lblColor);

            if (isSelected) {
                this.setBorder(BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground()));
            } else {
                this.setBorder(BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground()));
            }
            return this;
        }
    }
}
