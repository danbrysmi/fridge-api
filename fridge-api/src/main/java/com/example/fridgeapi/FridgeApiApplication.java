package com.example.fridgeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FridgeApiApplication {

	// Setup logger
	private static final Logger log = LoggerFactory.getLogger(FridgeApiApplication.class);
	
	// Run application on start
	public static void main(String[] args) {
		SpringApplication.run(FridgeApiApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(FridgeRepository repository) {
		return (args) -> {
			// save items to the fridge
			repository.save(new FridgeItem("Ham", "Meat", 1));
			repository.save(new FridgeItem("Cheese", "Dairy", 2));
			
			// fetch all items from the fridge
			log.info("Items found with findAll():");
			log.info("-------------------------------");
			for (FridgeItem fridgeitem : repository.findAll()) {
				log.info(fridgeitem.toString());
			}
		};
	}
}