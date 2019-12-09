package team7.sms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String startDate;
	private String endDate;
	@ManyToOne (fetch = FetchType.EAGER)
	private Subject subject;
	@OneToOne (fetch = FetchType.EAGER)
	private FacultyUser facultyUser;
	@ManyToMany (fetch = FetchType.EAGER)
	private List<StudentUser> studentUsers;
	public Course() {
		this.studentUsers = new ArrayList<StudentUser>();
	}
	public Course(String startDate, String endDate, Subject subject, FacultyUser facultyUser) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.subject = subject;
		this.facultyUser = facultyUser;
		this.studentUsers = new ArrayList<StudentUser>();
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
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public FacultyUser getFacultyUser() {
		return facultyUser;
	}
	public void setFacultyUser(FacultyUser facultyUser) {
		this.facultyUser = facultyUser;
	}
	public List<StudentUser> getStudentUsers() {
		return studentUsers;
	}
}
