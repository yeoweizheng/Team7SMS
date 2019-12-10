package team7.sms.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import team7.sms.Team7SmsApplication;
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
	@Autowired
	public void setDbService(DbService dbService) {
		this.dbService = dbService;
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
		if(session.getAttribute("username") == null) return null;
		StudentUser studentUser = dbService.findStudentUserByUsername(session.getAttribute("username").toString());
		return studentUser;
	}

	@GetMapping("/ExamGrades")
	public String examGrades(HttpSession session, Model model) {
		if(getStudentUserFromSession(session) == null) return "redirect:/Home/StudentLogin/";
		ArrayList<Enrollment> enrollments = dbService.findEnrollment();
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "student/examGrades");
		model.addAttribute("enrollments", enrollments);
		return "index";
	}
	
	@GetMapping("/AvailableCourses")
	public String availableCourses(HttpSession session, Model model) {
		if(getStudentUserFromSession(session) == null) {
			return "redirect:/Home/StudentLogin";
		}
		ArrayList<Course> courses = dbService.findCourses();
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "student/availableCourses");
		model.addAttribute("courses", courses);
		return "index";
	}
	@GetMapping("/EnrolledCourses")
	public String enrollCourses(HttpSession session, Model model) {
		if(getStudentUserFromSession(session) == null) {
			return "redirect:/Home/StudentLogin";
		}
		ArrayList<Enrollment> enrollments = dbService.findEnrollments();
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "student/enrolledCourses");
		model.addAttribute("enrollments", enrollments);
		return "index";
	}
	
	
}
