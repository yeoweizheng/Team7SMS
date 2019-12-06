package team7.sms.controller;

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
		sidebar.addItem("Copy of Grades", "/Student/CopyofGrades/");
		sidebar.addItem("GPA", "/Student/GPA/");
		sidebar.addItem("Available Courses", "/Student/AvailableCourses/");
		sidebar.addItem("Enroll Courses", "/Student/EnrollCourses/");
		navbar = new Navbar();
		navbar.addItem("Logout", "/Student/Logout/");
	}
	
	@GetMapping("/")
	public String index() {
		return "redirect:/Student/CopyofGrades/";
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

	@GetMapping("/CopyofGrades")
	public String copyofGrades(HttpSession session, Model model) {
		if(getStudentUserFromSession(session) == null) return "redirect:/Home/StudentLogin/";
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "student/copyofGrades");
		return "index";
	}
	
	@GetMapping("/GPA")
	public String GPA(HttpSession session, Model model) {
		if(getStudentUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "student/GPA");
		return "index";
	}
	@GetMapping("/AvailableCourses")
	public String availableCourses(HttpSession session, Model model) {
		if(getStudentUserFromSession(session) == null) {
			return "redirect:/Home/StudentLogin";
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "student/availableCourses");
		return "index";
	}
	@GetMapping("/EnrollCourses")
	public String enrollCourses(HttpSession session, Model model) {
		if(getStudentUserFromSession(session) == null) {
			return "redirect:/Home/StudentLogin";
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "student/enrollCourses");
		return "index";
	}
}
