package view.panels.home;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import model.gym.ICourse;

public class CoursesWrapperRenderer extends JPanel implements TableCellRenderer {

	private static final long serialVersionUID = 7575007678292078570L;
	
	private static final int MAX_COLORS_PER_ROW = 3;
	
	public CoursesWrapperRenderer() {
		super();
		this.setLayout(new GridBagLayout());
		this.setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object object,
			final boolean isSelected, final boolean hasFocus, final int row, final int col) {
		
		final CoursesWrapper courses = (CoursesWrapper) object;
		
		this.addCoursesToTable(courses.getCourses(), courses.isOpened());
		
		
		if(isSelected) {
			this.setBorder(BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground()));
		}
		else {
			this.setBorder(BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground()));
		}
		
		return this;
	}
	
	private void addCoursesToTable(final List<ICourse> courses, final boolean opened) {
		
		this.removeAll();
		
		int gridx = 0;
		int gridy = 0;
		
		JLabel label = null;
		
		final double weightx = (double) CoursesWrapperRenderer.MAX_COLORS_PER_ROW / 100;
		final double weighty = ((double) courses.size() / CoursesWrapperRenderer.MAX_COLORS_PER_ROW) / 100;
		
		if(courses.isEmpty()) { //se non ci sono corsi in quell'ora, la palestra è chiusa in quell'ora -> lbl nero.

			if(opened) {
				label = this.createLabel(Color.WHITE);
			}
			
			else {
				label = this.createLabel(Color.BLACK);
			}
			
			this.add(label, new GridBagConstraints(gridx, gridy, 1, 1, weightx, weighty, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
		}
		
		for(final ICourse course : courses) {

			label = this.createLabel(course.getCourseColor());
			
			this.add(label, new GridBagConstraints(gridx, gridy, 1, 1, weightx, weighty, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
			
			gridx++;
			
			if(gridx == CoursesWrapperRenderer.MAX_COLORS_PER_ROW) {
				gridx = 0;
				gridy++;
			}
			
		}
	}
	
	private JLabel createLabel(final Color color) {
		final JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(5, 5));
		label.setBackground(color);
		label.setOpaque(true);
		
		return label;
	}

	public static class CoursesWrapper {
		
		private final List<ICourse> courses;
		private final boolean opened;
		
		public CoursesWrapper(final List<ICourse> courses, final boolean opened) {
			this.courses = new ArrayList<ICourse>(courses);
			this.opened = opened;
		}
		
		public List<ICourse> getCourses() {
			return new ArrayList<ICourse>(this.courses);
		}
		
		public boolean isOpened() {
			return this.opened;
		}
	}
}
