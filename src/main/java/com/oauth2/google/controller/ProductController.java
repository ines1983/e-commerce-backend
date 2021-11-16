package com.oauth2.google.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oauth2.google.exception.NotFoundException;
import com.oauth2.google.model.product.Product;
import com.oauth2.google.service.product.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/all")
	public ResponseEntity<List<Product>> findAllProducts() {
		return new ResponseEntity<> (productService.findAllProducts(), HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable("id") Integer id) {
		Product product = null;
		try {
			product = productService.findProductById(id);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(product, HttpStatus.NOT_FOUND);
		}
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

	@PostMapping("/create")
	public ResponseEntity<Product> createOrder(@RequestBody Product product) {
		return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable Integer id){
		productService.deleteByProductId(id); 
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/search-by-name-desc")
	public List<Product> retriveProductsByNameOrDesc(@RequestParam("searchTerm") String searchTerm) {
		return productService.retriveProductsByNameOrDesc(searchTerm);
	}
}
