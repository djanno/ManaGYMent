package test;

import static org.junit.Assert.assertEquals;
import model.gym.Gym;
import model.gym.IGym;
import model.gym.members.Employee;
import model.gym.members.IEmployee;

import org.junit.Test;

public class TestEmployee {
	private static final double DELTA = 1e-15;
	
	@Test
	public void testEmployeeCreation(){
		
		/*
		 * TESTING CREATION SUBSCRIBER
		 */
		final IGym gym = new Gym("nome");
		
		final IEmployee employee = new Employee("nome", "cognome", "123456789012345", "via le dita dal naso 69", "12457963",
				"qwerty@asd.com", gym, 800);
		assertEquals("nome", employee.getName());
		
		/*
		 * TESTING GET CREDIT
		 */
		assertEquals(800.0, employee.getCredit(), DELTA);
		
		/*
		 * TESTING SETTLE CREDIT
		 */
		employee.settleCredit();
		assertEquals(0.0, employee.getCredit(), DELTA);
		
	}
}
