package com.nexio.magasin.business.service;

import java.util.Optional;

import com.nexio.magasin.domain.entity.Cart;
import com.nexio.magasin.domain.entity.User;

public interface LinkCartWithUserService {

	Optional<Cart> execute(Long cartId, User user);
}
