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
	@OneToMany(mappedBy = "notification", fetch = FetchType.LAZY)
	private List<StudentUser> studentUsers;
	private String notification;

	public Notification() {
		this.studentUsers = new ArrayList<StudentUser>();
	}
	public Notification(String title, String notification) {
		this.title = title;
		this.notification = notification;
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
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	
	@Override
	public String toString() {
		return "Email [id=" + id + ", title=" + title + ", notification=" + notification + "]";
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
