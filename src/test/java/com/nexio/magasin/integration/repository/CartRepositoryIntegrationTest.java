package com.nexio.magasin.integration.repository;

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
import com.nexio.magasin.domain.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CartRepositoryIntegrationTest {

	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private UserRepository userRepository;
 
    @Test
    public void whenFindWithUserById_thenReturnCartWithUser() {
    	
        // given a user found in database (see data.sql)
    	Optional<User> optUser = userRepository.findByEmail("user@nexio.com");
    	assertThat(optUser.isPresent()).isEqualTo(true);
    	
    	User u = optUser.get();
    	
    	// and an associated cart
        Cart cart = new Cart();
        cart.setUser(u);
        cart = entityManager.persist(cart);
        entityManager.flush();
     
        // when find the cart with the user
        Optional<Cart> optFoundCart = cartRepository.findWithUserById(cart.getId());
     
        // then the cart is found
        assertThat(optFoundCart.isPresent()).isEqualTo(true);
        
        Cart foundCart = optFoundCart.get();
        
        // and there is a user
        assertThat(foundCart.getUser()).isNotNull();
        assertThat(foundCart.getUser().getEmail()).isEqualTo(u.getEmail());
    }
}
