package com.oauth2.google.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oauth2.google.model.order.Order;
import com.oauth2.google.repository.order.OrderRepository;
import com.oauth2.google.service.order.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {
	
	@Mock
    private OrderRepository orderRepository;
	
    @InjectMocks
    private OrderServiceImpl orderService;
    
    @Test
    void getAllOrdersTest() {
    	Mockito.when(orderRepository.findAll())
    	.thenReturn(Stream.of(DataForTest.buildOrder(1000), DataForTest.buildOrder(2000)).collect(Collectors.toList()));
		 List<Order> orders = orderService.getAllOrders();
		 assertEquals(1000, orders.get(0).getCode());
		 assertEquals(2000, orders.get(1).getCode());
    }
    
    @Test
    void findOrderById() {
    	Mockito.when(orderRepository.findById(1000))
    	.thenReturn(Optional.of(DataForTest.buildOrder(1000)));
		 List<Order> order = orderService.getAllOrders();
		 assertEquals(1000, order.get(0).getCode());
    }
    
   }
