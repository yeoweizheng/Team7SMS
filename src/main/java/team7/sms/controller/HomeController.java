package team7.sms.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import team7.sms.PasswordService;
import team7.sms.Team7SmsApplication;
import team7.sms.database.AdminUserRepository;
import team7.sms.database.DbService;
import team7.sms.database.DbServiceInterface;
import team7.sms.database.FacultyUserRepository;
import team7.sms.database.StudentUserRepository;
import team7.sms.model.AdminUser;
import team7.sms.model.ErrorMsg;
import team7.sms.model.FacultyUser;
import team7.sms.model.Navbar;
import team7.sms.model.Sidebar;
import team7.sms.model.StudentUser;

@Controller
@RequestMapping("/Home")
public class HomeController {
	private static Sidebar sidebar;
	private static Navbar navbar;
	private static final Logger log = LoggerFactory.getLogger(Team7SmsApplication.class);

	private DbService dbService;
	@Autowired
	public void setDbService(DbService dbService) {
		this.dbService = dbService;
	}
	private PasswordService passwordService;
	@Autowired
	private void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
	}

	public static void init() {
		sidebar = new Sidebar();
		sidebar.addItem("News", "/Home/News/");
		sidebar.addItem("Calendar", "/Home/Calendar/");
		sidebar.addItem("Events", "/Home/Events/");
		sidebar.addItem("Blog", "/Home/Blog/");
		navbar = new Navbar();
		navbar.addItem("Admin Login", "/Home/AdminLogin/");
		navbar.addItem("Student Login", "/Home/StudentLogin/");
		navbar.addItem("Faculty Login", "/Home/FacultyLogin/");
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "home/home");
		return "index";
	}

	@GetMapping("/News")
	public String news(Model model) {
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "home/news");
		return "index";
	}

	@GetMapping("/Calendar")
	public String calendar(Model model) {
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "home/calendar");
		return "index";
	}

	@GetMapping("/Events")
	public String events(Model model) {
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "home/events");
		return "index";
	}

	@GetMapping("/Blog")
	public String blog(Model model) {
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "home/blog");
		return "index";
	}
	
	@GetMapping("/AdminLogin")
	public String adminLogin(Model model, HttpSession session) {
		if(session.getAttribute("adminUser") != null) {
			AdminUser adminUser = dbService.findAdminUserById(Integer.parseInt(session.getAttribute("adminUser").toString()));
			if(adminUser != null) return "redirect:/Admin/";
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "home/adminLogin");
		model.addAttribute("adminUser", new AdminUser());
		return "index";
	}
	
	@PostMapping("/AdminLogin")
	public String adminLogin(Model model, HttpSession session, @ModelAttribute AdminUser adminUserInput) {
		if( adminUserInput.getUsername().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Username cannot be blank.", "/Home/AdminLogin"));
			return "redirect:/Admin/Error";
		}
		if( adminUserInput.getPassword().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Password cannot be blank.", "/Home/AdminLogin"));
			return "redirect:/Admin/Error";
		}
		AdminUser adminUser = dbService.findAdminUserByUsername(adminUserInput.getUsername());
		if(adminUser != null) {
			if(passwordService.getPasswordEncoder().matches(adminUserInput.getPassword(), adminUser.getPassword())) {
				session.setAttribute("adminUser", adminUser.getId());
				return "redirect:/Admin/";
			}
		}
		return "redirect:/Home/AdminLogin/";
	}
	
	@GetMapping("/StudentLogin")
	public String studentLogin(Model model, HttpSession session) {
		if(session.getAttribute("studentUser") != null) {
			StudentUser studentUser = dbService.findStudentUserById(Integer.parseInt(session.getAttribute("studentUser").toString()));
			if(studentUser != null) return "redirect:/Student/";
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "home/studentLogin");
		model.addAttribute("studentUser", new StudentUser());
		return "index";
	}
	
	@PostMapping("/StudentLogin")
	public String studentLogin(Model model, HttpSession session, @ModelAttribute StudentUser studentUserInput) {
		if( studentUserInput.getUsername().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Username cannot be blank.", "/Home/StudentLogin"));
			return "redirect:/Admin/Error";
		}
		if( studentUserInput.getPassword().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Password cannot be blank.", "/Home/StudentLogin"));
			return "redirect:/Admin/Error";
		}
		StudentUser studentUser = dbService.findStudentUserByUsername(studentUserInput.getUsername());
		if(studentUser != null) {
			if(passwordService.getPasswordEncoder().matches(studentUserInput.getPassword(), studentUser.getPassword())) {
				session.setAttribute("studentUser",studentUser.getId());
				return "redirect:/Student/";
			}
		}
		return "redirect:/Home/StudentLogin/";
	}

	@GetMapping("/FacultyLogin")
	public String facultyLogin(Model model, HttpSession session) {
		if(session.getAttribute("facultyUser") != null) {
			FacultyUser facultyUser = dbService.findFacultyUserById(Integer.parseInt(session.getAttribute("facultyUser").toString()));
			if(facultyUser != null) return "redirect:/Faculty/";
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "home/facultyLogin");
		model.addAttribute("facultyUser", new FacultyUser());
		return "index";
	}
	
	@PostMapping("/FacultyLogin")
	public String facultyLogin(Model model, HttpSession session, @ModelAttribute FacultyUser facultyUserInput) {
		if( facultyUserInput.getUsername().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Username cannot be blank.", "/Home/FacultyLogin"));
			return "redirect:/Admin/Error";
		}
		if( facultyUserInput.getPassword().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Password cannot be blank.", "/Home/FacultyLogin"));
			return "redirect:/Admin/Error";
		}
		FacultyUser facultyUser = dbService.findFacultyUserByUsername(facultyUserInput.getUsername());
		if(facultyUser != null) {
			if(passwordService.getPasswordEncoder().matches(facultyUserInput.getPassword(), facultyUser.getPassword())) {
				session.setAttribute("facultyUser",facultyUser.getId());
				return "redirect:/Faculty/";
			}
		}
		return "redirect:/Home/FacultyLogin/";
	}
	
}
