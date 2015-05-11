package controller.panels.home;

import java.util.List;

import model.gym.members.IEmployee;

public interface ISetCalendarObserver {

	void loadData();

	List<IEmployee> loadCoachesByCourseName(final String courseName);
	
	void formTable();

	void addPairInHoursCmd(final String courseName, final String employee,
			final Integer hourFrom, final Integer hourTo, final  Integer openingTime,
			final Integer closingTime);

	void removePairInHourCmd(final Integer time, final String courseName, final String fiscalCode);

	void endCmd(final Boolean isOpen, final Integer openingTime, final Integer closingTime);

	
}
