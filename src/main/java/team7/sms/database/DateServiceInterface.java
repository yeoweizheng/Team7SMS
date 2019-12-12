package team7.sms.database;

public interface DateServiceInterface {
	public boolean checkOverlap(String startDateStr1, String endDateStr1, String startDateStr2, String endDateStr2);
	public boolean checkStartEndValidity(String startDateStr, String endDateStr);
}
