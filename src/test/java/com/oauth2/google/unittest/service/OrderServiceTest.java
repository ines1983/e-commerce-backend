package com.oauth2.google.unittest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oauth2.google.DataForTest;
import com.oauth2.google.exception.NotFoundException;
import com.oauth2.google.model.order.Order;
import com.oauth2.google.repository.order.OrderRepository;
import com.oauth2.google.service.order.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
	
	@Mock
    private OrderRepository orderRepository;
	
    @InjectMocks
    private OrderServiceImpl orderService;
    
    @BeforeEach
    void initUseCase() {
    }
    
    @Test
    void getAllOrdersTest() {
    	Mockito.when(orderRepository.findAll())
    	.thenReturn(Stream.of(DataForTest.buildOrder(1000), DataForTest.buildOrder(2000)).collect(Collectors.toList()));
		 List<Order> orders = orderService.getAllOrders();
		 assertEquals(1000, orders.get(0).getCode());
		 assertEquals(2000, orders.get(1).getCode());
    }
    
    @Test
    void findOrderByIdTest() throws NotFoundException {
    	Mockito.when(orderRepository.findById(1000))
    	.thenReturn(Optional.of(DataForTest.buildOrder(1000)));
		 Order order = orderService.findOrderById(1000);
		 assertEquals(1000, order.getCode());
    }
    
    @Test
    void saveOrderTest() {
    	Order order = DataForTest.buildOrder(1000);
    	Mockito.when(orderRepository.save(order)).thenReturn(order);
    	Order orderDb = orderService.save(order);
    	assertTrue(orderDb.getCode() != null);
    }
    
    @Test
    void deleteOrderTest() throws NotFoundException {
    	Order order = DataForTest.buildOrder(1000);
    	
    	Mockito.when(orderRepository.findById(1000)).thenReturn(Optional.of(order));
    	orderService.deleteOrder(order);
    	Order orderDb = orderService.findOrderById(1000);
    	assertTrue(orderDb != null);
    }
    
   }
