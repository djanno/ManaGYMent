package view.panels.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import model.gym.GymCalendar.DaysOfWeek;
import model.gym.ICourse;
import view.panels.GenericTable;
import controller.panels.home.IHomePanelController;

/**
 * The home view of the application. It is used to display the weekly schedule of the logged user's gym.
 * @author Federico Giannoni
 *
 */
public class HomePanel extends GenericTable implements IHomePanel, ActionListener {

    private static final long serialVersionUID = 7596892298271249519L;

    private static final String[] HEADERS = { "", DaysOfWeek.LUNEDI.getName(), DaysOfWeek.MARTEDI.getName(), DaysOfWeek.MERCOLEDI.getName(),
            DaysOfWeek.GIOVEDI.getName(), DaysOfWeek.VENERDI.getName(), DaysOfWeek.SABATO.getName(), DaysOfWeek.DOMENICA.getName() };
    private static final int HEADER_HEIGHT = 40;
    private static final int ROW_HEIGHT = 20;

    private IHomePanelController observer;

    /**
     * Constructs a default home panel.
     * @param path the path to the image to be used as background.
     */
    public HomePanel(final String path) {
        super(HEADERS, path);
        this.setLayout(new BorderLayout());
        this.table.setTableHeader(new JTableHeader(this.table.getColumnModel()) {

            private static final long serialVersionUID = 1L;

            @Override
            public Dimension getPreferredSize() {
                final Dimension d = super.getPreferredSize();
                d.height = HEADER_HEIGHT;
                return d;
            }
        });
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.setRowSelectionAllowed(true);
        this.table.setColumnSelectionAllowed(false);
        this.table.setDefaultRenderer(CoursesWrapper.class, new CoursesWrapperRenderer());

        for (int i = 1; i < this.table.getColumnCount(); i++) {
            final TableColumn tc = this.table.getColumnModel().getColumn(i);
            tc.setHeaderRenderer(new ButtonHeader(this.table, this.table.getColumnName(i), this));
        }

        this.table.setRowHeight(ROW_HEIGHT);

        final JScrollPane scroll = new JScrollPane(this.table);
        this.add(scroll, BorderLayout.CENTER);
    }

    @Override
    public void attachObserver(final IHomePanelController observer) {
        this.observer = observer;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final String command = e.getActionCommand();
        this.observer.cmdEditDaySchedule(command);
    }

    /**
     * A wrapper class that wraps a list of {@link ICourse}.
     * This class is used to store in a JTable cell the schedule of a specific hour in a specific day.
     * Through this data type in fact, it's possible to assign to the JTable containing the weekly schedule of
     * the gym, a CellRenderer that renders this data type into a visible representation.
     * @author Federico Giannoni
     *
     */
    public static class CoursesWrapper {

        private final List<ICourse> courses;
        private final boolean opened;

        /**
         * Constructs a {@link CoursesWrapper} from a list of {@link ICourse}.
         * @param courses the courses contained in a specific hour of a specific day that needs to be wrapped.
         * @param opened a boolean that specifies if the gym is opened (true) or not (false) at such specific hour of such specific day. 
         */
        public CoursesWrapper(final List<ICourse> courses, final boolean opened) {
            this.courses = new ArrayList<ICourse>(courses);
            this.opened = opened;
        }

        /**
         * Returns the list of courses wrapped in the class.
         * @return the list of courses wrapper in the class.
         */
        public List<ICourse> getCourses() {
            return new ArrayList<ICourse>(this.courses);
        }

        /**
         * Returns if the gym is opened or not in the hour whose schedule is wrapped by the class.
         * @return true if the gym is opened, false otherwise.
         */
        public boolean isOpened() {
            return this.opened;
        }
    }

    /**
     * A renderer that displays the {@link CoursesWrapper} data type contained by the cells of the table in the
     * {@link HomePanel} through a panel and colored labels.
     * @author Federico Giannoni
     *
     */
    class CoursesWrapperRenderer extends JPanel implements TableCellRenderer {

        private static final long serialVersionUID = 7575007678292078570L;

        private static final int MAX_COLORS_PER_ROW = 3;

        /**
         * Constructs a {@link CoursesWrapperRenderer}.
         */
        public CoursesWrapperRenderer() {
            super();
            this.setLayout(new GridBagLayout());
            this.setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(final JTable table, final Object object, final boolean isSelected, final boolean hasFocus,
                final int row, final int col) {

            final CoursesWrapper courses = (CoursesWrapper) object;

            this.addCoursesToTable(courses.getCourses(), courses.isOpened());

            if (isSelected) {
                this.setBorder(BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground()));
            } else {
                this.setBorder(BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground()));
            }

            return this;
        }

        /**
         * Cleans the panel to be used as the view for the {@link CoursesWrapper} data type and then fills it with
         * colored labels each representing a course.
         * @param courses the courses to be represented in the table cell.
         * @param opened specifies if the gym is opened or not at the time whose schedule is wrapped by the {@link CoursesWrapper} to be rendered.
         * 				 If false, a black label will be used to represent such information.
         */
        private void addCoursesToTable(final List<ICourse> courses, final boolean opened) {

            this.removeAll();

            int gridx = 0;
            int gridy = 0;

            JLabel label = null;

            final double weightx = (double) CoursesWrapperRenderer.MAX_COLORS_PER_ROW / 100;
            final double weighty = ((double) courses.size() / CoursesWrapperRenderer.MAX_COLORS_PER_ROW) / 100;

            if (courses.isEmpty()) { 

                if (opened) {
                    label = this.createLabel(Color.WHITE);
                }

                else {
                    label = this.createLabel(Color.BLACK);
                }

                this.add(label, new GridBagConstraints(gridx, gridy, 1, 1, weightx, weighty, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(2, 2, 2, 2), 0, 0));
            }

            for (final ICourse course : courses) {

                label = this.createLabel(course.getCourseColor());

                this.add(label, new GridBagConstraints(gridx, gridy, 1, 1, weightx, weighty, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(2, 2, 2, 2), 0, 0));

                gridx++;

                if (gridx == CoursesWrapperRenderer.MAX_COLORS_PER_ROW) {
                    gridx = 0;
                    gridy++;
                }

            }
        }

        /**
         * Builds the label with a specified color (the color is the color of a course wrapped by the {@link CoursesWrapperRenderer} that is currently being rendered).
         * @param color specifies the color of the label that is going to be created.
         * @return the JLabel with the specified color.
         */
        private JLabel createLabel(final Color color) {
            final JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(5, 5));
            label.setBackground(color);
            label.setOpaque(true);

            return label;
        }

    }

    /**
     * A renderer for the header of the JTable contained in the {@link HomePanel}. It is used to make each element of the header of such table
     * a clickable button. The button will be used to open a perspective that allows the user to edit the content of the specified column of the table.
     * @author Federico Giannoni
     *
     */
    class ButtonHeader extends JButton implements TableCellRenderer, MouseListener {

        private static final long serialVersionUID = -2671561830772836025L;

        private final JTable table;
        private boolean click;
        private int column;

        /**
         * Constructs a {@link ButtonHeader} for the specified JTable.
         * @param table the table that requires the header renderer.
         * @param text the text that has to be displayed in a header cell.
         * @param listener the action listener for the {@link ButtonHeader}.
         */
        public ButtonHeader(final JTable table, final String text, final ActionListener listener) {
            super(text);
            this.table = table;
            this.addActionListener(listener);
            this.click = false;

            if (this.table != null && this.table.getTableHeader() != null) {
                this.table.getTableHeader().addMouseListener(this);
            }
        }

        @Override
        public Component getTableCellRendererComponent(final JTable table, final Object object, final boolean isSelected, final boolean hasFocus,
                final int row, final int column) {

            if (table != null && table.getTableHeader() != null) {
                this.setForeground(table.getTableHeader().getForeground());
                this.setBackground(table.getTableHeader().getBackground());
            }

            this.column = column;
            this.setActionCommand(DaysOfWeek.values()[column - 1].getName()); 
            this.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            return this;
        }

        /**
         * Handles the click event. Since by default, the header of JTable is only displayed and it's impossible for
         * the user to interact with its content, it's possible to make the buttons in the {@link ButtonHeader} clickable
         * only by registering the clicks on the renderer and actually clicking the buttons that it contains via code.
         * @param e the mouse event that has been registered on the table header.
         */
        private void handleClick(final MouseEvent e) {
            if (this.click) {
                click = false;

                final JTableHeader header = (JTableHeader) e.getSource();
                final int columnIndex = header.getTable().getColumnModel().getColumnIndexAtX(e.getX());

                if (e.getClickCount() == 1 && columnIndex > 0 && column == columnIndex) {
                    ((ButtonHeader) header.getColumnModel().getColumn(columnIndex).getHeaderRenderer()).doClick();
                }
            }
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            this.handleClick(e);
        }

        @Override
        public void mouseEntered(final MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(final MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed(final MouseEvent e) {
            this.click = true;
        }

        @Override
        public void mouseReleased(final MouseEvent e) {

        }

    }

}
