package team7.sms.database;

import team7.sms.model.*;

public interface DbServiceInterface {
	public AdminUser findAdminUserByUsername(String username);
	public StudentUser findStudentUserByUsername(String username);
	public void addAdminUser(AdminUser adminUser);
	public void addStudentUser(StudentUser studentUser);
}
