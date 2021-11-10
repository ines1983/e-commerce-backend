package com.oauth2.google.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oauth2.google.dto.LocalUser;
import com.oauth2.google.exception.NotFoundException;
import com.oauth2.google.model.order.Order;
import com.oauth2.google.model.order.OrderItem;
import com.oauth2.google.model.product.Product;
import com.oauth2.google.service.order.OrderService;
import com.oauth2.google.service.orderitem.OrderItemService;

import io.jsonwebtoken.lang.Collections;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderItemService orderItemService;

	@GetMapping("/all")
	public ResponseEntity<List<Order>> findAllOrders() {
		return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
	}

	@GetMapping("/find/current-order")
	public ResponseEntity<Order> findCurrentOrder() {
		LocalUser localUser = (LocalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Order order = null;
		if (!Collections.isEmpty(localUser.getUser().getOrders()))
			order = localUser.getUser().getOrders().get(0);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Order> findOrdeById(@PathVariable("id") Integer id) {
		Order order = null;
		try {
			order = orderService.findOrderById(id);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(order, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		order = orderService.save(order);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@GetMapping("/find/order-items")
	public ResponseEntity<List<OrderItem>> findOrderItem() {
		return new ResponseEntity<>(orderItemService.retrieveOrderItemByOrder(), HttpStatus.OK);
	}

	@GetMapping("/search-by-name-description")
	public ResponseEntity<List<Product>> searchProductsByNameOrDesc(@RequestParam("orderId") int orderId,
			@RequestParam("searchTerm") String searchTerm) {
		return new ResponseEntity<>(orderService.getProductsByNameOrDesc(orderId, searchTerm), HttpStatus.OK);
	}

	@DeleteMapping("/delete-order-items")
	public ResponseEntity<?> deleteOrderByItemIds(@RequestParam("orderItemsId") List<Integer> orderItemsId) {
		orderItemService.deleteOrderByItemIds(orderItemsId);
		LocalUser localUser = (LocalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<OrderItem> orderItems = orderItemService.retrieveOrderItemByOrder();
		if (ObjectUtils.isEmpty(orderItems))
			orderService.deleteOrder(localUser.getUser().getOrders().get(0));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/add-products-to-order")
	public ResponseEntity<?> addProductsToOrder(@RequestBody OrderItem orderItem) {
		orderItemService.addProductsToOrder(orderItem);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/update-order-item")
	public ResponseEntity<?> updateOrderItem(@RequestBody OrderItem orderItem) {
		orderItem = orderItemService.updateOrderItem(orderItem);
		return new ResponseEntity<>(HttpStatus.OK);
	}


}
