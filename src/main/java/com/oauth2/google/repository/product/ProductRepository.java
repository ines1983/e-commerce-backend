package com.oauth2.google.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oauth2.google.model.product.Product;


/**
 * Interface for Product repository
 * 
 * @author Ines Heni
 *
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

	/**
	 * 
	 * @param productId
	 * @return
	 */
	Integer deleteById(long id);
	
	@Query("SELECT product FROM Product product "
			+ "WHERE product.name like %:searchTerm% "
			+ "OR product.description like %:searchTerm%")
	List<Product> retriveProductsByNameOrDesc(@Param("searchTerm") String searchTerm);
}
