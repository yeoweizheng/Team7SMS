package team7.sms.controller;

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
import team7.sms.model.AdminUser;
import team7.sms.model.Navbar;
import team7.sms.model.Sidebar;

@Controller
@RequestMapping("/Home")
public class HomeController {
	private static Sidebar sidebar;
	private static Navbar navbar;
	private static final Logger log = LoggerFactory.getLogger(Team7SmsApplication.class);

	// Initialize static content
	public static void init() {
		sidebar = new Sidebar();
		sidebar.addItem("News", "News");
		sidebar.addItem("Calendar", "Calendar");
		sidebar.addItem("Events", "Events");
		sidebar.addItem("Blog", "Blog");
		navbar = new Navbar();
		navbar.addItem("Admin Login", "AdminLogin");
		navbar.addItem("Student Login", "StudentLogin");
		navbar.addItem("Faculty Login", "FacultyLogin");
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
	public String adminLogin(Model model) {
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "home/adminLogin");
		model.addAttribute("adminUser", new AdminUser());
		return "index";
	}
	
	@PostMapping("/AdminLogin")
	public String adminLogin(Model model, @ModelAttribute AdminUser adminUser) {
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "home/adminLogin");
		log.info(adminUser.getUsername());
		log.info(adminUser.getPassword());
		return "index";
	}

	@GetMapping("/StudentLogin")
	public String studentLogin(Model model) {
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "home/studentLogin");
		return "index";
	}

	@GetMapping("/FacultyLogin")
	public String facultyLogin(Model model) {
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "home/facultyLogin");
		return "index";
	}
}
