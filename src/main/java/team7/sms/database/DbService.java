package team7.sms.database;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team7.sms.model.*;

@Service
public class DbService implements DbServiceInterface{
	private AdminUserRepository adminRepo;
	private StudentUserRepository studentRepo;
	@Autowired
	public void setAdminRepo(AdminUserRepository adminRepo) {
		this.adminRepo = adminRepo;
	}
	@Autowired
	public void setStudentRepo(StudentUserRepository studentRepo) {
		this.studentRepo = studentRepo;
	}
	@Override
	@Transactional
	public AdminUser findAdminUserByUsername(String username) {
		AdminUser adminUser = adminRepo.findOneByUsername(username);
		return adminUser;
	}
	@Override
	@Transactional
	public StudentUser findStudentUserByUsername(String username) {
		StudentUser studentUser = studentRepo.findOneByUsername(username);
		return studentUser;
	}
	@Override
	@Transactional
	public ArrayList<StudentUser> findStudentUsers(){
		return studentRepo.findAll();
	}
	@Override
	@Transactional
	public void addAdminUser(AdminUser adminUser) {
		adminRepo.save(adminUser);
	}
	@Override
	@Transactional
	public void addStudentUser(StudentUser studentUser) {
		studentRepo.save(studentUser);
	}
}
