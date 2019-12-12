package team7.sms.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AdminLeave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String startDate;
	private String endDate;
	@ManyToOne (fetch = FetchType.LAZY)
	private AdminUser adminUser;
	private String status;
	
	public AdminLeave(int id, String startDate, String endDate, AdminUser adminUser, String status) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.adminUser = adminUser;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		if (id != other.getId())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AdminLeave [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", adminUser.UserName=" + adminUser.getUsername()
				+ ", status=" + status + "]";
	}


	
}
