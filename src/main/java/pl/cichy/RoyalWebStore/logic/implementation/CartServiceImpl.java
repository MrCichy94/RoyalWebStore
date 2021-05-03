package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.CopyNotFoundException;
import pl.cichy.RoyalWebStore.logic.CartService;
import pl.cichy.RoyalWebStore.model.Cart;
import pl.cichy.RoyalWebStore.model.CartItem;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.repository.CartItemRepository;
import pl.cichy.RoyalWebStore.model.repository.CartRepository;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequestScope
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final CopyRepository copyRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(final CartRepository cartRepository,
                           final CustomerRepository customerRepository,
                           final CopyRepository copyRepository,
                           final CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.copyRepository = copyRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Set<Cart> findAllUnique() {
        return cartRepository.findAllUnique();
    }

    @Override
    public Page<Cart> findAll(Pageable page) {
        return cartRepository.findAll(page);
    }

    @Override
    public Cart getCartByCustomerId(int id) {
        return cartRepository.getCartByCustomerId(id);
    }

    @Override
    public void addToCart(int productId, int copyId, Authentication authentication) {

        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Customer customer = customerRepository.getByEmailLogin(principal.getUsername());
        int customerId = customer.getCustomerId();

        Cart cart = getCartByThisSessionIdIfExistsOrCreateNew(customerId);

        Copy copy = getCopyByCopyIdIfExistsOrThrowException(copyId);

        cart.addCartItem(new CartItem(copy));
        cartRepository.save(cart);

    }

    @Transactional
    @Override
    public void removeItem(int copyId, Authentication authentication) {

        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Customer customer = customerRepository.getByEmailLogin(principal.getUsername());
        int customerId = customer.getCustomerId();

        Cart cart = getCartByThisSessionIdIfExistsOrCreateNew(customerId);

        Copy copy = getCopyByCopyIdIfExistsOrThrowException(copyId);

        removeSingleCartItemFromCartOrIfThisIsLastDeleteCartAndCartItem(customerId, cart, copy);
    }

    private void removeSingleCartItemFromCartOrIfThisIsLastDeleteCartAndCartItem(int customerId,
                                                                                 Cart cart, Copy copy) {
        int key = cartItemRepository.getCartItemByCustomerId(customerId);
        int deleteCartItemById = cart.getCartItems().get(key).getCartItemId();

        removeSingleCartItemFromCart(cart, copy, key);

        cartRepository.save(cart);

        deleteCartAndCleanUpDB(cart, deleteCartItemById);
    }

    private void deleteCartAndCleanUpDB(Cart cart, int deleteCartItemById) {
        if (cart.getCartItems().isEmpty()) {
            cartRepository.deleteById(cart.getCartId());
            cartItemRepository.deleteById(deleteCartItemById);
        }
    }

    private void removeSingleCartItemFromCart(Cart cart, Copy copy, int key) {
        if (cart.getCartItems().get(key).getQuantity() > 1) {
            int quantityBefore = cart.getCartItems().get(key).getQuantity();
            cart.getCartItems().get(key).setQuantity(quantityBefore - 1);
        } else {
            cart.removeCartItem(new CartItem(copy));
        }
    }

    private Copy getCopyByCopyIdIfExistsOrThrowException(int copyId) {
        Copy copy = copyRepository.getById(copyId);
        if (copy == null) {
            throw new CopyNotFoundException(HttpStatus.NOT_FOUND,
                    "No copy found with id: " + copyId,
                    new RuntimeException(),
                    copyId);
        }
        return copy;
    }

    private Cart getCartByThisSessionIdIfExistsOrCreateNew(int customerId) {
        Cart cart = cartRepository.getCartByCustomerId(customerId);
        if (cart == null) {
            cart = cartRepository.save(new Cart(customerId));
        }
        return cart;
    }
}
