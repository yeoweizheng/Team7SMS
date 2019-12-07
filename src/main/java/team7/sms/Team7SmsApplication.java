package team7.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import team7.sms.controller.*;

import team7.sms.controller.HomeController;
import team7.sms.database.AdminUserRepository;
import team7.sms.database.CourseRepository;
import team7.sms.database.DbService;
import team7.sms.database.StudentUserRepository;
import team7.sms.model.AdminUser;
import team7.sms.model.Course;
import team7.sms.model.StudentUser;
import team7.sms.model.FacultyUser;
import team7.sms.database.FacultyUserRepository;

@SpringBootApplication
public class Team7SmsApplication {

	@Autowired
	private AdminUserRepository adminRepo;
	@Autowired
	private StudentUserRepository studentRepo;
	@Autowired
	private FacultyUserRepository facultyRepo;
	@Autowired
	private CourseRepository courseRepo;

	private DbService dbService;
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
			dbService.addAdminUser(new AdminUser("admin", "admin"));
			dbService.addStudentUser(new StudentUser("mark", "mark123", "Mark Goh", 'M', "123 Kent Ridge Dr", "91234567"));
			dbService.addStudentUser(new StudentUser("gaoge", "gaoge123", "Gao Ge", 'F', "456 Kent Ridge Dr", "98765432"));
			dbService.addFacultyUser(new FacultyUser("weizheng", "weizheng123", "Wei Zheng", 'M', "789 Kent Ridge Dr", "98127634"));
			dbService.addCourse(new Course("Java", "01-11-2019", "08-11-2019", "ISS"));
			dbService.addCourse(new Course("C#", "06-11-2019", "13-11-2019", "ISS"));
		};
	}
}
