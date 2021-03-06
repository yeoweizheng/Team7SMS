package team7.sms.database;

import java.util.ArrayList;
import java.util.Collection;

import team7.sms.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer>{
	public Course findOneById (int id);
	public ArrayList<Course> findAll();
	public ArrayList<Course> findByFacultyUser(FacultyUser facultyUser);
	public ArrayList<Course> findByStatus(String status);
	public ArrayList<Course> findByFacultyUserAndStatusIn(FacultyUser facultyUser, Collection<String> statuses);
	public ArrayList<Course> findBySubject(Subject subject);
}
