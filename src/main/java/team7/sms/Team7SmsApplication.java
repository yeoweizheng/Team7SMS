package team7.sms;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import team7.sms.controller.*;



import team7.sms.model.*;
import team7.sms.database.*;

@SpringBootApplication
public class Team7SmsApplication {

	private DbService dbService;
	private static final Logger log = LoggerFactory.getLogger(Team7SmsApplication.class);
	@Autowired
	public void setDbService(DbService dbService) {
		this.dbService = dbService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Team7SmsApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner initializeControllers() {
		return (args) -> {
			HomeController.init();
			AdminController.init();
			StudentController.init();
			FacultyController.init();
		};
	}
	
	@Bean
	public CommandLineRunner initializeDb() {
		return (args) -> {
			dbService.addAdminUser(new AdminUser("admin", "admin", "Administrator"));
			dbService.addStudentUser(new StudentUser("mark", "mark123", "Mark Goh", 'M', "123 Kent Ridge Dr", "91234567"));
			dbService.addStudentUser(new StudentUser("gaoge", "gaoge123", "Gao Ge", 'F', "456 Kent Ridge Dr", "98765432"));
			dbService.addFacultyUser(new FacultyUser("weizheng", "weizheng123", "Wei Zheng", 'M', "789 Kent Ridge Dr", "98127634"));
			dbService.addFacultyUser(new FacultyUser("ziling", "ziling123", "Zi Ling", 'M', "567 Kent Ridge Dr", "98127123"));
			dbService.addSubject(new Subject("Java", "Java programming fundamentals"));
			dbService.addSubject(new Subject("C#", "C# object oriented programming"));
			dbService.addCourse(new Course("02-Mar-19", "03-Mar-19",
					dbService.findSubjectById(1), dbService.findFacultyUserById(1)));
			dbService.addCourse(new Course("15-Apr-19", "06-Jun-19",
					dbService.findSubjectById(2), dbService.findFacultyUserById(2)));
			dbService.addEnrollment(new Enrollment(dbService.findStudentUserById(1), 
					dbService.findCourseById(1)));
			dbService.addEnrollment(new Enrollment(dbService.findStudentUserById(2), 
					dbService.findCourseById(1)));
		};
	}
	
}

			
			
			
			
		
