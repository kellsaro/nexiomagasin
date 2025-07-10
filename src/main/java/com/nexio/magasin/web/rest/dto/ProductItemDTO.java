package com.nexio.magasin.web.rest.dto;

import java.math.BigDecimal;

import com.nexio.magasin.domain.entity.ProductItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemDTO {

	private Long id;
	private Long product_id;
	private Long cart_id;
	private BigDecimal price;
	private Integer quantity;
	
	public ProductItemDTO(ProductItem productItem) {
		this(productItem.getId(), 
				productItem.getProduct().getId(),
				productItem.getCart().getId(),
				productItem.getPrice(),
				productItem.getQuantity());
	}
	
}
