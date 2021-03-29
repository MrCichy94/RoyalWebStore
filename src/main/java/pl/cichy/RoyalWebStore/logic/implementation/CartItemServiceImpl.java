package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.logic.CartItemService;
import pl.cichy.RoyalWebStore.model.CartItem;
import pl.cichy.RoyalWebStore.model.repository.CartItemRepository;

import java.util.List;
import java.util.Set;

@Service
@RequestScope
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(final CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public Set<CartItem> findAllUnique() {
        return cartItemRepository.findAllUnique();
    }

    @Override
    public Page<CartItem> findAll(Pageable page) {
        return cartItemRepository.findAll(page);
    }

    @Override
    public void deleteById(Integer id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public CartItem save(CartItem entity) {
        return cartItemRepository.save(entity);
    }

    @Override
    public CartItem getById(Integer id) {
        return cartItemRepository.getById(id);
    }
}
