package com.oauth2.google.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oauth2.google.model.order.Order;


/**
 * Repository for {@Link Order}
 * 
 * @author Ines Heni
 *
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	@Modifying
	@Query("DELETE FROM Order order1 WHERE id = :id")
	void deleteById(@Param("id") Integer id);
}
