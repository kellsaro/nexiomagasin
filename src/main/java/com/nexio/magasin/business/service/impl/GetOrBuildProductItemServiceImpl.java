package com.nexio.magasin.business.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexio.magasin.business.service.GetOrBuildProductItemService;
import com.nexio.magasin.domain.entity.Cart;
import com.nexio.magasin.domain.entity.Product;
import com.nexio.magasin.domain.entity.ProductItem;
import com.nexio.magasin.domain.repository.ProductItemRepository;

@Service
public class GetOrBuildProductItemServiceImpl implements GetOrBuildProductItemService {
	
	@Autowired
	private ProductItemRepository productItemRepository;
	
	public ProductItem execute(Product product, Cart cart) {
		
		Optional<ProductItem> optProductItem = productItemRepository.findByCartInAndProductIn(cart, product);
		
		ProductItem productItem = optProductItem.isPresent() ? optProductItem.get() : new ProductItem(cart, product);
		
		return productItem;
	}
}
