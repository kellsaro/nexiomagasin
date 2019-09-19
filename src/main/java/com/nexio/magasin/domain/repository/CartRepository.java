package com.nexio.magasin.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nexio.magasin.domain.entity.Cart;
import com.nexio.magasin.domain.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	
	@Query("select c from Cart c left join fetch c.user where c.id =:id")
	Optional<Cart> findWithUserById(@Param(value = "id") Long id);
	
	Optional<Cart> findByUser(User user);
}
