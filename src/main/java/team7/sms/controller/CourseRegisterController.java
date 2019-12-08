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
import team7.sms.database.CourseRegisterRepository;
import team7.sms.database.DbService;
import team7.sms.model.*;

@Controller
@RequestMapping("/CourseRegister")
public class CourseRegisterController {
	
	private DbService dbService;
	@Autowired
	public void setDbService(DbService dbService) {
		this.dbService = dbService;
	}
	
	//CourseRegister DB functions:
	/*
	 * public CourseRegister findCourseRegisterById(int Id); 
	 * public CourseRegister findCourseRegisterByStudentId(int studentUserId); 
	 * public CourseRegister findCourseRegisterByInstructorId(int facultyUserId); 
	 * public void deleteCourseRegisterById(int id); 
	 * public ArrayList<CourseRegister> findCourseRegister(); 
	 * public void addCourseRegister(CourseRegister courseRegister);
	 */
	
	
}
