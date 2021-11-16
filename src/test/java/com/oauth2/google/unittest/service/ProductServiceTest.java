package com.oauth2.google.unittest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oauth2.google.DataForTest;
import com.oauth2.google.exception.NotFoundException;
import com.oauth2.google.model.product.Product;
import com.oauth2.google.repository.product.ProductRepository;
import com.oauth2.google.service.product.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductServiceImpl productService;
	
	@Test
	public void findAllProductsTest() {
		Mockito.when(productRepository.findAll()).thenReturn(Stream.of(DataForTest.buildProduct(1)
				, DataForTest.buildProduct(2)).collect(Collectors.toList()));
		List<Product> products = productService.findAllProducts();
		assertEquals(products.size(), 2);
		assertEquals(products.get(0).getId(), 1);
		assertEquals(products.get(1).getId(), 2);
        verify(productRepository).findAll();
	}
	
	@Test
	public void findProductByIdTest() throws NotFoundException {
		Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(DataForTest.buildProduct(1)));
		Product product = productService.findProductById(1);
		assertEquals(product.getId(), 1);
		verify(productRepository).findById(1);
	}
	
	@Test
	public void retriveProductsByNameOrDescTest() {
		Mockito.when(productRepository.retriveProductsByNameOrDesc("JavaScript")).thenReturn(Stream.of(DataForTest.buildProduct(1)
				, DataForTest.buildProduct(2)).collect(Collectors.toList()));
		List<Product> products = productService.retriveProductsByNameOrDesc("JavaScript");
		assertTrue(products.get(0).getDescription().contains("JavaScript"));
		verify(productRepository).retriveProductsByNameOrDesc("JavaScript");
	}
	
	@Test
    public void saveProductTest() {
    	Product product = DataForTest.buildProduct(1);
    	when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(product);
    	product.setDescription("Spring tutoriam");
    	Product created = productService.save(product);
    	assertTrue(created.getDescription().contains("Spring tutoriam"));
    	verify(productRepository).save(product);
    }
	
	 @Test
	    void deleteProductTest() throws NotFoundException {
		 	Product product = DataForTest.buildProduct(1);
	    	
	    	Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));
	    	productService.deleteByProductId(product.getId());
	    	Product productDb = productService.findProductById(1);
	    	assertTrue(productDb != null);
	    }

}
