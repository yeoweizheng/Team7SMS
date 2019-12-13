package team7.sms;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface PasswordServiceInterface {
	
	public BCryptPasswordEncoder getPasswordEncoder();
	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder);
		
}
