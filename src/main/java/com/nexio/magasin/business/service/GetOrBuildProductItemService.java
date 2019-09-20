package com.nexio.magasin.business.service;

import com.nexio.magasin.domain.entity.Cart;
import com.nexio.magasin.domain.entity.Product;
import com.nexio.magasin.domain.entity.ProductItem;

public interface GetOrBuildProductItemService {
	
	ProductItem execute(Product product, Cart cart);
}
