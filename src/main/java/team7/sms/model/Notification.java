package team7.sms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	@ManyToMany(fetch = FetchType.LAZY)
	private List<StudentUser> studentUsers;
	private String message;
	@ManyToOne(fetch = FetchType.LAZY)
	private Course course;

	public Notification() {
		this.studentUsers = new ArrayList<StudentUser>();
	}
	public Notification(String title, String message, Course course) {
		this.title = title;
		this.message = message;
		this.course = course;
		this.studentUsers = new ArrayList<StudentUser>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<StudentUser> getStudentUsers() {
		return studentUsers;
	}
	public void setStudentUsers(List<StudentUser> studentUsers) {
		this.studentUsers = studentUsers;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
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
		Notification other = (Notification) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
