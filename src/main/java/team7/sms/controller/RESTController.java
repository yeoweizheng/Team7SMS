package team7.sms.controller;

import javax.servlet.http.HttpSession;

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
	@PostMapping("/enrollCourse")
	public ResponseEntity enrollCourse(@RequestParam int studentUserId, @RequestParam int courseId) {
		log.info("" + studentUserId);
		dbService.addEnrollment(new Enrollment(dbService.findStudentUserById(studentUserId),
				dbService.findCourseById(courseId), "Pending", "Pending"));
		return new ResponseEntity(HttpStatus.OK);
	}
}
