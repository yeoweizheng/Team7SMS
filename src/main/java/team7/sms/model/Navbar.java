package team7.sms.model;

import java.util.LinkedHashMap;

public class Navbar {
	private LinkedHashMap<String, String> items;

	public Navbar() {
		this.items = new LinkedHashMap<String, String>();
	}
	
	public void addItem(String name, String url) {
		this.items.put(name, url);
	}

	public LinkedHashMap<String, String> getItems() {
		return items;
	}
}
