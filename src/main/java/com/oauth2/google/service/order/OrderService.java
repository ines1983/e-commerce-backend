package com.oauth2.google.service.order;

import java.util.List;

import com.oauth2.google.exception.NotFoundException;
import com.oauth2.google.model.order.Order;
import com.oauth2.google.model.product.Product;

/**
 * Order service class
 * 
 * @author Ines Heni
 *
 */
public interface OrderService {
	
	/**
	 * Find order by ID
	 * 
	 * @param orderId
	 * @return {@Link Order}
	 * @throws NotFoundException 
	 */
	Order findOrderById(Integer orderId) throws NotFoundException;
	
	/**
	 * Save Order
	 * 
	 * @param order {@Link Order}
	 * @return {@Link Order}
	 */
	Order save(Order order);
	
	/**
	 * Get all orders
	 * 
	 * @return List of {@link Order}
	 */
	List<Order> getAllOrders();
	
	/**
	 * Find Products by name or description
	 * 
	 * @param orderId
	 * @param searchTerm
	 * @return
	 */
	List<Product> getProductsByNameOrDesc(Integer orderId, String searchTerm);
	
	/**
	 * Delete Order
	 * 
	 * @param order
	 */
	void deleteOrder(Order order);
}
