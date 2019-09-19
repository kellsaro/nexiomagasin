package com.nexio.magasin.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name = "product_items")
public class ProductItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id")
	@NotNull
	private Product product;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "cart_id", nullable = false, referencedColumnName = "id")
	@NotNull
	private Cart cart;

	@Min(value = 1)
	@NotNull
	private Integer quantity;
	
	@NotNull
	@DecimalMin("0.01")
	private BigDecimal price;
	
	public ProductItem() {}
	
	public ProductItem(Cart cart, Product product) {
		
		this();
		this.setCart(cart);
		this.setProduct(product);
		this.setPrice(product.getPrice());
		this.setQuantity(1);
	}
}
