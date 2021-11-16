package com.oauth2.google.unittest.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.oauth2.google.DataForTest;
import com.oauth2.google.model.order.Order;
import com.oauth2.google.repository.order.OrderRepository;

@DataJpaTest
public class OrderRepositoryTest {
	
	@Autowired
	private OrderRepository orderRepository;

	@Test
	public void deleteByIdTest() {
		orderRepository.save(DataForTest.buildOrder(1000));
		orderRepository.deleteById(1);
		Optional<Order> order = orderRepository.findById(1);
		assertFalse(order.isPresent());
	}

}
