package team7.sms.controller;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.expression.Dates;

import antlr.collections.List;
import team7.sms.DateService;
import team7.sms.PasswordService;
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
	private DateService dateService;
	@Autowired
	public void setDbService(DbService dbService) {
		this.dbService = dbService;
	}
	@Autowired
	public void setDateService(DateService dateService) {
		this.dateService = dateService;
	}
	private PasswordService passwordService;
	@Autowired
	private void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
	}

	public static void init() {
		sidebar = new Sidebar();
		sidebar.addItem("Pending Applications", "/Admin/PendingApplications/");
		sidebar.addItem("Pending Faculty Leaves", "/Admin/PendingFacultyLeaves/");
		sidebar.addItem("Faculty Leaves Schedule", "/Admin/FacultyLeavesSchedule/");
		sidebar.addItem("Student Users", "/Admin/StudentUsers/");
		sidebar.addItem("Faculty Users", "/Admin/FacultyUsers/");
		sidebar.addItem("Subjects", "/Admin/Subjects/");
		sidebar.addItem("Courses", "/Admin/Courses/");
		sidebar.addItem("CGPA", "/Admin/CGPA/");
		navbar = new Navbar();
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
		if(session.getAttribute("adminUser") == null) return null;
		AdminUser adminUser = dbService.findAdminUserById(Integer.parseInt(session.getAttribute("adminUser").toString()));
		addGreeting(adminUser);
		return adminUser;
	}
	
	private void addGreeting(AdminUser adminUser) {
		if(adminUser != null) {
			navbar.clearItems();
			navbar.addItem("Hello, " + adminUser.getFullname(), "#");
			navbar.addItem("Logout", "/Admin/Logout/");
		}
	}

	@GetMapping("/Error")
	public String error(HttpSession session, Model model) {
		if(session.getAttribute("error") == null) return "redirect:/Admin/";
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "errorPage");
		model.addAttribute("errorMsg", session.getAttribute("error"));
		session.setAttribute("error", null);
		return "index";
	}
		
	@GetMapping("/PendingApplications")
	public String pendingApplications(HttpSession session, Model model) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByStatus("Pending");
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/pendingApplications");
		model.addAttribute("enrollments", enrollments);
		return "index";
	}
	

	@GetMapping("/StudentUsers")
	public String studentUsers(HttpSession session, Model model) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
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
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
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
		if( studentUser.getFullname().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Full name cannot be blank.", "/Admin/AddStudentUser"));
			return "redirect:/Admin/Error";
		}
		String genderString = Character.toString(studentUser.getGender());
		if( (genderString.isEmpty())) {
			session.setAttribute("error", new ErrorMsg("Gender cannot be blank.", "/Admin/AddStudentUser"));
			return "redirect:/Admin/Error";
		}
		if( studentUser.getMobileNo().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Mobile No. cannot be blank.", "/Admin/AddStudentUser"));
			return "redirect:/Admin/Error";
		}
		if( studentUser.getAddress().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Address cannot be blank.", "/Admin/AddStudentUser"));
			return "redirect:/Admin/Error";
		}
		if( studentUser.getUsername().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Username cannot be blank.", "/Admin/AddStudentUser"));
			return "redirect:/Admin/Error";
		}
		if( studentUser.getPassword().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Password cannot be blank.", "/Admin/AddStudentUser"));
			return "redirect:/Admin/Error";
		}
		studentUser.setPassword(passwordService.getPasswordEncoder().encode(studentUser.getPassword()));
		dbService.addStudentUser(studentUser);
		return "redirect:/Admin/StudentUsers";
	}
	@GetMapping("/EditStudentUser/{id}")
	public String editStudentUser(HttpSession session, Model model, @PathVariable int id) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		StudentUser studentUser = dbService.findStudentUserById(id);
		ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByStudentUser(studentUser);
		ArrayList<Notification> notifiations = dbService.findNotificationsByStudentUser(studentUser);
		boolean allowDelete = true;
		if(enrollments.size() != 0 || notifiations.size() != 0) allowDelete = false;
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/editStudentUser");
		model.addAttribute("allowDelete", allowDelete);
		model.addAttribute("studentUser", studentUser);
		return "index";
	}
	@PostMapping("/EditStudentUser/{id}")
	public String editStudentUser(HttpSession session, @PathVariable int id, @ModelAttribute StudentUser studentUser) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		if( studentUser.getFullname().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Full name cannot be blank.", "/Admin/EditStudentUser/" + id));
			return "redirect:/Admin/Error";
		}
		String genderString = Character.toString(studentUser.getGender());
		if( (genderString.isEmpty())) {
			session.setAttribute("error", new ErrorMsg("Gender cannot be blank.", "/Admin/EditStudentUser/" + id));
			return "redirect:/Admin/Error";
		}
		if( studentUser.getMobileNo().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Mobile No. cannot be blank.", "/Admin/EditStudentUser/" + id));
			return "redirect:/Admin/Error";
		}
		if( studentUser.getAddress().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Address cannot be blank.", "/Admin/EditStudentUser/" + id));
			return "redirect:/Admin/Error";
		}
		if( studentUser.getUsername().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Username cannot be blank.", "/Admin/EditStudentUser/" + id));
			return "redirect:/Admin/Error";
		}
		if(studentUser.getPassword().equals("")) {
			studentUser.setPassword(dbService.findStudentUserById(studentUser.getId()).getPassword());
		}
		else {
			studentUser.setPassword(passwordService.getPasswordEncoder().encode(studentUser.getPassword()));
		}
		dbService.addStudentUser(studentUser);
		return "redirect:/Admin/StudentUsers";
	}
	@GetMapping("/DeleteStudentUser/{id}")
	public String deleteStudentUser(HttpSession session, @PathVariable int id) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		StudentUser studentUser = dbService.findStudentUserById(id);
		dbService.deleteStudentUser(studentUser);
		return "redirect:/Admin/StudentUsers";
	}
	
	@GetMapping("/FacultyUsers")
	public String facultyUsers(HttpSession session, Model model) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		ArrayList<FacultyUser> facultyUsers = dbService.findFacultyUsers();
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/facultyUsers");
		model.addAttribute("facultyUsers", facultyUsers);
		return "index";
	}
	
	@GetMapping("/AddFacultyUser")
	public String addFacultyUser(HttpSession session, Model model) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		FacultyUser facultyUser = new FacultyUser();
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("facultyUser", facultyUser);
		model.addAttribute("content", "admin/addFacultyUser");
		return "index";
	}
	@PostMapping("/AddFacultyUser")
	public String addFacultyUser(HttpSession session, @ModelAttribute FacultyUser facultyUser) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		if( facultyUser.getFullname().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Full name cannot be blank.", "/Admin/AddFacultyUser"));
			return "redirect:/Admin/Error";
		}
		String genderString = Character.toString(facultyUser.getGender());
		if( (genderString.isEmpty())) {
			session.setAttribute("error", new ErrorMsg("Gender cannot be blank.", "/Admin/AddFacultyUser"));
			return "redirect:/Admin/Error";
		}
		if( facultyUser.getMobileNo().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Mobile No. cannot be blank.", "/Admin/AddFacultyUser"));
			return "redirect:/Admin/Error";
		}
		if( facultyUser.getAddress().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Address cannot be blank.", "/Admin/AddFacultyUser"));
			return "redirect:/Admin/Error";
		}
		if( facultyUser.getUsername().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Username cannot be blank.", "/Admin/AddFacultyUser"));
			return "redirect:/Admin/Error";
		}
		if( facultyUser.getPassword().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Password cannot be blank.", "/Admin/AddFacultyUser"));
			return "redirect:/Admin/Error";
		}
		facultyUser.setPassword(passwordService.getPasswordEncoder().encode(facultyUser.getPassword()));
		dbService.addFacultyUser(facultyUser);
		return "redirect:/Admin/FacultyUsers";
	}
	@GetMapping("/EditFacultyUser/{id}")
	public String editFacultyUser(HttpSession session, Model model, @PathVariable int id) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		FacultyUser facultyUser = dbService.findFacultyUserById(id);
		ArrayList<Course> courses = dbService.findCoursesByFacultyUser(facultyUser);
		ArrayList<FacultyLeave> leaves = dbService.findFacultyLeavesByFacultyUser(facultyUser);
		boolean allowDelete = true;
		if(courses.size() != 0 || leaves.size() != 0) allowDelete = false;
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/editFacultyUser");
		model.addAttribute("facultyUser", facultyUser);
		model.addAttribute("allowDelete", allowDelete);
		return "index";
	}
	@PostMapping("/EditFacultyUser/{id}")
	public String editFacultyUser(HttpSession session, @PathVariable int id, @ModelAttribute FacultyUser facultyUser) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		if( facultyUser.getFullname().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Full name cannot be blank.", "/Admin/EditFacultyUser/" + id));
			return "redirect:/Admin/Error";
		}
		String genderString = Character.toString(facultyUser.getGender());
		if( (genderString.isEmpty())) {
			session.setAttribute("error", new ErrorMsg("Gender cannot be blank.", "/Admin/EditFacultyUser/" + id));
			return "redirect:/Admin/Error";
		}
		if( facultyUser.getMobileNo().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Mobile No. cannot be blank.", "/Admin/EditFacultyUser/" + id));
			return "redirect:/Admin/Error";
		}
		if( facultyUser.getAddress().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Address cannot be blank.", "/Admin/EditFacultyUser/" + id));
			return "redirect:/Admin/Error";
		}
		if( facultyUser.getUsername().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Username cannot be blank.", "/Admin/EditFacultyUser/" + id));
			return "redirect:/Admin/Error";
		}
		if(facultyUser.getPassword().equals("")) {
			facultyUser.setPassword(dbService.findFacultyUserById(facultyUser.getId()).getPassword());
		}
		else {

			facultyUser.setPassword(passwordService.getPasswordEncoder().encode(facultyUser.getPassword()));
		}
		dbService.addFacultyUser(facultyUser);
		return "redirect:/Admin/FacultyUsers";
	}
	@GetMapping("/DeleteFacultyUser/{id}")
	public String deleteFacultyUser(HttpSession session, @PathVariable int id) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		dbService.deleteFacultyUserById(id);
		return "redirect:/Admin/FacultyUsers";
	}
	@GetMapping("/Subjects")
	public String subjects(HttpSession session, Model model) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		ArrayList<Subject> subjects = dbService.findSubjects();
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/subjects");
		model.addAttribute("subjects", subjects);
		return "index";
	}
	@GetMapping("/Courses")
	public String courses(HttpSession session, Model model) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		ArrayList<Course> courses = dbService.findCourses();
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/courses");
		model.addAttribute("courses", courses);
		return "index";
	}
	@GetMapping("/AddCourse")
	public String addCourse(HttpSession session, Model model) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		Course course = new Course();
		ArrayList<FacultyUser> facultyUsers = dbService.findFacultyUsers();
		ArrayList<Subject> subjects = dbService.findSubjects();
		CourseForm courseForm = new CourseForm();
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/addCourse");
		model.addAttribute("course", course);
		model.addAttribute("facultyUsers", facultyUsers);
		model.addAttribute("subjects", subjects);
		model.addAttribute("courseForm", courseForm);
		return "index";
	}
	@PostMapping("/AddCourse")
	public String addCourse(HttpSession session, @ModelAttribute CourseForm courseForm) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		String startDate = courseForm.getStartDate();
		String endDate = courseForm.getEndDate();
		FacultyUser facultyUser = dbService.findFacultyUserById(courseForm.getFacultyUserId());
		if( startDate.isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Start date cannot be blank.", "/Admin/AddCourse"));
			return "redirect:/Admin/Error";
		}
		if( endDate.isEmpty()) {
			session.setAttribute("error", new ErrorMsg("End date cannot be blank.", "/Admin/AddCourse"));
			return "redirect:/Admin/Error";
		}
		if(!dateService.checkStartEndValidity(startDate, endDate)) {
			session.setAttribute("error", new ErrorMsg("Start date after end date.", "/Admin/AddCourse"));
			return "redirect:/Admin/Error";
		}
		ArrayList<Course> existingCourses = dbService.findCoursesByFacultyUser(facultyUser);
		boolean overlap = false;
		for(Course existingCourse : existingCourses) {
			if(dateService.checkOverlap(existingCourse.getStartDate(),
				existingCourse.getEndDate(), startDate, endDate)) overlap = true;
		}
		if(overlap) {
			session.setAttribute("error", new ErrorMsg("Schedule clash for faculty user " + facultyUser.getFullname(), "/Admin/AddCourse"));
			return "redirect:/Admin/Error";
		}
		Course course = new Course(startDate, endDate, dbService.findSubjectById(courseForm.getSubjectId()),facultyUser);
		dbService.addCourse(course);
		return "redirect:/Admin/Courses";
	}
	@GetMapping("/EditCourse/{id}")
	public String editCourse(HttpSession session, Model model, @PathVariable int id) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		ArrayList<FacultyUser> facultyUsers = dbService.findFacultyUsers();
		ArrayList<Subject> subjects = dbService.findSubjects();
		Course course = dbService.findCourseById(id);
		ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByCourse(course);
		boolean allowDelete = true;
		if(enrollments.size() > 0) {
			for(Enrollment enrollment : enrollments) {
				if(!enrollment.getStatus().equals("Rejected")) allowDelete = false;
			}
		}
		CourseForm courseForm = new CourseForm();
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/editCourse");
		model.addAttribute("course", course);
		model.addAttribute("facultyUsers", facultyUsers);
		model.addAttribute("subjects", subjects);
		model.addAttribute("courseForm", courseForm);
		model.addAttribute("allowDelete", allowDelete);
		return "index";
	}
	@PostMapping("/EditCourse/{id}")
	public String addCourse(HttpSession session, @ModelAttribute CourseForm courseForm, @PathVariable int id) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		String startDate = courseForm.getStartDate();
		String endDate = courseForm.getEndDate();
		FacultyUser facultyUser = dbService.findFacultyUserById(courseForm.getFacultyUserId());
		Course course = dbService.findCourseById(id);
		if(!dateService.checkStartEndValidity(startDate, endDate)) {
			session.setAttribute("error", new ErrorMsg("Start date after end date", "/Admin/EditCourse/" + id));
			return "redirect:/Admin/Error";
		}

		ArrayList<Course> existingCourses = dbService.findCoursesByFacultyUser(facultyUser);
		boolean overlap = false;
		for(Course existingCourse : existingCourses) {
			if(dateService.checkOverlap(existingCourse.getStartDate(),
				existingCourse.getEndDate(), startDate, endDate) && existingCourse.getId() != id) overlap = true;
		}
		if(overlap) {
			session.setAttribute("error", new ErrorMsg("Schedule clash for faculty user " + facultyUser.getFullname(), "/Admin/EditCourse/" + id));
			return "redirect:/Admin/Error";
		}

		ArrayList<String> statuses = new ArrayList<String>(Arrays.asList("Pending", "Approved"));
		ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByCourseAndStatusIn(course, statuses);
		statuses = new ArrayList<String>(Arrays.asList("Pending", "Approved", "Started"));
		for(Enrollment enrollment : enrollments) {
			StudentUser studentUser = enrollment.getStudentUser();
			ArrayList<Enrollment> otherEnrollments = dbService.findEnrollmentsByStudentUserAndStatusIn(studentUser, statuses);
			for(Enrollment otherEnrollment : otherEnrollments) {
				if(dateService.checkOverlap(startDate, endDate, 
					otherEnrollment.getCourse().getStartDate(), otherEnrollment.getCourse().getEndDate())
					&& otherEnrollment.getCourse().getId() != id) {
					overlap = true;
				}
			}
		}
		if(overlap) {
			session.setAttribute("error", new ErrorMsg("Schedule clash for student user(s)", "/Admin/EditCourse/" + id));
			return "redirect:/Admin/Error";
		}

		course.setSubject(dbService.findSubjectById(courseForm.getSubjectId()));
		course.setFacultyUser(facultyUser);
		course.setStartDate(startDate);
		course.setEndDate(endDate);
		dbService.addCourse(course);
		return "redirect:/Admin/Courses";
	}
	@GetMapping("/DeleteCourse/{id}")
	public String deleteCourse(HttpSession session, @PathVariable int id) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		Course course = dbService.findCourseById(id);
		ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByCourse(course);
		if(enrollments != null) {
			for(Enrollment enrollment : enrollments) {
				dbService.deleteEnrollment(enrollment);
			}
		}
		dbService.deleteCourse(course);
		return "redirect:/Admin/Courses";
	}
	@GetMapping("/AddSubject")
	public String addSubject(HttpSession session, Model model) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		Subject subject = new Subject();
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/addSubject");
		model.addAttribute("subject", subject);
		return "index";
	}
	@PostMapping("/AddSubject")
	public String addSubject(HttpSession session, @ModelAttribute Subject subject) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		if( subject.getName().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Subject name cannot be blank.", "/Admin/AddSubject/"));
			return "redirect:/Admin/Error";
		}
		if( subject.getDescription().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Subject description cannot be blank.", "/Admin/AddSubject/"));
			return "redirect:/Admin/Error";
		}
		dbService.addSubject(subject);
		return "redirect:/Admin/Subjects";
	}
	@GetMapping("/EditSubject/{id}")
	public String editSubject(HttpSession session, Model model, @PathVariable int id) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		Subject subject = dbService.findSubjectById(id);
		ArrayList<Course> courses = dbService.findCoursesBySubject(subject);
		boolean allowDelete = true;
		if(courses.size() > 0) allowDelete = false;
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/editSubject");
		model.addAttribute("subject", subject);
		model.addAttribute("allowDelete", allowDelete);
		return "index";
	}
	@PostMapping("/EditSubject/{id}")
	public String editSubject(HttpSession session, @PathVariable int id, @ModelAttribute Subject subject) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		if( subject.getName().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Subject name cannot be blank.", "/Admin/EditSubject/" + id));
			return "redirect:/Admin/Error";
		}
		if( subject.getDescription().isEmpty()) {
			session.setAttribute("error", new ErrorMsg("Subject description cannot be blank.", "/Admin/EditSubject/" + id));
			return "redirect:/Admin/Error";
		}
		dbService.addSubject(subject);
		return "redirect:/Admin/Subjects";
	}
	@GetMapping("/DeleteSubject/{id}")
	public String deleteSubject(HttpSession session, @PathVariable int id) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		dbService.deleteSubjectById(id);
		return "redirect:/Admin/Subjects";
	}
	
	private double getGPAFromGrade(String grade){
		switch(grade) {
			case "A+": return 5.0;
			case "A": return 5.0;
			case "B+": return 4.0;
			case "B": return 3.5;
			case "B-": return 3.0;
			case "C+": return 2.5;
			case "C": return 2.0;
			case "D+": return 1.5;
			case "D": return 1.0;
			default: return 0;
		}
	}
	
	@GetMapping("/CGPA")
	public String CGPA(HttpSession session, Model model) {
		if(getAdminUserFromSession(session) == null) {
			return "redirect:/Home/AdminLogin";
		}
		ArrayList<StudentUser> studentUsers = dbService.findStudentUsers();
		ArrayList<String> statuses = new ArrayList<String>(Arrays.asList("Graded"));
		for(StudentUser studentUser : studentUsers){
			double cgpa = 0;
			ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByStudentUserAndStatusIn(studentUser, statuses);
			for(Enrollment enrollment :enrollments){
				cgpa = cgpa + getGPAFromGrade(enrollment.getGrade());
			}
			if(enrollments.size() > 0) cgpa = cgpa / enrollments.size();
			else cgpa = -1;
			studentUser.setCgpa(cgpa);
		}
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/CGPA");
		model.addAttribute("studentUsers", studentUsers);
		return "index";
	}
	
	@GetMapping("/PendingFacultyLeaves")
	public String pendingFacultyLeaves(HttpSession session, Model model) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		navbar.addItem("Logout", "/Admin/Logout/");
		ArrayList<FacultyLeave> facultyLeaves = dbService.findFacultyLeavesByStatus("Pending");
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/pendingFacultyLeaves");
		model.addAttribute("facultyLeaves", facultyLeaves);
		return "index";
	}
	
	@GetMapping("/FacultyLeavesSchedule")
	public String FacultyLeavesSchedule(HttpSession session, Model model) {
		AdminUser adminUser = getAdminUserFromSession(session);
		if(adminUser == null) {
			return "redirect:/Home/AdminLogin";
		}
		navbar.addItem("Logout", "/Admin/Logout/");
		ArrayList<FacultyLeave> facultyLeaves = dbService.findFacultyLeavesByStatus("Approved");
		model.addAttribute("sidebar", sidebar);
		model.addAttribute("navbar", navbar);
		model.addAttribute("content", "admin/facultyLeavesSchedule");
		model.addAttribute("facultyLeaves", facultyLeaves);
		return "index";
	}
	
}
