package team7.sms;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import team7.sms.model.Course;

@Component
public class CourseComparator implements Comparator<Course>{
	private DateService dateService;
	@Autowired
	public void setDateService(DateService dateService) {
		this.dateService = dateService;
	}
	@Override
	public int compare(Course o1, Course o2) {
		if(dateService.parseLocalDate(o1.getStartDate()).isBefore(dateService.parseLocalDate(o2.getStartDate()))) {
			return -1;
		} else {
			return 1;
		}
	}
}
