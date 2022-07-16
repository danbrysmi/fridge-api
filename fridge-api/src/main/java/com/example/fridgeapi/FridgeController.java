package com.example.fridgeapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;

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
	
	@GetMapping(value="/add")
	public String newEvent(Model model) {
		model.addAttribute("item", new FridgeItem());
		return "food/add";
	}
	
	@GetMapping(value="/{id}")
	public String getEventById(@PathVariable("id") long id, Model model) {
		FridgeItem selectedItem = fridgeRepo.findById(id);
		
		if (selectedItem == null) {
			selectedItem = new FridgeItem("empty", "empty", 404);
			return "food/index";
		}
		model.addAttribute("itemId", id);
		model.addAttribute("itemName", selectedItem.getName());
		model.addAttribute("itemFoodType", selectedItem.getFoodType());
		model.addAttribute("itemQuantity", selectedItem.getQuantity());
		
		return "food/item";
	}
	
	@PostMapping
	public String addEvent(@ModelAttribute FridgeItem item, Model model) {
		model.addAttribute("item", item);
		fridgeRepo.save(item);
		
		return "redirect:/food";
	}
	
	@GetMapping(value="/update/{id}")
	public String updateItem(@PathVariable("id") long id, Model model) {
		FridgeItem selectedItem = fridgeRepo.findById(id);
		
		if (selectedItem == null) {
			return "food/index";
		}
		model.addAttribute("editItem", selectedItem);
		//model.addAttribute("fridgeformmodel", new FridgeItemFormModel());
		
		return "food/update";
	}
	
	@PostMapping(value="/update/{id}")
	public String changeItem(@PathVariable("id") long id, @Valid FridgeItem item, BindingResult errors, Model model) {
		FridgeItem selectedItem = fridgeRepo.findById(id);
		
		if (selectedItem == null) {
			fridgeRepo.save(item);
		}
		else {
			selectedItem.setName(item.getName());
			selectedItem.setFoodType(item.getFoodType());
			selectedItem.setQuantity(item.getQuantity());
		}
		
		fridgeRepo.save(selectedItem);
		
		return "redirect:/food";
	}
	
	@GetMapping(value="/search")
	public String searchEvents(Model model, @RequestParam(value="q") String q) {
		
		String searchString = "\\b" + q + "\\b";

		Pattern pattern = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE);
		Matcher m;
		
		Iterable<FridgeItem> items = fridgeRepo.findAll();
		ArrayList<FridgeItem> matches = new ArrayList<FridgeItem>();
		
		for(FridgeItem item : items) {
			m = pattern.matcher(item.getName());
		    boolean matchFound = m.find();
		    if(matchFound) {
		    	matches.add(item);
		    }
		}
		
		model.addAttribute("itemMatches", matches);
		
		return "food/search";
	}
}