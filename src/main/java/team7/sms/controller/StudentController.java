package team7.sms.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import team7.sms.Team7SmsApplication;
import team7.sms.database.DateService;
import team7.sms.database.DbService;
import team7.sms.database.StudentUserRepository;
import team7.sms.model.*;

@Controller
@RequestMapping("/Student")
public class StudentController {
	private static Sidebar sidebar;
	private static Navbar navbar;
	private static final Logger log = LoggerFactory.getLogger(Team7SmsApplication.class);
	
	private DbService dbService;
	private DateService dateService;
	@Autowired
	public void setDbService(DbService dbService) {
		this.dbService = dbService;
	}
	@Autowired
	public void setDateService(DateService dateService) {
		this.dateService = dateService;
	}

	public static void init() {
		sidebar = new Sidebar();
		sidebar.addItem("Available Courses", "/Student/AvailableCourses/");
		sidebar.addItem("Enrolled Courses", "/Student/EnrolledCourses/");
		sidebar.addItem("Exam Grades", "/Student/ExamGrades/");
		navbar = new Navbar();
		navbar.addItem("Logout", "/Student/Logout/");
	}
	
	@GetMapping("/")
	public String index() {
		return "redirect:/Student/AvailableCourses/";
	}
	
	@GetMapping("/Logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/Home/";
	}

	private StudentUser getStudentUserFromSession(HttpSession session) {
		if(session.getAttribute("studentUser") == null) return null;
		StudentUser studentUser = dbService.findStudentUserById(Integer.parseInt(session.getAttribute("studentUser").toString()));
		return studentUser;
	}

	@GetMapping("/ExamGrades")
	public String examGrades(HttpSession session, Model model) {
		StudentUser studentUser = getStudentUserFromSession(session);
		if(studentUser == null) {
			return "redirect:/Home/StudentLogin";
		}
		ArrayList<String> statuses = new ArrayList<String>(Arrays.asList("Graded"));
		ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByStudentUserAndStatusIn(studentUser, statuses);
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "student/examGrades");
		model.addAttribute("enrollments", enrollments);
		return "index";
	}
	
	@GetMapping("/AvailableCourses")
	public String availableCourses(HttpSession session, Model model) {
		StudentUser studentUser = getStudentUserFromSession(session);
		if(studentUser == null) {
			return "redirect:/Home/StudentLogin";
		}
		ArrayList<Course> courses = dbService.findCoursesByStatus("Created");
		ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByStudentUser(studentUser);
		ArrayList<Course> filteredCourses = new ArrayList<Course>();
		// Remove courses already enrolled by student
		for(Course course : courses) {
			boolean enrolled = false;
			for(Enrollment enrollment : enrollments) {
				if(enrollment.getCourse().equals(course)) enrolled = true;
			}
			if(!enrolled) filteredCourses.add(course);
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "student/availableCourses");
		model.addAttribute("courses", filteredCourses);
		return "index";
	}

	@GetMapping("/EnrolledCourses")
	public String enrollCourses(HttpSession session, Model model) {
		StudentUser studentUser = getStudentUserFromSession(session);
		if(studentUser == null) {
			return "redirect:/Home/StudentLogin";
		}
		ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByStudentUser(studentUser);
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "student/enrolledCourses");
		model.addAttribute("enrollments", enrollments);
		return "index";
	}
	@GetMapping("/CourseDetails/{id}")
	public String courseDetails(HttpSession session, Model model, @PathVariable int id){
		StudentUser studentUser = getStudentUserFromSession(session);
		if(studentUser == null) {
			return "redirect:/Home/StudentLogin";
		}
		Course course = dbService.findCourseById(id);
		Enrollment enrollment = dbService.findEnrollmentByStudentUserAndCourse(studentUser, course);
		ArrayList<String> statuses = new ArrayList<String>(Arrays.asList("Pending", "Approved", "Started"));
		boolean clash = false;
		ArrayList<Enrollment> otherEnrollments = dbService.findEnrollmentsByStudentUserAndStatusIn(studentUser, statuses);
		for(Enrollment e : otherEnrollments) {
			if(dateService.checkOverlap(course.getStartDate(), course.getEndDate(), 
				e.getCourse().getStartDate(), e.getCourse().getEndDate()) && course.getId() != e.getCourse().getId()) {
				clash = true;
			}
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "student/courseDetails");
		model.addAttribute("course", course);
		model.addAttribute("enrollment", enrollment);
		model.addAttribute("studentUser", studentUser);
		model.addAttribute("clash", clash);
		return "index";
	}
}
