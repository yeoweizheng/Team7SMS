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
import team7.sms.database.StudentUserRepository;
import team7.sms.model.*;

@Controller
@RequestMapping("/Student")
public class StudentController {
	private static Sidebar sidebar;
	private static Navbar navbar;
	private static final Logger log = LoggerFactory.getLogger(Team7SmsApplication.class);
	
	@Autowired
	private StudentUserRepository studentRepo;

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

	@GetMapping("/PendingApplications")
	public String pendingApplications(HttpSession session, Model model) {
		if(session.getAttribute("username") == null) return "redirect:/Home/";
		StudentUser studentUser = studentRepo.findOneByUsername(session.getAttribute("username").toString());
		if(studentUser == null) {
			return "redirect:/Home/";
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "student/pendingApplications");
		return "index";
	}
}
