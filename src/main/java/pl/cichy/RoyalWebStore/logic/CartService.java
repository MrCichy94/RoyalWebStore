package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Cart;

import java.util.List;
import java.util.Set;

public interface CartService {

    List<Cart> findAll();

    Set<Cart> findAllUnique();

    Page<Cart> findAll(Pageable page);

    Set<Cart> getCartsByCustomerId(Integer id);

    void addToCart(int cartId, int copyId);

    void createNewCart(int productId, int copyId);
}
