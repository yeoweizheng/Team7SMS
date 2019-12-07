package team7.sms.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team7.sms.Team7SmsApplication;
import team7.sms.database.AdminUserRepository;
import team7.sms.database.DbService;
import team7.sms.model.*;

@Controller
@RequestMapping("/Admin")
public class AdminController {
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
		sidebar.addItem("Pending Applications", "/Admin/PendingApplications/");
		sidebar.addItem("Student Users", "/Admin/StudentUsers/");
		sidebar.addItem("Faculty Users", "/Admin/FacultyUsers/");
		sidebar.addItem("Courses", "/Admin/Courses/");
		sidebar.addItem("Departments", "/Admin/Departments/");
		navbar = new Navbar();
		navbar.addItem("Logout", "/Admin/Logout/");
	}

	@GetMapping("/")
	public String index() {
		return "redirect:/Admin/PendingApplications/";
	}
	
	@GetMapping("/Logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/Home/";
	}
	
	private AdminUser getAdminUserFromSession(HttpSession session) {
		if(session.getAttribute("username") == null) return null;
		AdminUser adminUser = dbService.findAdminUserByUsername(session.getAttribute("username").toString());
		return adminUser;
	}

	@GetMapping("/PendingApplications")
	public String pendingApplications(HttpSession session, Model model) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/pendingApplications");
		return "index";
	}
	@GetMapping("/StudentUsers")
	public String studentUsers(HttpSession session, Model model) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		ArrayList<StudentUser> studentUsers = dbService.findStudentUsers();
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/studentUsers");
		model.addAttribute("studentUsers", studentUsers);
		return "index";
	}
	@GetMapping("/AddStudentUser")
	public String addStudentUser(HttpSession session, Model model) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		StudentUser studentUser = new StudentUser();
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("studentUser", studentUser);
		model.addAttribute("content", "admin/addStudentUser");
		return "index";
	}
	@PostMapping("/AddStudentUser")
	public String addStudentUser(HttpSession session, @ModelAttribute StudentUser studentUser) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		dbService.addStudentUser(studentUser);
		return "redirect:/Admin/StudentUsers";
	}
	@GetMapping("/EditStudentUser/{id}")
	public String editStudentUser(HttpSession session, Model model, @PathVariable int id) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		StudentUser studentUser = dbService.findStudentUserById(id);
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/editStudentUser");
		model.addAttribute("studentUser", studentUser);
		return "index";
	}
	@PostMapping("/EditStudentUser/{id}")
	public String editStudentUser(HttpSession session, @PathVariable int id, @ModelAttribute StudentUser studentUser) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		if(studentUser.getPassword().equals("")) {
			studentUser.setPassword(dbService.findStudentUserById(studentUser.getId()).getPassword());
		}
		dbService.addStudentUser(studentUser);
		return "redirect:/Admin/StudentUsers";
	}
	@GetMapping("/DeleteStudentUser/{id}")
	public String deleteStudentUser(HttpSession session, @PathVariable int id) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		dbService.deleteStudentUserById(id);
		return "redirect:/Admin/StudentUsers";
	}
	@GetMapping("/FacultyUsers")
	public String facultyUsers(HttpSession session, Model model) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/facultyUsers");
		return "index";
	}
	@GetMapping("/Courses")
	public String courses(HttpSession session, Model model) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/courses");
		return "index";
	}
	@GetMapping("/Departments")
	public String departments(HttpSession session, Model model) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/departments");
		return "index";
	}
}
