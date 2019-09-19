package com.nexio.magasin.test.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.nexio.magasin.domain.entity.Cart;
import com.nexio.magasin.domain.entity.User;
import com.nexio.magasin.domain.repository.CartRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CartRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private CartRepository cartRepository;
 
    @Test
    public void whenFindWithUserById_thenReturnCartWithUser() {
    	
        // given a user
    	User newUser = new User();
    	newUser.setEmail("new_user@nexio.com");
    	newUser.setPassword("myverysecurepassword");
    	newUser = entityManager.persist(newUser);
    	
    	// and an associated cart
        Cart cart = new Cart();
        cart.setUser(newUser);
        cart = entityManager.persist(cart);
        entityManager.flush();
     
        // when find the cart with the user
        Optional<Cart> optFoundCart = cartRepository.findWithUserById(cart.getId());
     
        // then the cart is found
        assertThat(optFoundCart.isPresent()).isEqualTo(true);
        
        Cart foundCart = optFoundCart.get();
        
        // and there is a user
        assertThat(foundCart.getUser()).isNotNull();
        assertThat(foundCart.getUser().getEmail()).isEqualTo(newUser.getEmail());
    }
}
