package team7.sms.database;

import java.util.ArrayList;
import team7.sms.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer>{
	public Subject findOneByName (String name);
	public Subject findOneById (int id);
	public ArrayList<Subject> findAll();
}
