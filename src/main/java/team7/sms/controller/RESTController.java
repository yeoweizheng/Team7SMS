package team7.sms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import team7.sms.Team7SmsApplication;
import team7.sms.database.DbService;
import team7.sms.model.*;

@RestController
@RequestMapping("/api")
public class RESTController {
	private static final Logger log = LoggerFactory.getLogger(Team7SmsApplication.class);
	private DbService dbService;
	@Autowired
	public void setDbService(DbService dbService) {
		this.dbService = dbService;
	}
	private StudentUser getStudentUserFromSession(HttpSession session) {
		if(session.getAttribute("studentUser") == null) return null;
		StudentUser studentUser = dbService.findStudentUserById(Integer.parseInt(session.getAttribute("studentUser").toString()));
		return studentUser;
	}
	private AdminUser getAdminUserFromSession(HttpSession session) {
		if(session.getAttribute("adminUser") == null) return null;
		AdminUser adminUser = dbService.findAdminUserById(Integer.parseInt(session.getAttribute("adminUser").toString()));
		return adminUser;
	}
	private FacultyUser getFacultyUserFromSession(HttpSession session) {
		if(session.getAttribute("facultyUser") == null) return null;
		FacultyUser facultyUser = dbService.findFacultyUserById(Integer.parseInt(session.getAttribute("facultyUser").toString()));
		return facultyUser;
	}
	@PostMapping("/enrollCourse")
	public ResponseEntity enrollCourse(HttpSession session, @RequestParam int studentUserId, @RequestParam int courseId) {
		if(getStudentUserFromSession(session) == null) return new ResponseEntity(HttpStatus.FORBIDDEN);
		dbService.addEnrollment(new Enrollment(dbService.findStudentUserById(studentUserId),
				dbService.findCourseById(courseId)));
		return new ResponseEntity(HttpStatus.OK);
	}
	@PostMapping("/approveEnrollment")
	public ResponseEntity approveEnrollment(HttpSession session, @RequestParam int enrollmentId) {
		if(getAdminUserFromSession(session) == null) return new ResponseEntity(HttpStatus.FORBIDDEN);
		Enrollment enrollment = dbService.findEnrollmentById(enrollmentId);
		enrollment.setStatus("Approved");
		dbService.addEnrollment(enrollment);
		return new ResponseEntity(HttpStatus.OK);
	}
	@PostMapping("/rejectEnrollment")
	public ResponseEntity rejectEnrollment(HttpSession session, @RequestParam int enrollmentId) {
		if(getAdminUserFromSession(session) == null) return new ResponseEntity(HttpStatus.FORBIDDEN);
		Enrollment enrollment = dbService.findEnrollmentById(enrollmentId);
		enrollment.setStatus("Rejected");
		dbService.addEnrollment(enrollment);
		return new ResponseEntity(HttpStatus.OK);
	}
	@PostMapping("/startCourse")
	public ResponseEntity startCourse(HttpSession session, @RequestParam int courseId) {
		if(getAdminUserFromSession(session) == null) return new ResponseEntity(HttpStatus.FORBIDDEN);
		Course course = dbService.findCourseById(courseId);
		course.setStatus("Started");
		dbService.addCourse(course);
		ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByCourse(course);
		for(Enrollment enrollment : enrollments) {
			if(enrollment.getStatus().equals("Pending")) enrollment.setStatus("Rejected");
			if(enrollment.getStatus().equals("Approved")) enrollment.setStatus("Started");
			dbService.addEnrollment(enrollment);
		}
		return new ResponseEntity(HttpStatus.OK);
	}
	@PostMapping("/finishCourse")
	public ResponseEntity finishCourse(HttpSession session, @RequestParam int courseId) {
		if(getAdminUserFromSession(session) == null) return new ResponseEntity(HttpStatus.FORBIDDEN);
		Course course = dbService.findCourseById(courseId);
		course.setStatus("Finished");
		ArrayList<Enrollment> enrollments = dbService.findEnrollmentsByCourse(course);
		dbService.addCourse(course);
		for(Enrollment enrollment : enrollments) {
			if(enrollment.getStatus().equals("Started")) enrollment.setStatus("Finished");
			dbService.addEnrollment(enrollment);
		}
		return new ResponseEntity(HttpStatus.OK);
	}
	@PostMapping("/submitScores")
	public ResponseEntity submitScores(HttpSession session, @RequestParam String scoreFormsJSON, @RequestParam int courseId) {
		if(getFacultyUserFromSession(session) == null) return new ResponseEntity(HttpStatus.FORBIDDEN);
		ObjectMapper mapper = new ObjectMapper();
		try {
			ScoreForm[] scoreForms = mapper.readValue(scoreFormsJSON, ScoreForm[].class);
			for(ScoreForm scoreForm : scoreForms) {
				Enrollment enrollment = dbService.findEnrollmentById(scoreForm.getId());
				enrollment.setScore(scoreForm.getScore());
				enrollment.setGrade(computeGrade(scoreForm.getScore()));
				enrollment.setStatus("Graded");
				dbService.addEnrollment(enrollment);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Course course = dbService.findCourseById(courseId);
		course.setStatus("Graded");
		dbService.addCourse(course);
		return new ResponseEntity(HttpStatus.OK);
	}
	private String computeGrade(int score) {
		if(score >= 90) return "A+";
		if(score >= 85) return "A";
		if(score >= 80) return "A-";
		if(score >= 70) return "B+";
		if(score >= 60) return "B";
		if(score >= 50) return "B-";
		if(score >= 40) return "C+";
		if(score >= 30) return "C";
		if(score >= 20) return "D+";
		if(score >= 10) return "D";
		return "F";
	}
}
