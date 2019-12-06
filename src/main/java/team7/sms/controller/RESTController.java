package team7.sms.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team7.sms.Team7SmsApplication;
import team7.sms.model.StudentUser;

@RestController
@RequestMapping("/api")
public class RESTController {
	private static final Logger log = LoggerFactory.getLogger(Team7SmsApplication.class);
	
}
