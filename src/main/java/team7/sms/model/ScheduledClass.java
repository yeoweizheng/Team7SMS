package team7.sms.model;

public class ScheduledClass {
	private String date;
	private String subjectName;
	public ScheduledClass(String date, String subjectName) {
		this.date = date;
		this.subjectName = subjectName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
}
