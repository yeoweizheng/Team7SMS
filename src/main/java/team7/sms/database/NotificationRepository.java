package team7.sms.database;

import java.util.ArrayList;
import team7.sms.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer>{
	public Notification findOneById (int id);
	public Notification findOneByTitle (String title);
	public ArrayList<Notification> findAll();
}