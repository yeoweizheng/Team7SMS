package team7.sms;

import org.hibernate.boot.model.relational.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import team7.sms.controller.*;

import team7.sms.controller.HomeController;
import team7.sms.database.AdminUserRepository;
import team7.sms.database.DbService;
import team7.sms.database.StudentUserRepository;
import team7.sms.model.AdminUser;
import team7.sms.model.StudentUser;

@SpringBootApplication
public class Team7SmsApplication {

	@Autowired
	private AdminUserRepository adminRepo;
	@Autowired
	private StudentUserRepository studentRepo;

	private DbService dbService;
	@Autowired
	public void setDbService(DbService dbService) {
		this.dbService = dbService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Team7SmsApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner initializeControllers() {
		return (args) -> {
			HomeController.init();
			AdminController.init();
			StudentController.init();
		};
	}
	
	@Bean
	public CommandLineRunner initializeDb() {
		return (args) -> {
			dbService.addAdminUser(new AdminUser("admin", "password"));
			dbService.addStudentUser(new StudentUser("student", "password"));
		};
	}

}
