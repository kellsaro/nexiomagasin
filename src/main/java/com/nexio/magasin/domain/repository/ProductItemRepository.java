package com.nexio.magasin.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nexio.magasin.domain.entity.Cart;
import com.nexio.magasin.domain.entity.Product;
import com.nexio.magasin.domain.entity.ProductItem;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long>{
	
	Optional<ProductItem> findByCartInAndProductIn(Cart cart, Product product);
	
	@Query("select productItem from ProductItem productItem where productItem.cart.id =:id")
	List<ProductItem> findAllByCartId(@Param(value = "id") Long cartId);
	
	long deleteByCart(Cart cart);
}
