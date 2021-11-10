package com.oauth2.google.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.oauth2.google.DataForTest;
import com.oauth2.google.model.order.OrderItem;
import com.oauth2.google.repository.OrderItemRepository;
import com.oauth2.google.service.orderitem.OrderItemServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {
	
	@Mock
    private OrderItemRepository orderItemRepository;
	
    @InjectMocks
    private OrderItemServiceImpl orderItemService;
    
    @BeforeEach
    void initUseCase() {
    }
    
    @Test
    public void saveOrderTest() {
    	OrderItem orderItem = DataForTest.buildOrderItem(1000);
    	when(orderItemRepository.save(ArgumentMatchers.any(OrderItem.class))).thenReturn(orderItem);
    	OrderItem created = orderItemService.save(orderItem);
    	assertEquals(created.getOrder().getCode(), orderItem.getOrder().getCode());
    	verify(orderItemRepository).save(orderItem);
    }
    
    @Test
    public void saveOrderAllTest() {
    	List<OrderItem> orderItems = Stream.of(DataForTest.buildOrderItem(1000), DataForTest.buildOrderItem(2000)).collect(Collectors.toList());
    	when(orderItemRepository.saveAll(ArgumentMatchers.any(List.class))).thenReturn(orderItems);
    	List<OrderItem> orderItemSaved = orderItemService.saveAll(Stream.of(DataForTest.buildOrderItem(1000), DataForTest.buildOrderItem(2000)).collect(Collectors.toList()));
    	assertEquals(orderItemSaved.size(), 2);
    	assertEquals(orderItemSaved.get(0).getOrder().getCode(), orderItems.get(0).getOrder().getCode());
    	verify(orderItemRepository, times(1)).saveAll(ArgumentMatchers.any(List.class));
    }
    
    @Test
    public void updateOrderItemTest() {
    	OrderItem orderItem = DataForTest.buildOrderItem(1000);
    	when(orderItemRepository.findById(1)).thenReturn(Optional.of(orderItem));

    	OrderItem newOrderItem = DataForTest.buildOrderItem(1000);
    	newOrderItem.setQuantity(3);
    	when(orderItemRepository.save(ArgumentMatchers.any(OrderItem.class))).thenReturn(newOrderItem);

    	newOrderItem = orderItemService.updateOrderItem(orderItem);
    	assertNotEquals(newOrderItem.getQuantity(), orderItem.getQuantity());
    	verify(orderItemRepository).save(orderItem);
    	verify(orderItemRepository).findById(1);
    }
    
    @Test
    public void deleteOrderItemTest() {
    	OrderItem orderItem = DataForTest.buildOrderItem(1000);
    	orderItemService.deleteOrderByItemIds(Stream.of(orderItem.getId()).collect(Collectors.toList()));
    	verify(orderItemRepository).deleteAllById(Stream.of(orderItem.getId()).collect(Collectors.toList()));
    }

}
