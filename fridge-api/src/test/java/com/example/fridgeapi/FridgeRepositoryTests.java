package com.example.fridgeapi;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
class FridgeRepositoryTests {
	
	@Autowired
    private FridgeRepository fridgeRepo;
	
	@Test
	void saveItemTest() {
		FridgeItem item = new FridgeItem("Beans", "Legume", 4);
		
		fridgeRepo.save(item);
		
		Assertions.assertThat(item.getId()).isGreaterThan(0);
	}
	
	@Test
    public void getItemTest(){

        FridgeItem item = fridgeRepo.findById(1L);

        Assertions.assertThat(item.getId()).isEqualTo(1L);

    }
	
	@Test
    public void getItemsTest(){

        List<FridgeItem> items = (List<FridgeItem>) fridgeRepo.findAll();

        Assertions.assertThat(items.size()).isGreaterThan(0);

    }
	
	@Test
    @Rollback(value = false)
    public void updateItemTest(){

        FridgeItem item = fridgeRepo.findById(1L);

        item.setName("Peanuts");

        FridgeItem newItem =  fridgeRepo.save(item);

        Assertions.assertThat(newItem.getName()).isEqualTo("Peanuts");

    }

}
