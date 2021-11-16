package com.oauth2.google.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oauth2.google.model.order.Order;


/**
 * Repository for {@Link Order}
 * 
 * @author Ines Heni
 *
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
