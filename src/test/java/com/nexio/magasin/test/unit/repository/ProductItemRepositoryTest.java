package com.nexio.magasin.test.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.nexio.magasin.domain.entity.Cart;
import com.nexio.magasin.domain.entity.Product;
import com.nexio.magasin.domain.entity.ProductItem;
import com.nexio.magasin.domain.repository.CartRepository;
import com.nexio.magasin.domain.repository.ProductItemRepository;
import com.nexio.magasin.domain.repository.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductItemRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private ProductItemRepository productItemRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
 
    @Test
    public void whenFindWithUserById_thenReturnCartWithUser() {
    	
    	productItemRepository.deleteAll();
    	cartRepository.deleteAll();
    	productRepository.deleteAll();
    	
    	Cart cart = entityManager.persist(new Cart());
    	
    	Product product = new Product();
    	product.setCode("CODEOFTHEPRODUCT");
    	product.setDetail("detail of the product");
    	product.setName("Product 1");
    	product.setPrice(new BigDecimal("10.00"));
    	product = entityManager.persist(product);
        
        ProductItem productItem = new ProductItem();
        productItem.setCart(cart);
        productItem.setPrice(product.getPrice());
        productItem.setProduct(product);
        productItem.setQuantity(1);
        productItem = entityManager.persist(productItem);
        
        entityManager.flush();
        
        List<ProductItem> productItemList = productItemRepository.findAllByCartId(cart.getId());
     
        // then the cart is found
        assertThat(productItemList.isEmpty()).isEqualTo(false);
        
        assertThat(productItemList.size()).isEqualTo(1);
        
        assertThat(productItemList.get(0)).isEqualTo(productItem);
    }
}
