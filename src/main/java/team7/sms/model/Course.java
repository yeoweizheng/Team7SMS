package team7.sms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Course implements Comparable<Course> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String startDate;
	private String endDate;
	@ManyToOne (fetch = FetchType.LAZY)
	private Subject subject;
	@ManyToOne (fetch = FetchType.LAZY)
	private FacultyUser facultyUser;
	@OneToMany (mappedBy = "course", fetch = FetchType.LAZY)
	private List<Enrollment> enrollments;
	@OneToMany (mappedBy = "course", fetch = FetchType.LAZY)
	private List<Notification> notifications;
	private String status;
	public Course() {
		this.enrollments = new ArrayList<Enrollment>();
		this.notifications = new ArrayList<Notification>();
	}
	public Course(String startDate, String endDate, Subject subject, FacultyUser facultyUser) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.subject = subject;
		this.facultyUser = facultyUser;
		this.enrollments = new ArrayList<Enrollment>();
		this.notifications = new ArrayList<Notification>();
		this.status = "Created";
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Enrollment> getEnrollments() {
		return enrollments;
	}
	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}
	public List<Notification> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
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
		Course other = (Course) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public int compareTo(Course o) {
		// Ignore, comparison will be done by CourseComparator
		return 0;
	}
}
