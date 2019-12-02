package team7.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import team7.sms.controller.*;

import team7.sms.controller.HomeController;

@SpringBootApplication
public class Team7SmsApplication {

	public static void main(String[] args) {
		HomeController.init();
		SpringApplication.run(Team7SmsApplication.class, args);
	}

}
