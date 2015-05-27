package view.panels.gym;

import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.NONE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import view.panels.Background;

public class EssentialPanelCourse extends Background implements IEssentialPanelCourse{

    private static final long serialVersionUID = 8205150569897367407L;
    private static final int LONG_TEXT_FIELD=10;
    private final JTextField courseName;
    private final JTextField price;
    private final JTextField maxMember;
    private final JButton chooseColor;
    private final JButton chooseNColor;
    private  JDialog dialog;
    private final JColorChooser dialJColorChooser;
    
    public EssentialPanelCourse(final String path) {
    	super(path);
    	
        this.courseName=new JTextField(LONG_TEXT_FIELD);
        this.price=new JTextField(LONG_TEXT_FIELD);
        this.maxMember=new JTextField(LONG_TEXT_FIELD);
        
        this.chooseColor=new JButton("Scegli colore");
        this.chooseNColor=new JButton();
        
        this.dialog=new JDialog();
        this.dialJColorChooser=new JColorChooser();
        
        this.setLayout(new GridBagLayout());
        
        this.chooseColor.addActionListener(e->dialog.setVisible(true));
        
        this.dialog = JColorChooser.createDialog(this.chooseColor, "Pick a Color", true, this.dialJColorChooser, event->chooseNColor.setBackground(dialJColorChooser.getColor()), null); 
        
        final Insets insets=new Insets(6, 8, 6, 8);
        
        this.chooseNColor.setPreferredSize(new Dimension(30,30));
        this.chooseNColor.setEnabled(false);
        this.chooseNColor.setBackground(Color.WHITE);
        
        final JLabel lblCouseName=new JLabel("Nome Corso:");
        final GridBagConstraints gbcLblCourseName= new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,CENTER,NONE,insets,1,1);
        this.add(lblCouseName,gbcLblCourseName);
        
        final GridBagConstraints gbcTextCourseName= new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,CENTER,NONE,insets,1,1);
        this.add(courseName,gbcTextCourseName);
        
        final GridBagConstraints gbcBtnChooseColor= new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,CENTER,NONE,insets,1,1);
        this.add(chooseColor,gbcBtnChooseColor);
        
        final GridBagConstraints gbcBtnChooseNColor= new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,CENTER,NONE,insets,1,1);
        this.add(chooseNColor,gbcBtnChooseNColor);
        
        final JLabel lblPrice=new JLabel("Prezzo giornaliero:");
        final GridBagConstraints gbcLblPrice= new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,CENTER,NONE,insets,1,1);
        this.add(lblPrice,gbcLblPrice);
    
        final GridBagConstraints gbcTxtPrice= new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,CENTER,NONE,insets,1,1);
        this.add(price,gbcTxtPrice);
        
        final JLabel lblMaxMembers = new JLabel("Membri massimi:");
        final GridBagConstraints gbcLblMaxMembers= new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,CENTER,NONE,insets,1,1);
        this.add(lblMaxMembers,gbcLblMaxMembers);
    
        final GridBagConstraints gbcTxtMaxMembers= new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,CENTER,NONE,insets,1,1);
        this.add(maxMember,gbcTxtMaxMembers);
    }

    @Override
    public void setCourseName(final String courseName) {
        this.courseName.setText(courseName);
    }

    @Override
    public void setCoursePrice(final double price) {
        this.price.setText(Double.toString(price));
    }

    @Override
    public void setCourseColor(final Color color) {
        this.chooseNColor.setBackground(color);
    }

    @Override
    public void setCourseMaxMember(final int maxMember) {
        this.maxMember.setText(Integer.toString(maxMember));
    }

    @Override
    public String getCourseMaxMember() {
        return this.maxMember.getText();
    }

    @Override
    public String getCoursePrice() {
        return this.price.getText();
    }

    @Override
    public String getCourseName() {
        return this.courseName.getText();
    }

    @Override
    public Color getButtonColor() {
        return chooseNColor.getBackground();
    }
}
