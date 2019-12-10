package team7.sms.database;

import org.springframework.data.jpa.repository.JpaRepository;

import team7.sms.model.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, Integer>{
	public AdminUser findOneByUsername(String username);
	public AdminUser findOneById(int id);
}
