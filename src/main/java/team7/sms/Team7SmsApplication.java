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
import team7.sms.model.AdminUser;

@SpringBootApplication
public class Team7SmsApplication {

	@Autowired
	private AdminUserRepository adminRepo;

	@Autowired
	private DbService dbService;
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
		};
	}
	
	@Bean
	public CommandLineRunner initializeDb() {
		return (args) -> {
			adminRepo.save(new AdminUser("admin", "password"));
		};
	}

}
