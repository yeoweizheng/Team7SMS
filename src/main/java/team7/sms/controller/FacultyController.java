package team7.sms.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import team7.sms.CourseComparator;
import team7.sms.DateService;
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
	private CourseComparator courseComparator;
	private DateService dateService;
	@Autowired
	public void setDbService(DbService dbService) {
		this.dbService = dbService;
	}
	@Autowired
	public void setCourseComparator(CourseComparator couseComparator){
		this.courseComparator = courseComparator;
	}
	@Autowired
	public void setDateService(DateService dateService) {
		this.dateService = dateService;
	}
	public static void init() {
		sidebar = new Sidebar();
		sidebar.addItem("Schedule", "/Faculty/Schedule/");
		sidebar.addItem("List of Courses", "/Faculty/CourseList/");
		sidebar.addItem("Score Cards", "/Faculty/ScoreCards/");
		sidebar.addItem("Leaves", "/Faculty/Leaves/");
		navbar = new Navbar();
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

	@GetMapping("/Error")
	public String error(HttpSession session, Model model) {
		if(session.getAttribute("error") == null) return "redirect:/Faculty/";
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "errorPage");
		model.addAttribute("errorMsg", session.getAttribute("error"));
		session.setAttribute("error", null);
		return "index";
	}

	private FacultyUser getFacultyUserFromSession(HttpSession session) {
		if(session.getAttribute("facultyUser") == null) return null;
		FacultyUser facultyUser = dbService.findFacultyUserById(Integer.parseInt(session.getAttribute("facultyUser").toString()));
		addGreeting(facultyUser);
		return facultyUser;
	}

	private void addGreeting(FacultyUser facultyUser) {
		if(facultyUser != null) navbar.addItem("Hello, " + facultyUser.getFullname(), "#");
		navbar.addItem("Logout", "/Faculty/Logout/");
	}

	@GetMapping("/Schedule")
	public String masterList(HttpSession session, Model model) {
		FacultyUser facultyUser = getFacultyUserFromSession(session);
		if(facultyUser == null) 
			return "redirect:/Home/FacultyLogin/";
		ArrayList<String> statuses = new ArrayList<String>(Arrays.asList("Started"));
		ArrayList<Course> courses = dbService.findCoursesByFacultyUserAndStatusIn(facultyUser, statuses);
		Collections.sort(courses, courseComparator);
		ArrayList<ScheduledClass> scheduledClasses = new ArrayList<ScheduledClass>();
		for(Course course : courses) {
			ArrayList<String> dates = dateService.getDateList(course.getStartDate(), course.getEndDate());
			for(String date : dates) {
				scheduledClasses.add(new ScheduledClass(date, course.getSubject().getName()));
			}
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/schedule");
		model.addAttribute("scheduledClasses", scheduledClasses);
		return "index"; 
	}
	
	@GetMapping("/CourseList")
	public String courseList(HttpSession session, Model model) {
		FacultyUser facultyUser = getFacultyUserFromSession(session);
		if(facultyUser == null) 
			return "redirect:/Home/FacultyLogin/";
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
		if(facultyUser == null) 
			return "redirect:/Home/FacultyLogin/";
		Course course = dbService.findCourseById(id);
		ArrayList<String> statuses = new ArrayList<String>(Arrays.asList("Pending", "Approved", "Started", "Finished", "Graded"));
		ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByCourseAndStatusIn(course, statuses);
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/enrollmentList");
		model.addAttribute("course", course);
		model.addAttribute("enrollments", enrollments);
		return "index";
	}
	
	@GetMapping("/ScoreCards")
	public String scoreCards(HttpSession session, Model model) {
		FacultyUser facultyUser = getFacultyUserFromSession(session);
		if(facultyUser == null) 
			return "redirect:/Home/FacultyLogin/";
		ArrayList<String> statuses = new ArrayList<String>(Arrays.asList("Finished", "Graded"));
		ArrayList<Course> courses = dbService.findCoursesByFacultyUserAndStatusIn(facultyUser, statuses);
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/scoreCards");
		model.addAttribute("courses", courses);
		return "index"; 
	}
	
	@GetMapping("/ScoreForm/{id}")
	public String scoreForm(HttpSession session, Model model, @PathVariable int id) {
		FacultyUser facultyUser = getFacultyUserFromSession(session);
		if(facultyUser == null) 
			return "redirect:/Home/FacultyLogin/";
		Course course = dbService.findCourseById(id);
		ArrayList<String> statuses = new ArrayList<String>(Arrays.asList("Finished", "Graded"));
		ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByCourseAndStatusIn(course, statuses);
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/scoreForm");
		model.addAttribute("course", course);
		model.addAttribute("enrollments", enrollments);
		return "index";
	}
	@GetMapping("/Leaves")
	public String leave(HttpSession session, Model model) {
		FacultyUser facultyUser = getFacultyUserFromSession(session);
		if(facultyUser == null) {
			return "redirect:/Home/FacultyLogin/";
		}
		ArrayList<FacultyLeave> facultyLeaves = dbService.findFacultyLeavesByFacultyUser(facultyUser);
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/leaves");
		model.addAttribute("facultyLeaves", facultyLeaves);
		return "index"; 
	}
	

	@GetMapping("/EditLeave/{id}")
	public String editFacultyLeave(HttpSession session, Model model, @PathVariable int id) {
		if(getFacultyUserFromSession(session) == null) {
			return "redirect:/Home/FacultyLogin";
		}
		FacultyLeave facultyLeave = dbService.findFacultyLeaveById(id);
		FacultyLeaveForm facultyLeaveForm = new FacultyLeaveForm();
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/editLeave");
		model.addAttribute("facultyLeave", facultyLeave);
		model.addAttribute("facultyLeaveForm", facultyLeaveForm);
		return "index";
	}
	@PostMapping("/EditLeave/{id}")
	public String editFacultyLeave(HttpSession session, @PathVariable int id, @ModelAttribute FacultyLeaveForm facultyLeaveForm) {
		FacultyUser facultyUser = getFacultyUserFromSession(session);
		if(facultyUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		String startDate = facultyLeaveForm.getStartDate();
		String endDate = facultyLeaveForm.getEndDate();
		ArrayList<String> statuses = new ArrayList<String>(Arrays.asList("Created", "Started"));
		ArrayList<Course> courses = dbService.findCoursesByFacultyUserAndStatusIn(facultyUser, statuses);
		if(!dateService.checkStartEndValidity(startDate, endDate)) {
			session.setAttribute("error", new ErrorMsg("Start date after end date", "/Faculty/EditLeave/" + id));
			return "redirect:/Faculty/Error";
		}
		boolean clash = false;
		for(Course course : courses) {
			if(dateService.checkOverlap(startDate, endDate, course.getStartDate(), course.getEndDate())) clash = true;
		}
		if(clash) {
			session.setAttribute("error", new ErrorMsg("Cannot change leave dates due to courses pending / started.", "/Faculty/EditLeave/" + id));
			return "redirect:/Faculty/Error";
		}
		ArrayList<FacultyLeave> facultyLeaves = dbService.findFacultyLeavesByFacultyUser(facultyUser);
		for(FacultyLeave f : facultyLeaves) {
			if(dateService.checkOverlap(startDate, endDate, f.getStartDate(), f.getEndDate())
				&& f.getId() != id) clash = true;
		}
		if(clash) {
			session.setAttribute("error", new ErrorMsg("Cannot change leave dates due to clash with existing leave.", "/Faculty/EditLeave/" + id));
			return "redirect:/Faculty/Error";
		}
		FacultyLeave facultyLeave = dbService.findFacultyLeaveById(id);
		facultyLeave.setStartDate(startDate);
		facultyLeave.setEndDate(endDate);
		return "redirect:/Faculty/Leave";
	}
	@GetMapping("/DeleteLeave/{id}")
	public String deleteFacultyLeave(HttpSession session, @PathVariable int id) {
		if(getFacultyUserFromSession(session) == null) {
			return "redirect:/Home/FacultyLogin";
		}
		FacultyLeave facultyleave = dbService.findFacultyLeaveById(id);
		dbService.deleteFacultyLeave(facultyleave);
		return "redirect:/Faculty/Leave";
	}
	@GetMapping("/ApplyLeave")
	public String applyLeave(HttpSession session, Model model) {
		FacultyUser facultyUser = getFacultyUserFromSession(session);
		if(facultyUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		FacultyLeaveForm facultyLeaveForm = new FacultyLeaveForm();
		navbar.addItem("Logout", "/Admin/Logout/");
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "faculty/applyLeave");
		model.addAttribute("facultyUser", facultyUser);
		model.addAttribute("facultyLeaveForm", facultyLeaveForm);
		return "index";
	}
	@PostMapping("/ApplyLeave")
	public String applyLeave(HttpSession session, @ModelAttribute FacultyLeaveForm facultyLeaveForm) {
		FacultyUser facultyUser = getFacultyUserFromSession(session);
		if(facultyUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		String startDate = facultyLeaveForm.getStartDate();
		String endDate = facultyLeaveForm.getEndDate();
		ArrayList<String> statuses = new ArrayList<String>(Arrays.asList("Created", "Started"));
		ArrayList<Course> courses = dbService.findCoursesByFacultyUserAndStatusIn(facultyUser, statuses);
		if(!dateService.checkStartEndValidity(startDate, endDate)) {
			session.setAttribute("error", new ErrorMsg("Start date after end date", "/Faculty/ApplyLeave"));
			return "redirect:/Faculty/Error";
		}
		boolean clash = false;
		for(Course course : courses) {
			if(dateService.checkOverlap(startDate, endDate, course.getStartDate(), course.getEndDate())) clash = true;
		}
		if(clash) {
			session.setAttribute("error", new ErrorMsg("Cannot apply leave due to courses pending / started.", "/Faculty/ApplyLeave"));
			return "redirect:/Faculty/Error";
		}
		ArrayList<FacultyLeave> facultyLeaves = dbService.findFacultyLeavesByFacultyUser(facultyUser);
		for(FacultyLeave f : facultyLeaves) {
			if(dateService.checkOverlap(startDate, endDate, f.getStartDate(), f.getEndDate())) clash = true;
		}
		if(clash) {
			session.setAttribute("error", new ErrorMsg("Cannot apply leave due to clash with existing leave.", "/Faculty/ApplyLeave"));
			return "redirect:/Faculty/Error";
		}
		FacultyLeave facultyLeave = new FacultyLeave(startDate, endDate, facultyUser);
		dbService.addFacultyLeave(facultyLeave);
		return "redirect:/Faculty/Leaves";
	}
}
