package team7.sms.database;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import team7.sms.model.AdminUser;
import team7.sms.model.FacultyUser;

public interface FacultyUserRepository extends JpaRepository<FacultyUser, Integer> {
	public FacultyUser findOneByUsername(String username);
	public FacultyUser findOneById(int id);
	public ArrayList<FacultyUser> findAll();
}
