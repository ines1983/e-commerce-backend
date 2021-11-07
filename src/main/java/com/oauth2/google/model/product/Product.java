package com.oauth2.google.model.product;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oauth2.google.model.category.Category;

import lombok.Data;

/**
 * Product entity
 * 
 * @author Ines Heni
 *
 */
@Entity
@Table(name = "PRODUCT")
@Data
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

	@Size(max = 255)
    @Column(name = "CODE", nullable = false)
    private String code;
	
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @Size(max = 255)
    @Column(name = "NAME", nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Column(name = "UNIT_PRICE", nullable = false)
    private Double unitPrice;

    @Size(max = 255)
    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "UNIT_IN_STOCK")
    private int unitInStock;

    @Column(name = "CREATED_DATE", nullable = false)
    @Temporal(value= TemporalType.DATE)
    private Date createdDate;

    @Column(name = "LAST_UPDATED")
    @Temporal(value= TemporalType.DATE)
    private Date lastUpdated;

}
