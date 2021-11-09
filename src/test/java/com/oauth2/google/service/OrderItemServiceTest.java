package com.oauth2.google.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oauth2.google.repository.OrderItemRepository;
import com.oauth2.google.service.orderitem.OrderItemServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {
	
	@Mock
    private OrderItemRepository orderItemRepository;
	
    @InjectMocks
    private OrderItemServiceImpl orderService;
    
    @BeforeEach
    void initUseCase() {
    }
    
    @Test
    public void saveAllTest() {
    	
    }

}
