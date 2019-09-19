package com.nexio.magasin.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexio.magasin.domain.entity.Cart;
import com.nexio.magasin.domain.entity.ProductItem;
import com.nexio.magasin.domain.repository.CartRepository;
import com.nexio.magasin.domain.repository.ProductItemRepository;

@Service
public class GetProductItemsByCartService {
	
	@Autowired
	private ProductItemRepository productItemRepository;
	
	@Autowired
	private CartRepository cartRepository;

	public List<ProductItem> execute(Long cartId){
		
		if (cartId == null) return new ArrayList<>();
		
		Optional<Cart> optCart = cartRepository.findById(cartId);
		
		List<ProductItem> productItemList = productItemRepository.findAllByCartId(optCart.get().getId());
		
		if(optCart.isPresent()) return productItemList;
		
		return new ArrayList<>();
	}
}
