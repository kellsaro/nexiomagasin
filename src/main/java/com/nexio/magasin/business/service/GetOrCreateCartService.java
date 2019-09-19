package com.nexio.magasin.business.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexio.magasin.domain.entity.Cart;
import com.nexio.magasin.domain.repository.CartRepository;

@Service
public class GetOrCreateCartService {
	
	@Autowired
	private CartRepository cartRepository;

	public Cart execute(Long cartId) {
		
		if(cartId == null) return cartRepository.save(new Cart());
		
		Optional<Cart> optCart = cartRepository.findById(cartId);

		return optCart.isPresent() ? optCart.get(): cartRepository.save(new Cart());
	}
}
