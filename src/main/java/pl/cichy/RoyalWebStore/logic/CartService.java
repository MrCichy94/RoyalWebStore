package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Cart;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public interface CartService {

    List<Cart> findAll();

    Set<Cart> findAllUnique();

    Page<Cart> findAll(Pageable page);

    Set<Cart> getCartsByCustomerId(Integer id);

    void addToCart(int productId, int copyId, HttpServletRequest request);

    void removeItem(int productId, int copyId, HttpServletRequest request);

}
