package team7.sms.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class CourseRegister {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="course_id")
	Course course;
	
	@ManyToOne
	@JoinColumn(name="instructor_id")
	FacultyUser instructor;
	
	@ManyToOne
	@JoinColumn(name = "student_id")
	StudentUser student;
	
	//Student's grade
	private String studentGrade;
	//Instructor's comments on student
	private String studentComments;
	//Student's rating on instructor's performance on this course
	private int instructorRating;
	
	//getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public FacultyUser getInstructor() {
		return instructor;
	}
	public void setInstructor(FacultyUser instructor) {
		this.instructor = instructor;
	}
	public StudentUser getStudent() {
		return student;
	}
	public void setStudent(StudentUser student) {
		this.student = student;
	}
	public String getStudentGrade() {
		return studentGrade;
	}
	public void setStudentGrade(String studentGrade) {
		this.studentGrade = studentGrade;
	}
	public String getStudentComments() {
		return studentComments;
	}
	public void setStudentComments(String studentComments) {
		this.studentComments = studentComments;
	}
	public int getInstructorRating() {
		return instructorRating;
	}
	public void setInstructorRating(int instructorRating) {
		this.instructorRating = instructorRating;
	}
	
	
	@Override
	public String toString() {
		return "CourseRegister [id=" + id + ", course=" + course + ", instructor=" + instructor + ", student=" + student
				+ ", studentGrade=" + studentGrade + ", studentComments=" + studentComments + ", instructorRating=" + instructorRating + "]";
	}
	
	
	
	
}
