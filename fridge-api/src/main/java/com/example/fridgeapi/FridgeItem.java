package com.example.fridgeapi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class FridgeItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotEmpty(message="This field cannot be empty")
	@Size(max=20, message="Name cannot exceed 20 characters")
	private String name;
	
	@NotEmpty(message="This field cannot be empty")
	@Size(max=20, message="Name cannot exceed 20 characters")
	private String foodType;
	
	@Min(value=0, message="Minimum quantity must be 0")
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
	
	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
