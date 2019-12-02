package team7.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import team7.sms.model.Sidebar;

@Controller
@RequestMapping("/Home")
public class HomeController {

	@GetMapping("/")
	public String index(Model model) {
		Sidebar homeSidebar = new Sidebar();
		homeSidebar.addItem("News", "Home/News");
		homeSidebar.addItem("Calendars", "Home/Calendars");
		model.addAttribute("sidebar", homeSidebar);
		return "home";
	}
}
