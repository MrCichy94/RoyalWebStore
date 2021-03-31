package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.CopyNotFoundException;
import pl.cichy.RoyalWebStore.logic.CartService;
import pl.cichy.RoyalWebStore.model.Cart;
import pl.cichy.RoyalWebStore.model.CartItem;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.repository.CartItemRepository;
import pl.cichy.RoyalWebStore.model.repository.CartRepository;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
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
    public Set<Cart> getCartsByCustomerId(String id) {
        return cartRepository.getCartsByCustomerId(id);
    }

    @Override
    public void addToCart(int productId, int copyId, HttpServletRequest request) {
        String sessionId = request.getSession(true).getId();

        //BOTH LINE WILL WORK WHEN START USIGN OAUTH2
        //Principal principal = request.getUserPrincipal();
        //Customer customer = customerRepository.getByEmailLogin(principal.getName());
        //->customer.getCustomerId();

        //todo: try reduce shot to repo to 1.
        Cart cart = getCartByThisSessionIdIfExistsOrCreateNew(sessionId);

        Copy copy = getCopyByCopyIdIfExistsOrThrowException(copyId);

        cart.addCartItem(new CartItem(copy));
        cartRepository.save(cart);

    }

    @Transactional
    @Override
    public void removeItem(int copyId, HttpServletRequest request) {
        String sessionId = request.getSession(true).getId();

        //BOTH LINE WILL WORK WHEN START USIGN OAUTH2
        //Principal principal = request.getUserPrincipal();
        //Customer customer = customerRepository.getByEmailLogin(principal.getName());
        //->customer.getCustomerId();

        Cart cart = getCartByThisSessionIdIfExistsOrCreateNew(sessionId);

        Copy copy = getCopyByCopyIdIfExistsOrThrowException(copyId);

        removeSingleCartItemFromCartOrIfThisIsLastDeleteCartAndCartItem(sessionId, cart, copy);
    }

    private void removeSingleCartItemFromCartOrIfThisIsLastDeleteCartAndCartItem(String sessionId,
                                                                                 Cart cart, Copy copy) {
        int key = cartItemRepository.getCartItemBySession(sessionId);
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

    private Cart getCartByThisSessionIdIfExistsOrCreateNew(String sessionId) {
        Cart cart = cartRepository.getById(sessionId);
        if (cart == null) {
            cart = cartRepository.save(new Cart(1, sessionId));
        }
        return cart;
    }
}
