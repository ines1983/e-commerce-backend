package com.oauth2.google.controller;

import java.util.Collections;
import java.util.Date;

import com.oauth2.google.model.category.Category;
import com.oauth2.google.model.order.Order;
import com.oauth2.google.model.order.OrderItem;
import com.oauth2.google.model.product.Product;

public class DataForTest {
	
	public static Product buildProduct() {
		Product product = new Product();
		product.setCategory(buildCategory());
		product.setName("JavaScript - The Fun Parts");
		product.setDescription("Learn JavaScript");
		product.setUnitPrice(15.5);
		product.setImageUrl("assets/images/products/placeholder.png");
		product.setActive(true);
		product.setUnitInStock(100);
		product.setCreatedDate(new Date());
		product.setLastUpdated(new Date());
		return product;
	}
	
	public static Category buildCategory() {
		Category category = new Category();
		category.setName("Books");
		return category;
	}
	
	public static OrderItem buildOrderItem() {
		OrderItem orderItem = new OrderItem();
		//orderItem.setOrder(buildOrder());
		orderItem.setProduct(buildProduct());
		orderItem.setQuantity(5);
		return orderItem;
	}
	
	public static Order buildOrder() {
		Order order = new Order();
		OrderItem orderItem = buildOrderItem();
		order.setOrderItems(Collections.singletonList(orderItem));
		return order;
    }

}
