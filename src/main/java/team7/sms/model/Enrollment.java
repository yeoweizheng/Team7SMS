package team7.sms.model;
import javax.persistence.*;

@Entity
public class Enrollment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne (fetch = FetchType.LAZY)
	private StudentUser studentUser;
	@ManyToOne (fetch = FetchType.LAZY)
	private Course course;
	private String status;
	private String grade;
	private int score;
	
	public Enrollment() {}
	
	public Enrollment(StudentUser studentUser, Course course) {
		this.studentUser = studentUser;
		this.course = course;
		this.status = "Pending";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public StudentUser getStudentUser() {
		return studentUser;
	}
	public void setStudentUser(StudentUser studentUser) {
		this.studentUser = studentUser;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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
		Enrollment other = (Enrollment) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
