package com.nexio.magasin.business.service;

import java.util.List;

import com.nexio.magasin.domain.entity.ProductItem;

public interface GetProductItemsByCartService {
	
	List<ProductItem> execute(Long cartId);
}
