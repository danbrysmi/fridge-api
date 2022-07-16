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
	
	// /food - displays a list of all of the items in the fridge
	@GetMapping
	public String index(Model model) {
		// list of all items
		Iterable<FridgeItem> items = fridgeRepo.findAll();
		
		// sends list to view
		model.addAttribute("items", items);
		
		return "food/index";
	}
	
	// /food/add - readies an empty FridgeItem to be added to the database via form
	@GetMapping(value="/add")
	public String newItem(Model model) {
		// prepare blank food item
		model.addAttribute("item", new FridgeItem());
		return "food/add";
	}
	
	// /food - when redirected after adding a food item, the new item will be saved
	@PostMapping
	public String addItem(@ModelAttribute FridgeItem item, Model model) {
		// received item is saved to both model and repository
		model.addAttribute("item", item);
		fridgeRepo.save(item);
		
		return "redirect:/food";
	}
	
	// /food/x - shows the details page for FridgeItem with ID
	@GetMapping(value="/{id}")
	public String getItemById(@PathVariable("id") long id, Model model) {
		// attempts to find the FridgeItem by using its id
		FridgeItem selectedItem = fridgeRepo.findById(id);
		
		if (selectedItem == null) {
			// stops the transfer to the details page if FridgeItem does not exist
			return "food/index";
		}
		// send item details to view
		model.addAttribute("itemId", id);
		model.addAttribute("itemName", selectedItem.getName());
		model.addAttribute("itemFoodType", selectedItem.getFoodType());
		model.addAttribute("itemQuantity", selectedItem.getQuantity());
		
		return "food/item";
	}
	
	// /food/update/x - readies the FridgeItem in a form to be updated
	@GetMapping(value="/update/{id}")
	public String updateItem(@PathVariable("id") long id, Model model) {
		// attempts to find the FridgeItem by using its id
		FridgeItem selectedItem = fridgeRepo.findById(id);
		
		if (selectedItem == null) {
			// stops the transfer to the update page if FridgeItem does not exist
			return "food/index";
		}
		// send item object to view
		model.addAttribute("editItem", selectedItem);
		
		return "food/update";
	}
	
	// /food/update/x - saves the updated FridgeItem
	@PostMapping(value="/update/{id}")
	public String changeItem(@PathVariable("id") long id, @Valid FridgeItem item, BindingResult errors, Model model) {
		// attempts to find the FridgeItem by using its id
		FridgeItem selectedItem = fridgeRepo.findById(id);
		
		if (selectedItem == null) {
			// if item has no id, save as a new object
			fridgeRepo.save(item);
		}
		else {
			// if item has id update attributes and save
			selectedItem.setName(item.getName());
			selectedItem.setFoodType(item.getFoodType());
			selectedItem.setQuantity(item.getQuantity());
		}
		
		fridgeRepo.save(selectedItem);
		
		return "redirect:/food";
	}
	
	// food/search?q=... - searches the database for items where the name contains ...
	@GetMapping(value="/search")
	public String searchItems(Model model, @RequestParam(value="q") String q) {
		// regex string to search for whole word matches of search term
		String searchString = "\\b" + q + "\\b";

		Pattern pattern = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE);
		Matcher m;
		
		// loop through all items in database
		Iterable<FridgeItem> items = fridgeRepo.findAll();
		ArrayList<FridgeItem> matches = new ArrayList<FridgeItem>();
		
		// add matches to the matches array
		for(FridgeItem item : items) {
			m = pattern.matcher(item.getName());
		    boolean matchFound = m.find();
		    if(matchFound) {
		    	matches.add(item);
		    }
		}
		// send matches array to view
		model.addAttribute("itemMatches", matches);
		
		return "food/search";
	}
}