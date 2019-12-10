package team7.sms.database;

import java.util.ArrayList;
import team7.sms.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer>{
	public Course findOneById (int id);
	public ArrayList<Course> findAll();
	public ArrayList<Course> findAllByFacultyUser(FacultyUser lecturer);
}
