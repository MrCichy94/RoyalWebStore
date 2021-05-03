package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import pl.cichy.RoyalWebStore.model.Cart;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public interface CartService {

    List<Cart> findAll();

    Set<Cart> findAllUnique();

    Page<Cart> findAll(Pageable page);

    Cart getCartByCustomerId(int id);

    void addToCart(int productId, int copyId, Authentication authentication);

    void removeItem(int copyId, Authentication authentication);


    //void removeCart(int copyId, HttpServletRequest request);
}
