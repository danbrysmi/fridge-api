package com.example.fridgeapi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FridgeItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String foodType;
	private int quantity;

	protected FridgeItem() {}
	
	public FridgeItem(String name, String foodType, int quantity) {
		this.name = name;
		this.foodType = foodType;
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return String.format("Item[id=%d, name='%s', type='%s', quantity=%d]",
				id, name, foodType, quantity);
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFoodType() {
		return foodType;
	}
	
	public void setfoodType(String foodType) {
		this.foodType = foodType;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
