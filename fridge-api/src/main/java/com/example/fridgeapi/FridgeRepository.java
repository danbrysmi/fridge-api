package com.example.fridgeapi;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FridgeRepository extends CrudRepository<FridgeItem, Long> {
	
	List<FridgeItem> findByName(String name);

	FridgeItem findById(long id);
}
