package com.nexio.magasin.web.rest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexio.magasin.domain.entity.Product;
import com.nexio.magasin.domain.repository.ProductRepository;
import com.nexio.magasin.web.rest.dto.ProductListDTO;

@RestController
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	/**
	 * Affiche un catalogue de produits
	 * @return
	 */
	@GetMapping(value = "/api/v1/products")
    public Page<ProductListDTO> index(@RequestParam(defaultValue = "0", required=false) int page, 
    		                          @RequestParam(defaultValue = "10", required=false) int size) {
		
		if (page < 0) page = 0;
		if (size < 1) size = 10;
		
		Pageable pageable = PageRequest.of(page, size);
		
		return productRepository.findAll(pageable)
				                .map((p) -> new ProductListDTO(p.getId(), p.getName()));
    }
	
    /**
     * Afficher le détail d’un produit
     * @param id
     * @return
     */
    @GetMapping("/api/v1/products/{id}")
    public ResponseEntity<Product> findOne(@PathVariable Long id){
    	
        Optional<Product> product = productRepository.findById(id);
        
        if(product.isPresent()) return new ResponseEntity<>(product.get(), HttpStatus.OK);
        
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
