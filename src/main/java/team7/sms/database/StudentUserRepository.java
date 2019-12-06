package team7.sms.database;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import team7.sms.model.AdminUser;
import team7.sms.model.StudentUser;

public interface StudentUserRepository extends JpaRepository<StudentUser, Integer>{
	public StudentUser findOneByUsername(String username);
	public StudentUser findOneById(int id);
	public ArrayList<StudentUser> findAll();
}
