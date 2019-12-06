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
		sidebar.addItem("Pending Applications", "/Student/PendingApplications/");
		sidebar.addItem("Student Users", "/Student/StudentUsers/");
		sidebar.addItem("Faculty Users", "/Student/FacultyUsers/");
		sidebar.addItem("Courses", "/Student/Courses/");
		sidebar.addItem("Departments", "/Student/Departments/");
		navbar = new Navbar();
		navbar.addItem("Logout", "/Student/Logout/");
	}
	
	@GetMapping("/")
	public String index() {
		return "redirect:/Student/PendingApplications/";
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

	@GetMapping("/PendingApplications")
	public String pendingApplications(HttpSession session, Model model) {
		if(getStudentUserFromSession(session) == null) return "redirect:/Home/StudentLogin/";
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "student/pendingApplications");
		return "index";
	}
}
