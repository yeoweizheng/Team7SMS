package team7.sms.model;

public class ErrorMsg {
	private String message;
	private String returnLink;
	public ErrorMsg(String message, String returnLink) {
		this.message = message;
		this.returnLink = returnLink;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReturnLink() {
		return returnLink;
	}
	public void setReturnLink(String returnLink) {
		this.returnLink = returnLink;
	}
	
}
