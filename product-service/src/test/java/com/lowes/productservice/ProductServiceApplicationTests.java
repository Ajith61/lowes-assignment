package com.lowes.productservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import com.lowes.productservice.service.ProductService;
import com.lowes.productservice.model.dao.ProductDao;
import com.lowes.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private ProductService service;

	@MockBean
	private ProductRepository repository;

	@Test
	public void getProductTest() {
		//store some hard coded data which will not dependent from database
		when(repository.findAll()).thenReturn(Stream
				.of(new ProductDao("1", "p1", "Pen",Long.valueOf(10),10), new ProductDao("2", "p2", "Pencil",Long.valueOf(10),10)).collect(Collectors.toList()));
		// check the data
		assertEquals(2, service.getAllProducts().getResponseModel().size());
	}
	
}
