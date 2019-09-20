package com.nexio.magasin.business.service;

public interface RemoveProductFromCartService {

	boolean execute(Long productId, Long cartId);
}
