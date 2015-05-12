package controller.panels.gym;

public interface IGymPanelController {
	
	void loadCoursesTable();
	
	void cmdAddCourse();
	
	void cmdEditCourse(final int index);
	
	void cmdDeleteCourse(final int index);
	
}
