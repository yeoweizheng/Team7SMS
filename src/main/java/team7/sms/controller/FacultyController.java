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
import team7.sms.database.*;
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
		sidebar.addItem("Schedule", "/Faculty/Schedule/");
		sidebar.addItem("List of Courses", "/Faculty/CourseList/");
		sidebar.addItem("Score Cards", "/Faculty/ScoreCards/");
		sidebar.addItem("Leave", "/Faculty/Leave/");

		navbar = new Navbar();
		navbar.addItem("Logout", "/Faculty/Logout/");
	}
	
	
	@GetMapping("/")
	public String index() { 
		return "redirect:/Faculty/Schedule/"; 
	}
	 
	
	@GetMapping("/Logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/Home/";
	}

	private FacultyUser getFacultyUserFromSession(HttpSession session) {
		if(session.getAttribute("facultyUser") == null) return null;
		FacultyUser facultyUser = dbService.findFacultyUserById(Integer.parseInt(session.getAttribute("facultyUser").toString()));
		return facultyUser;
	}

	@GetMapping("/Schedule")
	public String masterList(HttpSession session, Model model) {
		if(getFacultyUserFromSession(session) == null) return "redirect:/Home/FacultyLogin/";
		ArrayList<Enrollment> enrollments = dbService.findEnrollments();
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/schedule");
		model.addAttribute("enrollments", enrollments);
		return "index"; 
	}
	
	@GetMapping("/CourseList")
	public String courseList(HttpSession session, Model model) {
		FacultyUser facultyUser = getFacultyUserFromSession(session);
		if(facultyUser == null) return "redirect:/Home/FacultyLogin/";
		ArrayList<Course> courses = dbService.findCoursesByFacultyUser(facultyUser);
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/courseList");
		model.addAttribute("courses", courses);
		return "index"; 
	}
	@GetMapping("/EnrollmentList/{id}")
	public String enrollmentList(HttpSession session, Model model, @PathVariable int id) {
		FacultyUser facultyUser = getFacultyUserFromSession(session);
		if(facultyUser == null) return "redirect:/Home/FacultyLogin/";
		ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByCourse(dbService.findCourseById(id));
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/enrollmentList");
		model.addAttribute("enrollments", enrollments);
		return "index";
	}
	
	@GetMapping("/ScoreCards")
	public String scoreCards(HttpSession session, Model model) {
		FacultyUser facultyUser = getFacultyUserFromSession(session);
		if(facultyUser == null)
			return "redirect:/Home/FacultyLogin/";
		int id = facultyUser.getId();
		ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByCourse(dbService.findCourseById(id));
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/scoreCards");
		model.addAttribute("enrollments", enrollments);
		return "index"; 
	}
	
	@GetMapping("/Leave")
	public String leave(HttpSession session, Model model) {
		FacultyUser facultyUser = getFacultyUserFromSession(session);
		if(facultyUser == null)
			return "redirect:/Home/FacultyLogin/";
		int id = facultyUser.getId();
		ArrayList<FacultyLeave> facultyLeaves = dbService.findByFacultyUser(facultyUser);
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/leave");
		model.addAttribute("facultyLeaves", facultyLeaves);
		return "index"; 
	}
	
}
