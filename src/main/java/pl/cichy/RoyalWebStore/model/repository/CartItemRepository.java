package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.CartItem;

import java.util.List;
import java.util.Set;

public interface CartItemRepository {

    List<CartItem> findAll();

    Set<CartItem> findAllUnique();

    Page<CartItem> findAll(Pageable page);

    void deleteById(Integer id);

    CartItem save(CartItem entity);

    CartItem getById(Integer id);

    int getCartItemBySession(String copyId);

}
