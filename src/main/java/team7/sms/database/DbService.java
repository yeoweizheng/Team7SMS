package team7.sms.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.type.NTextType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team7.sms.model.*;

@Service
public class DbService implements DbServiceInterface {
	private AdminUserRepository adminRepo;
	private StudentUserRepository studentRepo;
	private FacultyUserRepository facultyRepo;
	private CourseRepository courseRepo;
	private SubjectRepository subjectRepo;
	private EnrollmentRepository enrollmentRepo;
	private FacultyLeaveRepository facultyLeaveRepo;
	private NotificationRepository notificationRepo;

	@Autowired
	public void setAdminRepo(AdminUserRepository adminRepo) {
		this.adminRepo = adminRepo;
	}

	@Autowired
	public void setStudentRepo(StudentUserRepository studentRepo) {
		this.studentRepo = studentRepo;
	}

	@Autowired
	public void setFacultyRepo(FacultyUserRepository facultyRepo) {
		this.facultyRepo = facultyRepo;
	}

	@Autowired
	public void setCourseRepo(CourseRepository courseRepo) {
		this.courseRepo = courseRepo;
	}

	@Autowired
	public void setSubjectRepo(SubjectRepository subjectRepo) {
		this.subjectRepo = subjectRepo;
	}

	@Autowired
	public void setEnrollmentRepo(EnrollmentRepository enrollmentRepo) {
		this.enrollmentRepo = enrollmentRepo;
	}

	@Autowired
	public void setFacultyLeaveRepo(FacultyLeaveRepository facultyLeaveRepo) {
		this.facultyLeaveRepo = facultyLeaveRepo;
	}
	
	@Autowired
	public void setNotificationRepo(NotificationRepository notificationRepo) {
		this.notificationRepo = notificationRepo;
	}

	@Override
	@Transactional
	public AdminUser findAdminUserById(int id) {
		AdminUser adminUser = adminRepo.findOneById(id);
		return adminUser;
	}

	@Override
	@Transactional
	public AdminUser findAdminUserByUsername(String username) {
		AdminUser adminUser = adminRepo.findOneByUsername(username);
		return adminUser;
	}

	@Override
	@Transactional
	public void addAdminUser(AdminUser adminUser) {
		adminRepo.save(adminUser);
	}

	@Override
	@Transactional
	public StudentUser findStudentUserByUsername(String username) {
		StudentUser studentUser = studentRepo.findOneByUsername(username);
		return studentUser;
	}

	@Override
	@Transactional
	public StudentUser findStudentUserById(int id) {
		StudentUser studentUser = studentRepo.findOneById(id);
		return studentUser;
	}

	@Override
	@Transactional
	public ArrayList<StudentUser> findStudentUsers() {
		return studentRepo.findAll();
	}
	
	@Override
	@Transactional
	public void addStudentUser(StudentUser studentUser) {
		studentRepo.save(studentUser);
	}

	@Override
	@Transactional
	public void deleteStudentUser(StudentUser studentUser) {
		studentRepo.delete(studentUser);
	}

	@Override
	@Transactional
	public FacultyUser findFacultyUserByUsername(String username) {
		FacultyUser facultyUser = facultyRepo.findOneByUsername(username);
		return facultyUser;
	}

	@Override
	@Transactional
	public FacultyUser findFacultyUserById(int id) {
		FacultyUser facultyUser = facultyRepo.findOneById(id);
		return facultyUser;
	}

	@Override
	@Transactional
	public ArrayList<FacultyUser> findFacultyUsers() {
		return facultyRepo.findAll();
	}

	@Override
	@Transactional
	public void addFacultyUser(FacultyUser facultyUser) {
		facultyRepo.save(facultyUser);
	}

	@Override
	@Transactional
	public void deleteFacultyUserById(int id) {
		facultyRepo.delete(facultyRepo.findOneById(id));
	}

	@Override
	@Transactional
	public Course findCourseById(int id) {
		return courseRepo.findOneById(id);
	}

	@Override
	@Transactional
	public ArrayList<Course> findCoursesBySubject(Subject subject) {
		return courseRepo.findBySubject(subject);
	}

	@Override
	@Transactional
	public void deleteCourse(Course course) {
		courseRepo.delete(course);
	}

	@Override
	@Transactional
	public ArrayList<Course> findCourses() {
		return courseRepo.findAll();
	}

	@Override
	@Transactional
	public void addCourse(Course course) {
		courseRepo.save(course);
	}

	@Override
	@Transactional
	public ArrayList<Course> findCoursesByFacultyUser(FacultyUser facultyUser) {
		return courseRepo.findByFacultyUser(facultyUser);
	}
	@Override
	@Transactional
	public ArrayList<Course> findCoursesByStatus(String status) {
		return courseRepo.findByStatus(status);
	}
	@Override
	@Transactional
	public ArrayList<Course> findCoursesByFacultyUserAndStatusIn(FacultyUser facultyUser, Collection<String> statuses) {
		return courseRepo.findByFacultyUserAndStatusIn(facultyUser, statuses);
	}
	
	@Override
	@Transactional
	public void addSubject(Subject subject){
		subjectRepo.save(subject);
	}

	@Override
	@Transactional
	public Subject findSubjectById(int id) {
		return subjectRepo.findOneById(id);
	}

	@Override
	@Transactional
	public void deleteSubjectById(int id) {
		subjectRepo.delete(subjectRepo.findOneById(id));
	}

	@Override
	@Transactional
	public Enrollment findEnrollmentById(int id) {
		Enrollment enrollment = enrollmentRepo.findOneById(id);
		return enrollment;
	}

	@Override
	@Transactional
	public void deleteEnrollment(Enrollment enrollment) {
		enrollmentRepo.delete(enrollment);
	}

	@Override
	@Transactional
	public ArrayList<Subject> findSubjects() {
		return subjectRepo.findAll();
	}
	
	@Override
	@Transactional
	public void addEnrollment(Enrollment enrollment) {
		enrollmentRepo.save(enrollment);
	}
	
	@Override
	@Transactional
	public ArrayList<Enrollment> findEnrollments() { 
		return enrollmentRepo.findAll(); 
	}

	@Override
	@Transactional
	public ArrayList<Enrollment> findEnrollmentsByCourse(Course course) {
		return enrollmentRepo.findByCourse(course);
	}
	
	@Override
	@Transactional
	public ArrayList<Enrollment> findEnrollmentsByStudentUser(StudentUser studentUser) {
		return enrollmentRepo.findByStudentUser(studentUser);
	}

	@Override
	@Transactional
	public Enrollment findEnrollmentByStudentUserAndCourse(StudentUser studentUser, Course course) {
		return enrollmentRepo.findOneByStudentUserAndCourse(studentUser, course);
	}
	
	@Override
	@Transactional
	public ArrayList<Enrollment> findEnrollmentsByStatus(String status){
		return enrollmentRepo.findByStatus(status);
	}

	@Override
	@Transactional
	public ArrayList<Enrollment> findEnrollmentsByCourseAndStatusIn(Course course, Collection<String> statuses){
		return enrollmentRepo.findByCourseAndStatusIn(course, statuses);
	}

	@Override
	@Transactional
	public ArrayList<Enrollment> findEnrollmentsByStudentUserAndStatusIn(StudentUser studentUser, Collection<String> statuses){
		return enrollmentRepo.findByStudentUserAndStatusIn(studentUser, statuses);
	}
	@Override
	@Transactional
	public FacultyLeave findFacultyLeaveById(int id) {
		return facultyLeaveRepo.findOneById(id);
	}
	@Override
	@Transactional
	public ArrayList<FacultyLeave> findFacultyLeavesByFacultyUser(FacultyUser facultyUser) {
		return facultyLeaveRepo.findByFacultyUser(facultyUser);
	}
	@Override
	@Transactional
	public ArrayList<FacultyLeave> findFacultyLeavesByStatus(String status) {
		ArrayList<FacultyLeave> facultyLeaves = facultyLeaveRepo.findByStatus(status);
		return facultyLeaves;
	}
	@Override
	@Transactional
	public void addFacultyLeave(FacultyLeave facultyLeave) {
		facultyLeaveRepo.save(facultyLeave);
	}
	@Override
	@Transactional
	public void deleteFacultyLeave(FacultyLeave facultyLeave) {
		facultyLeaveRepo.delete(facultyLeave);
	}
	@Override
	@Transactional
	public ArrayList<Notification> findNotifications() {
		return notificationRepo.findAll();
	}
	@Override
	@Transactional
	public void addNotification(Notification notification){
		notificationRepo.save(notification);
	}

	@Override
	@Transactional
	public Notification findNotificationById(int id) {
		return notificationRepo.findOneById(id);
	}

	@Override
	@Transactional
	public void deleteNotificationById(int id) {
		notificationRepo.delete(notificationRepo.findOneById(id));
	}
	
	@Override
	@Transactional
	public ArrayList<Notification> findNotificationsByStudentUser(StudentUser studentUser) {
		return notificationRepo.findByStudentUsers(studentUser);
	}
}
