package com.nexio.magasin.business.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexio.magasin.domain.entity.Cart;
import com.nexio.magasin.domain.entity.User;
import com.nexio.magasin.domain.repository.CartRepository;
import com.nexio.magasin.domain.repository.ProductItemRepository;
import com.nexio.magasin.domain.repository.UserRepository;

@Service
@Transactional
public class DeleteUserSessionService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductItemRepository productItemRepository;
	
	public boolean execute(String token) {
		
		Optional<User> optUser = userRepository.findBySessionToken(token);
		
		if (!optUser.isPresent()) return false;
		
		User user = optUser.get();
		
		Optional<Cart> optCart = cartRepository.findByUser(user);
		
		if (optCart.isPresent()) {
			productItemRepository.deleteByCart(optCart.get());
			cartRepository.delete(optCart.get());
		}
		
		user.setSessionToken(null);
		userRepository.save(user);
		
		return true;
	}
}
