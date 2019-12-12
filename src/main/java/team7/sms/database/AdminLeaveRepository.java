package team7.sms.database;

import java.util.ArrayList;
import team7.sms.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminLeaveRepository extends JpaRepository<AdminLeave, Integer>{
	public AdminLeave findOneById (int id);
	public ArrayList<AdminLeave> findAll();
	public ArrayList<AdminLeave> findByAdminUser(AdminUser adminUser);
	public ArrayList<AdminLeave> findByStatus(String status);
	
}
