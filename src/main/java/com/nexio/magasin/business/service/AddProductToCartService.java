package com.nexio.magasin.business.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexio.magasin.domain.entity.Cart;
import com.nexio.magasin.domain.entity.Product;
import com.nexio.magasin.domain.entity.ProductItem;
import com.nexio.magasin.domain.repository.ProductItemRepository;
import com.nexio.magasin.domain.repository.ProductRepository;

@Service
public class AddProductToCartService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductItemRepository productItemRepository;
	
	@Autowired
	private GetOrBuildProductItemService getOrBuildProductItemService;
	
	@Autowired
	private GetOrCreateCartService getOrCreateCartService;
	
	public Optional<ProductItem> execute(Long productId, Long cartId){
		
		ProductItem productItem = null;
		
		try {

			Optional<Product> optProduct = productRepository.findById(productId);
			
			if (!optProduct.isPresent()) return Optional.empty();
			
			Product product = optProduct.get();
			
			Cart cart = getOrCreateCartService.execute(cartId);
			
			productItem = getOrBuildProductItemService.execute(product, cart); 
			
			if(productItem.getId() != null) productItem.setQuantity(productItem.getQuantity() + 1);
			
			productItem = productItemRepository.save(productItem);
		
		}catch(Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
		
		return Optional.of(productItem);
	}
}
