package com.oauth2.google.service.orderitem;

import java.util.List;

import com.oauth2.google.model.order.OrderItem;

/**
 * 
 * @author Ines Heni
 *
 */
public interface OrderItemService {
	
	/**
	 * Delete order items by ids
	 * 
	 * @param orderItemsId
	 */
	void deleteOrderByItemIds(List<Integer> orderItemsId);
	
	/**
	 * Save list of OrderItem
	 * 
	 * @param orderItem
	 * @return
	 */
	List<OrderItem> saveAll(List<OrderItem> orderItem);
	
	/**
	 * Retrieve orderItem by order
	 * 
	 * @return list of {@OrderItem}
	 */
	List<OrderItem> retrieveOrderItemByOrder();
	
	/**
	 * Add new product to order
	 *  
	 * @param orderItem
	 */
	void addProductsToOrder(OrderItem orderItem);
	
	/**
	 * Update quantity in the order item
	 * 
	 * @param orderItem
	 */
	void updateOrderItem(OrderItem orderItem);

}
