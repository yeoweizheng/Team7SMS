package team7.sms.database;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team7.sms.model.*;

@Service
public class DbService implements DbServiceInterface{
	private AdminUserRepository adminRepo;
	private StudentUserRepository studentRepo;
	private FacultyUserRepository facultyRepo;
	private CourseRepository courseRepo;
	private SubjectRepository subjectRepo;
	private EnrollmentRepository enrollmentRepo;
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
	public ArrayList<StudentUser> findStudentUsers(){
		return studentRepo.findAll();
	}
	
	@Override
	@Transactional
	public void addStudentUser(StudentUser studentUser) {
		studentRepo.save(studentUser);
	}
	@Override
	@Transactional
	public void deleteStudentUserById(int id) {
		studentRepo.delete(studentRepo.findOneById(id));
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
	public ArrayList<FacultyUser> findFacultyUsers(){
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
		Course course = courseRepo.findOneById(id);
		return course;
	}
	@Override
	@Transactional
	public void deleteCourseById(int id) {
	courseRepo.delete(courseRepo.findOneById(id));
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
	public void addEnrollment(Enrollment enrollment) {
		enrollmentRepo.save(enrollment);
	}
	
}
