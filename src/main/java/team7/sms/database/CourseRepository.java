package team7.sms.database;

import java.util.ArrayList;
import team7.sms.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public class CourseRepository extends JpaRepository<Course, Integer>{
	public Course findOneByName (String name);
	public Course findOneById (int id);
	public ArrayList<Course> findAll();
}
