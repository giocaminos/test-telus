package com.test.telus.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.telus.app.entity.Product;
import com.test.telus.app.service.ProductService;

@WebMvcTest(TestProduc.class)
public class TestProduc {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService service;

	@Test
	public void findAllProduct() throws Exception {

		mockMvc.perform(get("/api/products")).andExpect(status().isOk());

	}

	@Test
	public void finByIDProduct() throws Exception {

		mockMvc.perform(get("/api/products/5")).andExpect(status().isOk());

	}

	@Test
	public void createProduct() throws Exception {

		Product product = new Product();
		product.setDescription("Test one");
		product.setName("TV 29 samsum");
		product.setPrice(256.90);

		mockMvc.perform(post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(product.toString()))
				.andExpect(status().isOk());

	}
}
