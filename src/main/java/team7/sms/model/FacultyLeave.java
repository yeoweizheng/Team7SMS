package team7.sms.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FacultyLeave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String startDate;
	private String endDate;
	@ManyToOne (fetch = FetchType.LAZY)
	private FacultyUser facultyUser;
	private String status;
	
	public FacultyLeave(String startDate, String endDate, FacultyUser facultyUser) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.facultyUser = facultyUser;
		this.status = "Pending";
	}
	


	public FacultyLeave() {
		// TODO Auto-generated constructor stub
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
		return "FacultyLeave [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", facultyUser.FullName="
				+ facultyUser.getFullname() + ", status=" + status + "]";
	}


	
}
