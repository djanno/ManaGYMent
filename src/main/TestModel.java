package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.IModel;
import model.Model;
import model.User;
import model.gym.Course;
import model.gym.Gym;
import model.gym.GymCalendar;
import model.gym.GymCalendar.DaysOfWeek;
import model.gym.ICourse;
import model.gym.Pair;
import model.gym.Schedule;
import model.gym.members.Employee;
import model.gym.members.IEmployee;
import model.gym.members.IGymMember;
import exceptions.UserAlreadyExistsException;

public class TestModel {
	
	private final IModel model;
	
	public TestModel() {
		Gym gym = new Gym("Domenicone");
		User user = new User("domenico ", "tesei", "Domenicone", new char[] {
				'c', 'n', 'a' }, gym, "ciao.gmial.com");
		this.model = new Model();
		try {
		this.model.addUser(user);
		} catch(UserAlreadyExistsException e){}
		final List<ICourse> list = new ArrayList<>();
		Employee e1 = new Employee("Giovanni", "Tesei", "205110658484849484",
				"pippo", "01254878778", "giovigay@libero.it", gym, 1200, list);
		Employee e2 = new Employee("luca", "coglione", "20511vdvvdv49484",
				"via dei pini", "01254878778", "giovigay@libero.it", gym, 1200, list);
		Employee e3 = new Employee("Elia", "gialli", "FTGTD&&///((((",
				"via ranuzi 66", "0i9i09i09ii9", "ciao.gay.load", gym, 1800, list);
		Employee e4 = new Employee("rossella", "bresca",
				"hbhbhbhbhbhbjhbolnoi", "kmomokmo", "90i909i09k9lk",
				"kmokmmkmokmokm", gym, 12500, list);
		gym.addEmployee(e1);
		gym.addEmployee(e2);
		gym.addEmployee(e3);
		gym.addEmployee(e4);
		Course c1 = new Course("pilates", Color.GREEN, 150, 20);
		Course c2 = new Course("nuoto", Color.BLUE, 400, 60);
		Course c3 = new Course("vela", Color.RED, 100, 100);
		Course c4 = new Course("Yoga", Color.ORANGE, 120, 10);
		Course c5 = new Course("Spinning", Color.CYAN, 40, 60);
		c1.addCoach(e1);
		c1.addCoach(e4);
		c2.addCoach(e1);
		c3.addCoach(e2);
		c4.addCoach(e3);
		c4.addCoach(e4);
		c5.addCoach(e1);
		c5.addCoach(e2);
		c5.addCoach(e3);
		gym.addCourse(c1);
		gym.addCourse(c2);
		gym.addCourse(c3);
		gym.addCourse(c4);
		gym.addCourse(c5);
		Map<Integer, List<ICourse>> program = new HashMap<Integer, List<ICourse>>();
		program.put(8, new ArrayList<ICourse>(Arrays.asList(c1, c2)));
		program.put(10, new ArrayList<ICourse>(Arrays.asList(c4, c1)));
		program.put(12, new ArrayList<ICourse>(Arrays.asList(c5, c3)));
		program.put(15, new ArrayList<ICourse>(Arrays.asList(c2, c4)));
		Map<Integer, List<Pair<ICourse, IEmployee>>> program2 = new HashMap<Integer, List<Pair<ICourse, IEmployee>>>();
		List<Pair<ICourse, IEmployee>> list1 = new ArrayList<>();
		list1.add(new Pair<ICourse, IEmployee>(c1, e4));
		list1.add(new Pair<ICourse, IEmployee>(c2, e1));
		List<Pair<ICourse, IEmployee>> list2 = new ArrayList<>();
		list2.add(new Pair<ICourse, IEmployee>(c3, e2));
		program2.put(10, list1);
		program2.put(11, list2);
		Schedule sch = new Schedule();
		sch.setOpened(true);
		sch.setOpeningHourAndClosingHour(10, 22);
		sch.setProgram(program2);
		List<IGymMember> lista = Arrays.asList(e1, e2, e3);

		// Schedule schMart = new Schedule(true, 9, 12, program2);
		GymCalendar gc = new GymCalendar();
		// gc.setSchedule(DaysOfWeek.LUNEDI, sch);
		gc.setSchedule(DaysOfWeek.MARTEDI, sch);
		gym.setCalendar(gc);
	}
	
	public IModel getModel() {
		return this.model;
	}

}
