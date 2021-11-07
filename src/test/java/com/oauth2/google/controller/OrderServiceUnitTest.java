package com.oauth2.google.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.oauth2.google.model.order.Order;
import com.oauth2.google.repository.order.OrderRepository;
import com.oauth2.google.service.order.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {
	
	@Mock
    private OrderRepository orderRepository;
	
    @Autowired
    @InjectMocks
    private OrderServiceImpl orderService;
    
    @Test
    void saveOrder() {
    	/*Order order = DataForTest.buildOrder();
         when(orderRepository.save(order)).thenReturn(order);
         orderService.addProduct(order);
         verify(orderRepository,times(1)).save(order);*/
    }

}
