package com.nexio.magasin.business.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexio.magasin.domain.entity.Cart;
import com.nexio.magasin.domain.entity.Product;
import com.nexio.magasin.domain.entity.ProductItem;
import com.nexio.magasin.domain.repository.CartRepository;
import com.nexio.magasin.domain.repository.ProductItemRepository;
import com.nexio.magasin.domain.repository.ProductRepository;

@Transactional
@Service
public class RemoveProductFromCartService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductItemRepository productItemRepository;
	
	@Autowired
	private CartRepository cartRepository;

	public boolean execute(Long productId, Long cartId){
		
		try {

			Optional<Product> optProduct = productRepository.findById(productId);
			Optional<Cart> optCart = cartRepository.findById(cartId);
			
			if (!optProduct.isPresent() || !optCart.isPresent()) return false;
			
			Product product = optProduct.get();
			
			Cart cart = optCart.get();
			
			Optional<ProductItem> optProductItem = productItemRepository.findByCartInAndProductIn(cart, product); 
			
			if(!optProductItem.isPresent()) return false;
			
			ProductItem productItem = optProductItem.get();
			
			productItem.setQuantity(productItem.getQuantity() - 1);
			
			if (productItem.getQuantity() == 0)
				productItemRepository.deleteById(productItem.getId());
			else
				productItemRepository.save(productItem);
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
