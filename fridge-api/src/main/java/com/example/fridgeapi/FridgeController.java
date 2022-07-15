package com.example.fridgeapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/food", produces = {MediaType.TEXT_HTML_VALUE})
public class FridgeController {
	
	@Autowired
	private FridgeRepository fridgeRepo;
	
	// '/food' - displays a list of all of the items in the fridge
	@GetMapping
	public String index(Model model) {
		Iterable<FridgeItem> items = fridgeRepo.findAll();
		model.addAttribute("items", items);
		
		return "food/index";
	}
	
	@GetMapping(value = "/add")
	public String newEvent(Model model) {
		model.addAttribute("item", new FridgeItem());
		return "food/add";
	}
	
	@PostMapping
	public String addEvent(@ModelAttribute FridgeItem item, Model model) {
		model.addAttribute("item", item);
		FridgeItem new_item = fridgeRepo.save(item);
		return "redirect:/food";
	}
}