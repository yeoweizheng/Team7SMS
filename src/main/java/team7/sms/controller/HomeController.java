package team7.sms.controller;

import javax.servlet.http.HttpSession;

import org.attoparser.dom.StructureTextsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import team7.sms.Team7SmsApplication;
import team7.sms.database.AdminUserRepository;
import team7.sms.database.StudentUserRepository;
import team7.sms.model.AdminUser;
import team7.sms.model.Navbar;
import team7.sms.model.Sidebar;
import team7.sms.model.StudentUser;

@Controller
@RequestMapping("/Home")
public class HomeController {
	private static Sidebar sidebar;
	private static Navbar navbar;
	private static final Logger log = LoggerFactory.getLogger(Team7SmsApplication.class);

	@Autowired
	private AdminUserRepository adminRepo;
	@Autowired
	private StudentUserRepository studentRepo;

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
		if(session.getAttribute("username") != null) {
			AdminUser adminUser = adminRepo.findOneByUsername(session.getAttribute("username").toString());
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
		AdminUser adminUser = adminRepo.findOneByUsername(adminUserInput.getUsername());
		if(adminUser != null) {
			if(adminUser.getPassword().equals(adminUserInput.getPassword())) {
				session.setAttribute("username", adminUser.getUsername());
				return "redirect:/Admin/PendingApplications/";
			}
		}
		return "redirect:/Home/AdminLogin/";
	}

	
	@GetMapping("/StudentLogin")
	public String studentLogin(Model model, HttpSession session) {
		if(session.getAttribute("username") != null) {
			StudentUser studentUser = studentRepo.findOneByUsername(session.getAttribute("username").toString());
			if(studentUser != null) return "redirect:/Student/";
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "home/studentLogin");
		model.addAttribute("studentUser", new AdminUser());
		return "index";
	}
	
	@PostMapping("/StudentLogin")
	public String studentLogin(Model model, HttpSession session, @ModelAttribute StudentUser studentUserInput) {
		StudentUser studentUser = studentRepo.findOneByUsername(studentUserInput.getUsername());
		if(studentUser != null) {
			if(studentUser.getPassword().equals(studentUserInput.getPassword())) {
				session.setAttribute("username",studentUser.getUsername());
				return "redirect:/Student/PendingApplications/";
			}
		}
		return "redirect:/Home/StudentLogin/";
	}

	@GetMapping("/FacultyLogin")
	public String facultyLogin(Model model) {
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "home/facultyLogin");
		return "index";
	}
}
