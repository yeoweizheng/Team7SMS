package team7.sms.database;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team7.sms.model.*;

@Service
public class DbService implements DbServiceInterface{
	private AdminUserRepository adminRepo;

	@Autowired
	public void setAdminRepo(AdminUserRepository adminRepo) {
		this.adminRepo = adminRepo;
	}
	
	@Override
	@Transactional
	public AdminUser findAdminUserByUsername(String username) {
		AdminUser adminUser = adminRepo.findOneByUsername(username);
		return adminUser;
	}
}
