package team7.sms.database;

import java.util.ArrayList;
import java.util.Collection;

import team7.sms.model.*;

public interface DbServiceInterface {
	public AdminUser findAdminUserByUsername(String username);
	public AdminUser findAdminUserById(int id);
	public void addAdminUser(AdminUser adminUser);
	
	public StudentUser findStudentUserByUsername(String username);
	public StudentUser findStudentUserById(int id);
	public ArrayList<StudentUser> findStudentUsers();
	public void addStudentUser(StudentUser studentUser);
	public void deleteStudentUser(StudentUser studentUser);
	
	public FacultyUser findFacultyUserByUsername(String username);
	public FacultyUser findFacultyUserById(int id);
	public void deleteFacultyUserById(int id);
	public ArrayList<FacultyUser> findFacultyUsers();
	public void addFacultyUser(FacultyUser facultyUser);
	
	public Course findCourseById(int id);
	public void deleteCourse(Course course);
	public ArrayList<Course> findCourses();
	public void addCourse(Course course);
	public ArrayList<Course> findCoursesByFacultyUser(FacultyUser facultyUser);
	public ArrayList<Course> findCoursesByStatus(String status);
	public ArrayList<Course> findCoursesByFacultyUserAndStatusIn(FacultyUser facultyUser, Collection<String> status);
	public ArrayList<Course> findCoursesBySubject(Subject subject);
	
	public void addSubject(Subject subject);
	public Subject findSubjectById(int id);
	public ArrayList<Subject> findSubjects();
	public void deleteSubjectById(int id);
	public ArrayList<Enrollment> findEnrollments();
	
	public Enrollment findEnrollmentById(int id);
	public void deleteEnrollment(Enrollment enrollment);
	public void addEnrollment(Enrollment enrollment);
	public ArrayList<Enrollment> findEnrollmentsByCourse(Course course);
	public ArrayList<Enrollment> findEnrollmentsByStudentUser(StudentUser studentUser);
	public Enrollment findEnrollmentByStudentUserAndCourse(StudentUser studentUser, Course course);
	public ArrayList<Enrollment> findEnrollmentsByCourseAndStatusIn(Course course, Collection<String> statuses);
	public ArrayList<Enrollment> findEnrollmentsByStatus(String status);
	public ArrayList<Enrollment> findEnrollmentsByStudentUserAndStatusIn(StudentUser studentUser, Collection<String> statuses);
	
	public AdminLeave findAdminLeaveById(int id);
	public ArrayList<AdminLeave> findAdminLeavesByAdminUser(AdminUser adminUser);
	public ArrayList<AdminLeave> findAdminLeavesByStatus(String status);
	public void addAdminLeave(AdminLeave adminLeave);
	public void deleteAdminLeave(AdminLeave adminLeave);
	
	public FacultyLeave findFacultyLeaveById(int id);
	public ArrayList<FacultyLeave> findFacultyLeavesByFacultyUser(FacultyUser facultyUser);
	public ArrayList<FacultyLeave> findFacultyLeavesByStatus(String status);
	public void addFacultyLeave(FacultyLeave facultyLeave);
	public void deleteFacultyLeave(FacultyLeave facultyLeave);
	
}
