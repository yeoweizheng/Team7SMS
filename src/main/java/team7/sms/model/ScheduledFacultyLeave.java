package team7.sms.model;

public class ScheduledFacultyLeave {

	private String date;
	private FacultyLeave facultyLeave;
	private FacultyUser facultyUser;
	private String facultyfullname;
	
	public ScheduledFacultyLeave() {}
	
	public ScheduledFacultyLeave(String date, String facultyfullname) {
		this.date=date;
		this.facultyfullname = facultyfullname;
	}

	public FacultyLeave getFacultyLeave() {
		return facultyLeave;
	}

	public void setFacultyLeave(FacultyLeave facultyLeave) {
		this.facultyLeave = facultyLeave;
	}

	public FacultyUser getFacultyUser() {
		return facultyUser;
	}

	public void setFacultyUser(FacultyUser facultyUser) {
		this.facultyUser = facultyUser;
	}
	
}
