package team7.sms.database;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import team7.sms.model.*;

public interface CourseRegisterRepository extends JpaRepository<CourseRegister, Integer> {
	public CourseRegister findCourseRegisterById(int Id);
	public CourseRegister findCourseRegisterByStudentId(int studentUserId);
	public CourseRegister findCourseRegisterByInstructorId(int facultyUserId);
	public ArrayList<CourseRegister> findAll();

}
