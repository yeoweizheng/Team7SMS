package team7.sms.database;

import java.util.ArrayList;
import java.util.List;

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
	
	public Course findCourseById(int id);
	public void deleteCourseById(int id);
	public ArrayList<Course> findCourses();
	public void addCourse(Course course);
	public ArrayList<Course> findCoursesByFacultyUser(FacultyUser facultyUser);
	
	public void addSubject(Subject subject);
	public Subject findSubjectById(int id);
	public ArrayList<Subject> findSubjects();
	
	public void addEnrollment(Enrollment enrollment);
	public ArrayList<Enrollment> findEnrollments();
	public ArrayList<Enrollment> findEnrollmentsByCourse(Course course);
	
}
