package team7.sms.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class StudentUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String fullname;
	private char gender;
	private String address;
	private String mobileNo;
	@OneToMany(mappedBy = "studentUser", fetch = FetchType.LAZY)
	private List<Enrollment> enrollments;
	private double cgpa;
	
	
	
	public StudentUser() {
		this.enrollments = new ArrayList<Enrollment>();
	}
	public StudentUser(String username, String password, String fullname, char gender, String address,
			String mobileNo) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.gender = gender;
		this.address = address;
		this.mobileNo = mobileNo;
		this.enrollments = new ArrayList<Enrollment>();
	}
	public double getCgpa() {
		return cgpa;
	}
	public void setCgpa(double cgpa) {
		this.cgpa = cgpa;
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
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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
		StudentUser other = (StudentUser) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
