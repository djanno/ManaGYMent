package test;

import static org.junit.Assert.*;

import model.Model;
import model.User;
import model.gym.Gym;
import model.gym.IGym;

import org.junit.Test;

import exceptions.UserAlreadyExistsException;
import exceptions.WrongCredentialsException;

public class TestModel {
	
	@Test
	public void testExceptionModel(){
		/*
		 * TESTING CREATION MODEL
		 */
		Model model = Model.getModel();
		final IGym gym = new Gym("nome");

		User user = new User("nome", "cognome", "username", new char[]{'c','n','a'}, gym, "testemailjava13@yahoo.com");
		try {
			model.addUser(user);
		} catch (UserAlreadyExistsException e) {
			e.printStackTrace();
		}
		
		
		try {
			model.addUser(user);
			fail("Error");
		} catch (Exception e) {
		}
		
		try {
			assertEquals(true, model.checkAccount(user.getUsername(), user.getPassword()));
		} catch (WrongCredentialsException e) {
			e.printStackTrace();
		}
		
		try {
			model.checkAccount("prova", user.getPassword());
			fail("Error");
		} catch (Exception e) {
		}	
	}
}
