package com.oauth2.google.model.order;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.oauth2.google.model.product.Product;

import lombok.Data;

/**
 * OrderItem entity
 * 
 * @author Ines Heni
 *
 */

@Entity
@Table(name = "ORDER_ITEM")
@Data
public class OrderItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
	private Product product;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ID")
	private Order order;
	
	@Column(name = "QUANTITY")
	private Integer quantity;

}
