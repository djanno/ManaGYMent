package test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

import exceptions.CourseIsFullException;
import model.gym.Course;
import model.gym.Gym;
import model.gym.GymCalendar;
import model.gym.GymCalendar.DaysOfWeek;
import model.gym.ICourse;
import model.gym.IGym;
import model.gym.IGymCalendar;

import model.gym.Schedule;
import model.gym.Schedule.Pair;
import model.gym.members.Employee;
import model.gym.members.IEmployee;
import model.gym.members.ISubscriber;
import model.gym.members.Subscriber;

public class TestGym {
	private static final double DELTA = 1e-15;
	
	@Test
	public void testGymCreation(){
		
		/*
		 * TESTING THE GYM CREATION
		 */
		final IGym gym = new Gym("nome");
		assertEquals("nome", gym.getGymName());
						
		/* 
		 * TESTING THE ADD COURSE
		 */
		final ICourse course = new Course("boxe", new Color(18), 5.0, 10);
		final List<ICourse> listCourses = new ArrayList<>();
		listCourses.add(course);
		gym.addCourse(course);
		assertEquals(listCourses, gym.getCourses());
		assertEquals(listCourses.get(0), course);
		
		/*
		 * TESTING ADD EMPLOYEE
		 */
		final IEmployee employee = new Employee("nome", "Cognome", "123456789012345","piazza la bomba e scappa",
				"123456789", "email@email.com", gym, 800.0);
		final List<IEmployee> listEmployees = new ArrayList<>();
		listEmployees.add(employee);	
		gym.addEmployee(employee);
		assertEquals(listEmployees, gym.getEmployees());
		
		/*
		 * TESTING ADD SUBSCRIBER
		 */
		final Calendar subscriptionDate = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
		final Calendar expirationDate = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
		expirationDate.set(2015, 10, 24);
		
		final ISubscriber subscriber = new Subscriber("nome", "Cognome", "123456789012345","piazza la bomba e scappa",
				"123456789", "email@email.com", gym, subscriptionDate, expirationDate, listCourses);
		final List<ISubscriber> listSubscribers = new ArrayList<>();
		listSubscribers.add(subscriber);	
		try {
			gym.addSubscriber(subscriber);
		} catch (IllegalArgumentException | CourseIsFullException e) {
			e.printStackTrace();
		}
		assertEquals(listSubscribers, gym.getSubscribers());
		
		/*
		 * TESTING UPDATE EMPLOYEE CREDIT
		 */
		
		gym.updateEmployeesCredit();
		assertEquals(800.0, employee.getCredit(), DELTA);
		
		/*
		 * TESTING INCOME
		 */
		employee.settleCredit();
		subscriber.payFee();
		assertEquals(85.0, gym.getCurrentIncome(), DELTA);
		
		/*
		 * TESTING GYM CALENDAR
		 */
		final IGymCalendar cal = new GymCalendar();
		final Schedule sched = new Schedule();
		
		sched.setOpened(true);
		sched.setOpeningHourAndClosingHour(8, 20);
		sched.putPairInHour(new Pair<ICourse,IEmployee>(course, employee), 8, 20);
		cal.setSchedule(DaysOfWeek.LUNEDI, sched);
		gym.setCalendar(cal);
		assertEquals(cal, gym.getProgram());		
		
		/*
		 * TESTING REMOVE COURSE
		 */
		listCourses.remove(course);
		gym.removeCourse(course);
		assertEquals(listCourses, gym.getCourses());
		
		/*
		 * TESTING REMOVE EMPLOYEE
		 */
		listEmployees.remove(employee);
		gym.removeEmployee(employee);
		assertEquals(listEmployees, gym.getEmployees());
		
		/*
		 * TESTING REMOVE SUBSCRIBER
		 */
		listSubscribers.remove(subscriber);
		gym.removeSubscriber(subscriber);
		assertEquals(listSubscribers, gym.getSubscribers());
		
		
		/*
		 * TESTING EXCEPTION
		 */
		try {
			gym.addSubscriber(subscriber);
			gym.addSubscriber(subscriber);
			fail("Errore");
		} catch (IllegalArgumentException | CourseIsFullException e) {
		}
		
		try {
			gym.addEmployee(employee);
			gym.addEmployee(employee);
			fail("Errore");
		} catch (IllegalArgumentException e) {
		}
	}
}
