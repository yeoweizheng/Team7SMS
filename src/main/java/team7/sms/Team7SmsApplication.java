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
			dbService.addAdminUser(new AdminUser("admin", "admin"));
			dbService.addStudentUser(new StudentUser("mark", "mark123", "Mark Goh", 'M', "123 Kent Ridge Dr", "91234567"));
			dbService.addStudentUser(new StudentUser("gaoge", "gaoge123", "Gao Ge", 'F', "456 Kent Ridge Dr", "98765432"));
			dbService.addFacultyUser(new FacultyUser("weizheng", "weizheng123", "Wei Zheng", 'M', "789 Kent Ridge Dr", "98127634"));
			dbService.addSubject(new Subject("Java", "Java programming fundamentals"));
			dbService.addCourse(new Course("02-03-2019", "03-03-2019",
					dbService.findSubjectById(1), dbService.findFacultyUserById(1)));
			Enrollment enrollment = new Enrollment(dbService.findStudentUserById(1), 
					dbService.findCourseById(1), "pending");
			dbService.addEnrollment(enrollment);
		};
	}
	
	//To test JPA queries
	@Bean
	public CommandLineRunner facultyTest() {
		return (args) -> {
			
//			log.info("Testing the Course Repo: .findAll");
//			for (Course c : courseRepo.findAll()) {
//				log.info("Course: " + c.toString());
//			}
//			
//			log.info("Testing the Course Repo: .findOneById");
//			log.info("Course: " + courseRepo.findOneById(1));
//			
//			log.info("Testing the Course Repo: .findByFacultyUser");
//			for (Course c : courseRepo.findByFacultyUser(facultyUserRepo.findOneById(1))) {
//				log.info("Course: " + c.toString());
//			}
//			
//			log.info("Testing the Enrollment Repo: .findEnrollmentsByCourseId");
//			ArrayList<Course> courses = courseRepo.findByFacultyUser(facultyRepo.findOneById(1));
//			ArrayList<Integer> courseIds = new ArrayList<Integer>(courses.size());
//			for (Course course : courses) {
//				courseIds.add(course.getId());
//			}
//			for (Enrollment enrollment : enrollmentRepo.findEnrollmentsByCourseIdIn(courseIds)) {
//				log.info("Enrollment: " + enrollment.toString());
//			}
			
			
			log.info("Testing Faculty/CourseList Data: ");
			FacultyUser facultyUser = new FacultyUser("weizheng", "weizheng123", "Wei Zheng", 'M', "789 Kent Ridge Dr", "98127634");
			int id = facultyUser.getId();
			ArrayList<Course> courses = dbService.findCoursesbyLecturerId(id);
			ArrayList<Subject> subjects = dbService.findSubjects();
			ArrayList<Enrollment> enrollments = dbService.findEnrollments();
			ArrayList<FacultyUser> facultyUsers = dbService.findFacultyUsers();
			for (Enrollment enrollment : enrollments) {
				log.info(Integer.toString(enrollment.getCourse().getId()));
				log.info(enrollment.getCourse().getStartDate());
				log.info(enrollment.getCourse().getEndDate());
				log.info(enrollment.getCourse().getSubject().getName());
				log.info(Integer.toString(enrollment.getId()));
			}
		};
	}
	

}

			
			
			
			
		
