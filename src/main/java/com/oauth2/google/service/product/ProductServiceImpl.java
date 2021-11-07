package com.oauth2.google.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oauth2.google.exception.NotFoundException;
import com.oauth2.google.model.product.Product;
import com.oauth2.google.repository.product.ProductRepository;

/**
 * ProductService Implementation
 * 
 * @author Ines Heni
 *
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository productRepository;
	
	@Autowired
	ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Product findProductById(Integer productId) throws NotFoundException {
		return productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundException("Product by id " + productId + " was not found"));
	}
	
	@Override
	public Integer deleteByProductId(long productId) {
		return productRepository.deleteById(productId);
	}
	
	@Override
	public List<Product> retriveProductsByNameOrDesc(String searchTerm) {
		return productRepository.retriveProductsByNameOrDesc(searchTerm);
	}
	
	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}

}
