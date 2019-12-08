package team7.sms.database;

import java.util.ArrayList;

import team7.sms.model.*;

public interface DbServiceInterface {
	public AdminUser findAdminUserByUsername(String username);
	public void addAdminUser(AdminUser adminUser);
	
	public StudentUser findStudentUserByUsername(String username);
	public StudentUser findStudentUserById(int id);
	public void deleteStudentUserById(int id);
	public ArrayList<StudentUser> findStudentUsers();
	public void addStudentUser(StudentUser studentUser);
	
	public FacultyUser findFacultyUserByUsername(String username);
	public FacultyUser findFacultyUserById(int id);
	public void deleteFacultyUserById(int id);
	public ArrayList<FacultyUser> findFacultyUsers();
	public void addFacultyUser(FacultyUser facultyUser);
	
	public Course findCourseByName(String name);
	public Course findCourseById(int id);
	public void deleteCourseById(int id);
	public ArrayList<Course> findCourse();
	public void addCourse(Course course);
	
	public CourseRegister findCourseRegisterById(int Id);
	public CourseRegister findCourseRegisterByStudentId(int studentUserId);
	public CourseRegister findCourseRegisterByInstructorId(int facultyUserId);
	public void deleteCourseRegisterById(int id);
	public ArrayList<CourseRegister> findCourseRegister();
	public void addCourseRegister(CourseRegister courseRegister);
	
	
	
}
