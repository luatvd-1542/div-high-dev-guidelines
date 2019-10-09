package com.sunasterisk.javaprojecttesting;

import com.sunasterisk.javaprojecttesting.service.OrderService;
import com.sunasterisk.javaprojecttesting.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.sunasterisk.javaprojecttesting.entity.Product;

import java.util.Optional;

//@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class JavaProjectTestingApplicationTests {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Test
	public void whenUserIdIsProvided_thenRetrievedNameIsCorrect() {
		Product product = new Product();
		product.setName("Test product");
		product.setId(3L);
		Optional<Product> productOptional = Optional.of(product);
		Mockito.when(productService.findById(3L)).thenReturn(productOptional);
		String testName = orderService.getProductName();
		Assert.assertEquals("Mock Product Name", testName);
	}
}
