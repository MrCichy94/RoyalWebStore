package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Cart;

import java.util.List;
import java.util.Set;

public interface CartRepository {

    List<Cart> findAll();

    Set<Cart> findAllUnique();

    Page<Cart> findAll(Pageable page);

    Cart getById(String id);

    Set<Cart> getCartsByCustomerId(String id);

    void deleteById(String cartId);

    Cart save(Cart entity);

}
