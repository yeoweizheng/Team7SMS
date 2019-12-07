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
import team7.sms.database.FacultyUserRepository;
import team7.sms.database.DbService;
import team7.sms.model.*;

@Controller
@RequestMapping("/Faculty")
public class FacultyController {
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
		sidebar.addItem("Master List", "/Faculty/MasterList/");
		sidebar.addItem("List of Courses", "/Faculty/CourseList/");
		sidebar.addItem("List of Students", "/Faculty/StudentList/");
		sidebar.addItem("Score Cards", "/Faculty/ScoreCards/");

		navbar = new Navbar();
		navbar.addItem("Logout", "/Faculty/Logout/");
	}
	
	
	@GetMapping("/")
	public String index() { 
		return "redirect:/Faculty/MasterList/"; 
	}
	 
	
	@GetMapping("/Logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/Home/";
	}

	private FacultyUser getFacultyUserFromSession(HttpSession session) {
		if(session.getAttribute("username") == null) return null;
		FacultyUser facultyUser = dbService.findFacultyUserByUsername(session.getAttribute("username").toString());
		return facultyUser;
	}

	@GetMapping("/MasterList")
	public String masterList(HttpSession session, Model model) {
		if(getFacultyUserFromSession(session) == null)
			return "redirect:/Home/FacultyLogin/";
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/masterList");
		return "index"; 
	}
	
	@GetMapping("/CourseList")
	public String courseList(HttpSession session, Model model) {
		if(getFacultyUserFromSession(session) == null)
			return "redirect:/Home/FacultyLogin/";
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/courseList");
		return "index"; 
	}
	
	@GetMapping("/StudentList")
	public String studentList(HttpSession session, Model model) {
		if(getFacultyUserFromSession(session) == null)
			return "redirect:/Home/FacultyLogin/";
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/studentList");
		return "index"; 
	}
	
	@GetMapping("/ScoreCards")
	public String scoreCards(HttpSession session, Model model) {
		if(getFacultyUserFromSession(session) == null)
			return "redirect:/Home/FacultyLogin/";
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/scoreCards");
		return "index"; 
	}
	 
}
