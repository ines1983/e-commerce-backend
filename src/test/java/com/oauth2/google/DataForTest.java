package com.oauth2.google;

import java.util.Date;

import com.oauth2.google.model.category.Category;
import com.oauth2.google.model.order.Order;
import com.oauth2.google.model.order.OrderItem;
import com.oauth2.google.model.product.Product;
import com.oauth2.google.model.user.User;

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
	
	public static OrderItem buildOrderItem(Integer code) {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(1);
		orderItem.setOrder(buildOrder(code));
		orderItem.setProduct(buildProduct());
		orderItem.setQuantity(5);
		return orderItem;
	}
	
	public static Order buildOrder(Integer code) {
		Order order = new Order();
		order.setCode(code);
		order.setClient(buildUser());
		return order;
    }

	public static User buildUser() {
		User user = new User();
		user.setEmail("admin.admin@gmail.com");
		user.setDisplayName("Admin Admin");
		user.setCreatedDate(new Date());
		return user;
    }
}
