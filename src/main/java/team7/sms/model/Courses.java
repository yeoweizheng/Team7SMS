package team7.sms.model;

import javax.persistence.*;

@Entity
public class Courses {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String starting_date;
	private String ending_date;
	private String faculty;
	public Courses() {}
	public Courses(int id, String name, String starting_date, String ending_date, String faculty) {
		super();
		this.id = id;
		this.name = name;
		this.starting_date = starting_date;
		this.ending_date = ending_date;
		this.faculty = faculty;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStarting_date() {
		return starting_date;
	}
	public void setStarting_date(String starting_date) {
		this.starting_date = starting_date;
	}
	public String getEnding_date() {
		return ending_date;
	}
	public void setEnding_date(String ending_date) {
		this.ending_date = ending_date;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	@Override
	public String toString() {
		return "Courses [id=" + id + ", name=" + name + ", starting_date=" + starting_date + ", ending_date="
				+ ending_date + ", faculty=" + faculty + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ending_date == null) ? 0 : ending_date.hashCode());
		result = prime * result + ((faculty == null) ? 0 : faculty.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((starting_date == null) ? 0 : starting_date.hashCode());
		return result;
	}


}
