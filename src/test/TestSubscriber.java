package test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import model.gym.Course;
import model.gym.Gym;
import model.gym.ICourse;
import model.gym.IGym;
import model.gym.members.ISubscriber;
import model.gym.members.Subscriber;

import org.junit.Test;

public class TestSubscriber {
	
	@Test
	public void testEmployeeCreation(){
		
		/*
		 * TESTING CREATION SUBSCRIBER
		 */
		final IGym gym = new Gym("nome");
		
		final ICourse course = new Course("boxe", new Color(18), 5.0, 10);
		final List<ICourse> listCourses = new ArrayList<>();
		
		final Calendar subscriptionDate = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
		final Calendar expirationDate = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
		expirationDate.set(2015, 10, 24);
		
		final ISubscriber subscriber = new Subscriber("nome", "cognome", "123456789012345", "via le dita dal naso 69", "12457963",
				"qwerty@asd.com", gym, subscriptionDate, expirationDate, listCourses);
		assertEquals("nome", subscriber.getName());
		
		/*
		 * TESTING ADD COURSES
		 */
		listCourses.add(course);
		subscriber.setCourses(listCourses);
		assertEquals(listCourses, subscriber.getCourses());		
		
	}
	
}
