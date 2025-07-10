package com.nexio.magasin.web.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexio.magasin.business.service.GetProductItemsByCartService;
import com.nexio.magasin.domain.entity.ProductItem;
import com.nexio.magasin.web.rest.dto.ProductItemDTO;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1")
@Api(value="Contrôleur de panier", description="Operations de afficher le contenu du panier")
public class CartController {
	
	@Autowired
	private GetProductItemsByCartService getProductItemsByCartService;
	
	/**
	 *  Affiche le contenu du panier
	 */
	@GetMapping("/carts/{id}")
    public ResponseEntity<List<ProductItemDTO>> show(@PathVariable Long id) {
		
		List<ProductItem> list = new ArrayList<>();
		
		try {
			list = getProductItemsByCartService.execute(id);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<ProductItemDTO> result = list.stream()
				                          .map((productItem) -> new ProductItemDTO(productItem))
				                          .collect(Collectors.toList());
			
		return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
