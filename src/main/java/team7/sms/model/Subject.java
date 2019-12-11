package team7.sms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String description;
	@OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
	private List<Course> courses;
	public Subject() {
		this.courses = new ArrayList<Course>();
	}
	public Subject(String name, String description) {
		this.name = name;
		this.description = description;
		this.courses = new ArrayList<Course>();
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Course> getCourses() {
		return courses;
	}
	@Override
	public String toString() {
		return "Subject [id=" + id + ", name=" + name + ", description=" + description + ", courses=" + courses + "]";
	}
	
}
