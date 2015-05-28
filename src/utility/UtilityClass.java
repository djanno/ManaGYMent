package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JTable;

/**
 * @author simone
 * This class respond to the principle of non-instantiability class like {@link java.util.Arrays} or other class in Java SE Library, that have a lot of static methods
 */
public final class UtilityClass {

    // Suppresses default constructor, ensuring non-instantiability
    private UtilityClass() {
    };

    /**
     * @param list
     *          a generic list
     * @param mapper 
     *          the map function that trasform the generic element X in String         
     * @return
     *          a String[] used to fill a comboBoxValues 
     */
    public static <X> String[] createComboBoxValues(final List<X> list, Function<? super X, ? extends String> mapper) {
        String[] comboBoxValues = new String[list.size()];
        if (!list.isEmpty()) {
                comboBoxValues = list.stream().map(mapper).collect(Collectors.toList()).toArray(comboBoxValues);
        }
        return comboBoxValues;
    }

    /**
     * set the buttons not editable if no one row is selected in table
     * 
     * @param table
     *          the selected/no selected table
     * @param enableButton
     *          the buttons that you want enable/disable
     */
    public static void setListListenerTable(final JTable table, final JButton... enableButton) {
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                final boolean rowSelected = table.getSelectedRow() != -1;
                for (final JButton button : enableButton) {
                    button.setEnabled(rowSelected);
                }
            }
        });
    }

    /**
     * 
     * @param list
     *          the list that you want defend
     * @return 
     *          a defend copy of a list
     */
    public static <X> List<X> defend(final List<X> list) {
        return new ArrayList<>(list);
    }
}
