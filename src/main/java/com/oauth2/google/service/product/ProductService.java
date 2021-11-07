package com.oauth2.google.service.product;

import java.util.List;

import com.oauth2.google.exception.NotFoundException;
import com.oauth2.google.model.product.Product;

/**
 * Product service class
 * 
 * @author Ines Heni
 *
 */
public interface ProductService {

	/**
	 * Find all products
	 * 
	 * @return list of {@link Product}
	 */
	List<Product> findAllProducts();
	
	/**
	 * Find product by id
	 * 
	 * @param productId
	 * @return {@link Product}
	 */
	Product findProductById(Integer productId) throws NotFoundException;
	
	/**
	 * Delete product by ID
	 * 
	 * @param productId
	 * @return
	 */
	Integer deleteByProductId(long productId);
	
	/**
	 * Search product by name or description
	 * 
	 * @param searchTerm
	 * @return
	 */
	List<Product> retriveProductsByNameOrDesc(String searchTerm);
	
	/**
	 * Save product
	 * 
	 * @param product
	 * @return {@link Product}
	 */
	Product save(Product product);
}
