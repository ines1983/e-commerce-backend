package com.oauth2.google.model.order;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.oauth2.google.model.product.Product;
import com.oauth2.google.model.user.User;

import lombok.Data;

/**
 * Order entity
 * 
 * @author Ines Heni
 *
 */

@Entity
@Table(name = "ORDERS")
@Data
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "CODE", nullable = false)
	private Integer code;
	
	@ManyToOne//(cascade = CascadeType.ALL )
	@JoinTable(name = "ORDER_CLIENT", joinColumns = @JoinColumn(name = "ORDER_ID"), inverseJoinColumns = @JoinColumn(name = "CLIENT_ID"))
	private User client;
	
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    private List<OrderItem> orderItems;*/
	
	@Transient
	private double totalPrice;
	

}
