package team7.sms;

import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class PasswordService implements PasswordServiceInterface {
	private BCryptPasswordEncoder passwordEncoder;
	
	public PasswordService() {
		passwordEncoder = new BCryptPasswordEncoder();
	}

	public BCryptPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
		
	
	
}
