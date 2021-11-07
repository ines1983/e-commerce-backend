package com.oauth2.google.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oauth2.google.model.order.Order;
import com.oauth2.google.model.order.OrderItem;

/**
 * Interface of OrderItem repository
 * 
 * @author Ines Heni
 *
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{
	
	@Query("SELECT orderItem FROM OrderItem orderItem "
			+ "JOIN orderItem.order order1 "
			+ "WHERE order1 = :order")
	List<OrderItem> retrieveOrderItemByOrder(@Param("order") Order order);
	
	@Query(nativeQuery = true, value ="SELECT order1.ID FROM ORDERS order1, ORDER_CLIENT "
			+ "WHERE order1.ID = ORDER_CLIENT.ORDER_ID "
			+ "AND ORDER_CLIENT.CLIENT_ID = :userId")
	List<Integer> retrieveOrderByUser(@Param("userId") Long userId);
	
	@Query(nativeQuery = true, value = "SELECT orderItem.ORDER_ID FROM ORDER_ITEM orderItem "
			+ "WHERE ORDER_ID = :orderId "
			+ "AND PRODUCT_ID = :productId ")
	Integer retrieveOrderItemByOrderAndProduct(@Param("orderId") Integer orderId, @Param("productId") Integer productId);
	
	@Query("SELECT order1 FROM Order order1 "
			+ "WHERE order1.id = :orderId")
	Order retriveOrderById(@Param("orderId") Integer orderId);

}
