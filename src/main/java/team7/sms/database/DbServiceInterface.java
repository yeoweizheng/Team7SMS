package team7.sms.database;

import java.util.ArrayList;

import team7.sms.model.*;

public interface DbServiceInterface {
	public AdminUser findAdminUserByUsername(String username);
	public void addAdminUser(AdminUser adminUser);
	
	public StudentUser findStudentUserByUsername(String username);
	public StudentUser findStudentUserById(int id);
	public void deleteStudentUserById(int id);
	public ArrayList<StudentUser> findStudentUsers();
	public void addStudentUser(StudentUser studentUser);
	
	public FacultyUser findFacultyUserByUsername(String username);
	public FacultyUser findFacultyUserById(int id);
	public void deleteFacultyUserById(int id);
	public ArrayList<FacultyUser> findFacultyUsers();
	public void addFacultyUser(FacultyUser facultyUser);
}
