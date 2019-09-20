package com.nexio.magasin.business.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexio.magasin.domain.entity.Cart;
import com.nexio.magasin.domain.entity.Product;
import com.nexio.magasin.domain.entity.ProductItem;
import com.nexio.magasin.domain.repository.ProductItemRepository;
import com.nexio.magasin.domain.repository.ProductRepository;

public interface AddProductToCartService {

	Optional<ProductItem> execute(Long productId, Long cartId);
}
