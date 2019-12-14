package team7.sms.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CourseForm {
	private int subjectId;
	private int facultyUserId;
	private String startDate;
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
