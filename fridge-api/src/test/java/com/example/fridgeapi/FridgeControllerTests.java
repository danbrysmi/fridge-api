package com.example.fridgeapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


//@SpringBootTest
@WebMvcTest(FridgeController.class)
public class FridgeControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private FridgeController controller;
	
	@Mock
	private FridgeItem item;

	@MockBean
	private FridgeRepository fridgeRepo;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

}
