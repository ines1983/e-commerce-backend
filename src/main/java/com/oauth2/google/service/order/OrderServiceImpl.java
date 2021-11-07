package com.oauth2.google.service.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oauth2.google.exception.NotFoundException;
import com.oauth2.google.model.order.Order;
import com.oauth2.google.model.product.Product;
import com.oauth2.google.repository.order.OrderRepository;

/**
 * Implementation for OrderService
 * 
 * @author Ines Heni
 *
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;

	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Order findOrderById(Integer orderId) throws NotFoundException {
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new NotFoundException("Order by id " + orderId + " was not found"));
	}

	@Override
	public Order save(Order order) {
		return orderRepository.save(order);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public List<Product> getProductsByNameOrDesc(Integer orderId, String searchTerm) {
		//Optional<Order> order = orderRepository.findById(orderId);
		/*
		 * if(order.isPresent()){ return order.get().getOrderItems().stream() .map(oi ->
		 * oi.getProduct()) .filter(p -> p.getName().contains(searchTerm) ||
		 * p.getDescription().contains(searchTerm)).collect(Collectors.toList()); }
		 */
		return null;
	}

	@Override
	public void deleteOrder(Order order) {
		orderRepository.deleteById(order.getId());
	}
}
