package team7.sms.database;

import java.util.ArrayList;

import team7.sms.model.*;

public interface DbServiceInterface {
	public AdminUser findAdminUserByUsername(String username);
	public StudentUser findStudentUserByUsername(String username);
	public StudentUser findStudentUserById(int id);
	public void deleteStudentUserById(int id);
	public ArrayList<StudentUser> findStudentUsers();
	public void addAdminUser(AdminUser adminUser);
	public void addStudentUser(StudentUser studentUser);
}
