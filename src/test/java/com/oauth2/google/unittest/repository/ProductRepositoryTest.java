package com.oauth2.google.unittest.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.oauth2.google.model.product.Product;
import com.oauth2.google.repository.product.ProductRepository;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void retriveProductsByNameOrDescTest() {	    
	    List<Product> products = productRepository.retriveProductsByNameOrDesc("JavaScript");
	    assertEquals(products.size(), 1); 
	    
	    List<Product> productsNotFound = productRepository.retriveProductsByNameOrDesc("Spring Boot");
	    assertEquals(productsNotFound.size(), 0); 
	}

	@Test
	public void deleteByIdTest() {
		productRepository.deleteById(1);
		Optional<Product> product = productRepository.findById(1);
		assertFalse(product.isPresent());
	}
}
