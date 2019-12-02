package team7.sms.model;

import java.util.HashMap;

public class Sidebar {
	private HashMap<String, String> items;

	public Sidebar() {
		this.items = new HashMap<String, String>();
	}
	
	public void addItem(String name, String url) {
		this.items.put(name, url);
	}

	public HashMap<String, String> getItems() {
		return items;
	}
}
