package team7.sms;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class DateService implements DateServiceInterface {
	private DateTimeFormatter formatter;
	public DateService() {
		formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
	}
	public LocalDate parseLocalDate(String inputString) {
		return LocalDate.parse(inputString, formatter);
	}
	public boolean checkOverlap(String startDateStr1, String endDateStr1, String startDateStr2, String endDateStr2) {
		LocalDate startDate1 = parseLocalDate(startDateStr1);
		LocalDate endDate1 = parseLocalDate(endDateStr1);
		LocalDate startDate2 = parseLocalDate(startDateStr2);
		LocalDate endDate2 = parseLocalDate(endDateStr2);
		if(startDate1.isEqual(startDate2)) return true;
		if(startDate1.isEqual(endDate2)) return true;
		if(endDate1.isEqual(startDate2)) return true;
		if(endDate1.isEqual(endDate2)) return true;
		if(startDate1.isBefore(startDate2) && startDate2.isBefore(endDate1)) return true;
		if(startDate2.isBefore(startDate1) && startDate1.isBefore(endDate2)) return true;
		return false;
	}
	public boolean checkStartEndValidity(String startDateStr, String endDateStr) {
		LocalDate startDate = parseLocalDate(startDateStr);
		LocalDate endDate = parseLocalDate(endDateStr);
		if(endDate.isAfter(startDate) || endDate.isEqual(startDate)) {
			return true;
		} else {
			return false;
		}
	}
	public ArrayList<String> getDateList(String startDateStr, String endDateStr){
		ArrayList<String> dateList = new ArrayList<String>();
		LocalDate startDate = parseLocalDate(startDateStr);
		LocalDate endDate = parseLocalDate(endDateStr);
		for(;!startDate.isAfter(endDate);startDate = startDate.plusDays(1)) {
			dateList.add(startDate.format(formatter));
		}
		return dateList;
	}
}
