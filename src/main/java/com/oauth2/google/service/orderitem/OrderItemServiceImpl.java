package com.oauth2.google.service.orderitem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.oauth2.google.dto.LocalUser;
import com.oauth2.google.model.order.Order;
import com.oauth2.google.model.order.OrderItem;
import com.oauth2.google.model.user.User;
import com.oauth2.google.repository.OrderItemRepository;

import io.jsonwebtoken.lang.Collections;

/**
 * Implementation for OrderItemService
 * 
 * @author Ines Heni
 *
 */
@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
	
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
		this.orderItemRepository = orderItemRepository;
	}
	
	@Override
	public void deleteOrderByItemIds(List<Integer> orderItemsId) {
		orderItemRepository.deleteAllById(orderItemsId);
	}

	@Override
	public List<OrderItem> saveAll(List<OrderItem> orderItems) {
		return orderItemRepository.saveAll(orderItems);
	}
	
	@Override
	public List<OrderItem> retrieveOrderItemByOrder() {
		LocalUser localUser = (LocalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Order order = null;
		if(!Collections.isEmpty(localUser.getUser().getOrders()))
			order = localUser.getUser().getOrders().get(0);
		return orderItemRepository.retrieveOrderItemByOrder(order);
	}
	
	@Override
	public void addProductsToOrder(OrderItem orderItem) {
		Order order = null;
		Integer orderId = 0;
		LocalUser localUser = (LocalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = localUser.getUser();
		List<Integer> orders = orderItemRepository.retrieveOrderByUser(user.getId());
		if(!ObjectUtils.isEmpty(orders)) {
			orderId = orders.get(0);
		}
		
		if(orderId != 0) {
			Integer orderItemId = orderItemRepository.retrieveOrderItemByOrderAndProduct(orderId, orderItem.getProduct().getId());
			orderItem.setOrder(orderItemRepository.retriveOrderById(orderId));

			if(orderItemId != null) {
				OrderItem orderItemFromDB = orderItemRepository.findById(orderItemId).get();
				Integer qty = orderItemFromDB.getQuantity() + 1;
				orderItemFromDB.setQuantity(qty);
				orderItem = orderItemFromDB;
			}
				
		} else {
			order = new Order();
			int code = Double.valueOf(Math.random() * ((50 - 20) + 1)).intValue() + 20;
			order.setCode(code);
			orderItem.setOrder(order);
			order.setClient(user);
			localUser.getUser().getOrders().add(order);
		}
		orderItemRepository.save(orderItem);

		
	}
	
	@Override
	public void updateOrderItem(OrderItem orderItem) {
		OrderItem orderItemFromDb = orderItemRepository.findById(orderItem.getId()).get();
		orderItemFromDb.setQuantity(orderItem.getQuantity());
		orderItemRepository.save(orderItemFromDb);
	}

}
