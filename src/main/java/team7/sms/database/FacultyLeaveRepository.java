package team7.sms.database;

import java.util.ArrayList;
import team7.sms.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyLeaveRepository extends JpaRepository<FacultyLeave, Integer>{
	public FacultyLeave findOneById (int id);
	public ArrayList<FacultyLeave> findAll();
	public ArrayList<FacultyLeave> findByFacultyUser(FacultyUser facultyUser);
	public ArrayList<FacultyLeave> findByStatus(String status);
}
