package team7.sms.database;

import team7.sms.model.*;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer>{
	public Enrollment findOneById (int id);
	public ArrayList<Enrollment> findAll();
}
