package team7.sms.database;

import team7.sms.model.*;

public interface DbServiceInterface {
	public AdminUser findAdminUserByUsername(String username);
}
