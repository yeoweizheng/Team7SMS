package team7.sms.model;

import java.util.LinkedHashMap;

public class Sidebar {
	private LinkedHashMap<String, String> items;

	public Sidebar() {
		this.items = new LinkedHashMap<String, String>();
	}
	
	public void addItem(String name, String url) {
		this.items.put(name, url);
	}

	public LinkedHashMap<String, String> getItems() {
		return items;
	}
}
