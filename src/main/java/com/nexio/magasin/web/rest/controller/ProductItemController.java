package com.nexio.magasin.web.rest.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexio.magasin.business.service.AddProductToCartService;
import com.nexio.magasin.business.service.RemoveProductFromCartService;
import com.nexio.magasin.domain.entity.ProductItem;
import com.nexio.magasin.domain.repository.CartRepository;
import com.nexio.magasin.web.rest.dto.ProductIdDTO;
import com.nexio.magasin.web.rest.dto.ProductItemDTO;
import com.nexio.magasin.web.rest.util.CookieUtil;

@RestController
public class ProductItemController {
	
	@Autowired
	private AddProductToCartService addProductToCartService;
	
	@Autowired
	private RemoveProductFromCartService removeProductFromCartService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CookieUtil cookieUtil;
	
	/** 
	 * Ajouter un produit au panier
	 * @param productId
	 * @return
	 */
    @PostMapping("/api/v1/product_items")
    public ResponseEntity<ProductItemDTO> create(@RequestBody ProductIdDTO product, @CookieValue(required = false, name = "cart_id") Long cartId, HttpServletResponse response) {
    
    	Optional<ProductItem> optProductItem = addProductToCartService.execute(product.getId(), cartId);
    	
        if(optProductItem.isPresent()) { 
        	
        	ProductItem productItem = optProductItem.get();
        	
        	if (cartId == null) {
        		
	        	Map<String, Object> cookies = cookieUtil.buildCookiesMap();
	        	
	        	cookies.put("cart_id", productItem.getCart().getId());
	        	
	        	cookieUtil.setCookies(response, cookies.entrySet());
        	}
        	
        	return new ResponseEntity<>(new ProductItemDTO(productItem), HttpStatus.CREATED);
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
	
	/**
	 *  Enleve un produit du panier
	 */
    @DeleteMapping("/api/v1/product_items")
    public ResponseEntity<ProductItem> delete(@RequestBody ProductIdDTO product, @CookieValue(required = true, name = "cart_id") Long cartId, HttpServletRequest request, HttpServletResponse response) {
    	
    	if (cartId == null) return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    		
    	if (product.getId() == null) return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    	
    	return new ResponseEntity<>(removeProductFromCartService.execute(product.getId(), cartId) ? HttpStatus.OK : HttpStatus.NOT_MODIFIED);
    }
}
