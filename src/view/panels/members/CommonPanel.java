package view.panels.members;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * CommonPanel
 * 
 * @author Davide Borficchia
 *
 */

public class CommonPanel extends JPanel implements ICommonPanel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private final Map<IFormField, JTextField> map = new HashMap<>();

    /**
     * Constructor
     */
    public CommonPanel() {
        this.setLayout(new GridBagLayout());
        final IFormStrategy strat = new FieldsCommon();
        final List<IFormField> fields = strat.getFields();
        GridBagConstraints limits = new GridBagConstraints();
        limits.gridx = 0;
        limits.gridy = 0;
        limits.weightx = 0.33;
        limits.insets.top = 5;
        limits.insets.left = 5;

        limits.fill = GridBagConstraints.HORIZONTAL;

        for (final IFormField f : fields) {
            this.add(new JLabel(f.getField()), limits);
            limits.gridx++;

            map.put(f, new JTextField(20));
            this.add(map.get(f), limits);

            limits.gridy++;
            limits.gridx = 0;
        }

        limits.fill = GridBagConstraints.HORIZONTAL;
        limits.gridwidth = 2;
    }

    @Override
    public void setMap(final IFormField field, final String value) {
        this.map.get(field).setText(value);
    }

    @Override
    public Map<IFormField, String> getMapToPass() {
        final Map<IFormField, String> mapToPass = new HashMap<>();
        for (final IFormField f : map.keySet()) {
            mapToPass.put(f, map.get(f).getText().trim());
        }
        return mapToPass;
    }
}
