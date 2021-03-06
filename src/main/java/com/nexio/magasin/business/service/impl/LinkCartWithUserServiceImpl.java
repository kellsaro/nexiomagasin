package com.nexio.magasin.business.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexio.magasin.business.service.LinkCartWithUserService;
import com.nexio.magasin.domain.entity.Cart;
import com.nexio.magasin.domain.entity.User;
import com.nexio.magasin.domain.repository.CartRepository;

@Service
public class LinkCartWithUserServiceImpl implements LinkCartWithUserService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Override
	public Optional<Cart> execute(Long cartId, User user) {
		
		if(cartId != null && user != null) {
		
			Optional<Cart> optCart = cartRepository.findById(cartId);
			
			if (optCart.isPresent()) {
				
				Cart cart = optCart.get();
				cart.setUser(user);
				cart = cartRepository.save(cart);
				
				return Optional.of(cart);
			}
		}
		
		return Optional.empty();
	}

}
