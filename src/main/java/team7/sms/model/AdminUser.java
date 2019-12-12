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
	private String fullname;
	@OneToMany(mappedBy="adminUser", fetch = FetchType.LAZY)
	private List<AdminLeave> adminLeave;
	public AdminUser() {}
	public AdminUser(String username, String password, String fullname) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
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
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public List<AdminLeave> getAdminLeave() {
		return adminLeave;
	}
	public void setAdminLeave(List<AdminLeave> adminLeave) {
		this.adminLeave = adminLeave;
	}

}
