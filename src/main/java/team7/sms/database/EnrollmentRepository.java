package team7.sms.database;

import team7.sms.model.*;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer>{
	public Enrollment findOneById (int id);
	public ArrayList<Enrollment> findAll();
	
	//@Query(value="FROM Enrollment e JOIN Course c WHERE c.id = :courseId", nativeQuery = true)
	public ArrayList<Enrollment> findEnrollmentByCourse(Course course);
	//@Query("SELECT e FROM enrollment e WHERE e.course_id IN (SELECT c.id FROM course WHERE c.faculty_id = id)")
	public ArrayList<Enrollment> findEnrollmentsByCourseId(int id);
	public ArrayList<Enrollment> findEnrollmentsByCourseIdIn(ArrayList<Integer> courseIds);
}
