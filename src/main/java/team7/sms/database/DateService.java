package team7.sms.database;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class DateService implements DateServiceInterface {
	private DateTimeFormatter formatter;
	public DateService() {
		formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
	}
	public boolean checkOverlap(String startDateStr1, String endDateStr1, String startDateStr2, String endDateStr2) {
		LocalDate startDate1 = LocalDate.parse(startDateStr1, formatter);
		LocalDate endDate1 = LocalDate.parse(endDateStr1, formatter);
		LocalDate startDate2 = LocalDate.parse(startDateStr2, formatter);
		LocalDate endDate2 = LocalDate.parse(endDateStr2, formatter);
		if(startDate1.isEqual(startDate2)) return true;
		if(startDate1.isEqual(endDate2)) return true;
		if(endDate1.isEqual(startDate2)) return true;
		if(endDate1.isEqual(endDate2)) return true;
		if(startDate1.isBefore(startDate2) && startDate2.isBefore(endDate1)) return true;
		if(startDate2.isBefore(startDate1) && startDate1.isBefore(endDate2)) return true;
		return false;
	}
	public boolean checkStartEndValidity(String startDateStr, String endDateStr) {
		LocalDate startDate = LocalDate.parse(startDateStr, formatter);
		LocalDate endDate = LocalDate.parse(endDateStr, formatter);
		if(endDate.isAfter(startDate) || endDate.isEqual(startDate)) {
			return true;
		} else {
			return false;
		}
	}
}
