package com.nexio.magasin.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	@NotNull
	@NotBlank
	private String code;
	
	@NotNull
	@NotBlank
	private String name;
	
	@NotNull
	@DecimalMin("0.01")
	private BigDecimal price;
	
	@NotNull
	@NotBlank
	private String detail;
}
