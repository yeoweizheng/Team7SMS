package team7.sms.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CourseForm {
	@NotNull(message = "Subject cannot be blank.")
	private int subjectId;
	@NotNull(message = "Lecturer cannot be blank.")
	private int facultyUserId;
	@NotNull(message = "Start Date required.")
//	@Min(x no. of days in advance...?)
	private String startDate;
	@NotNull(message = "End Date required.")
//	@Min(startDate + 1)
	private String endDate;
	
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public int getFacultyUserId() {
		return facultyUserId;
	}
	public void setFacultyUserId(int facultyUserId) {
		this.facultyUserId = facultyUserId;
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

}
