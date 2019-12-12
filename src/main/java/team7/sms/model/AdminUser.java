package team7.sms.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class AdminUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	@OneToMany(mappedBy="adminUser", fetch = FetchType.LAZY)
	private List<AdminLeave> adminLeave;
	public AdminUser() {}
	public AdminUser(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<AdminLeave> getAdminLeave() {
		return adminLeave;
	}
	public void setAdminLeave(List<AdminLeave> adminLeave) {
		this.adminLeave = adminLeave;
	}

}
