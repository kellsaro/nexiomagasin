package com.nexio.magasin.business.service;

import com.nexio.magasin.domain.entity.Cart;

public interface GetOrCreateCartService {
	
	Cart execute(Long cartId);
}
