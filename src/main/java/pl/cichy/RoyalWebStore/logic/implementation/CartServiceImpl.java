package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
    public Cart getCartByCustomerId(int id) {
        return cartRepository.getCartByCustomerId(id);
    }

    @Override
    public void addToCart(int productId, int copyId, Authentication authentication) {

        String username = authentication.getPrincipal().toString();
        Customer customer = customerRepository.getByEmailLogin(username);
        int customerId = customer.getCustomerId();

        Cart cart = getCartByThisCustomerIdIfExistsOrCreateNew(customerId);

        Copy copy = getCopyByCopyIdIfExistsOrThrowException(copyId);

        cart.addCartItem(new CartItem(copy));
        cartRepository.save(cart);

    }

    @Transactional
    @Override
    public void removeItem(int copyId, Authentication authentication) {

        String username = authentication.getPrincipal().toString();
        Customer customer = customerRepository.getByEmailLogin(username);
        int customerId = customer.getCustomerId();

        Cart cart = getCartByThisCustomerIdIfExistsOrCreateNew(customerId);

        Copy copy = getCopyByCopyIdIfExistsOrThrowException(copyId);

        removeSingleCartItemFromCartOrIfThisIsLastDeleteCartAndCartItem(cart, copy);
    }

    private void removeSingleCartItemFromCartOrIfThisIsLastDeleteCartAndCartItem(Cart cart, Copy copy) {
        int cartItemID = cartItemRepository.getCartItemIdByCopyId(copy.getCopyId());
        int cartItemKey = cartItemRepository.getCartItemKeyByCartItemID(cartItemID);

        removeSingleCartItemFromCart(cart, copy, cartItemID, cartItemKey);

        cartRepository.save(cart);

        deleteCartAndCleanUpDB(cart);
    }

    private void deleteCartAndCleanUpDB(Cart cart) {
        if (cart.getCartItems().isEmpty()) {
            cartRepository.deleteById(cart.getCartId());
        }
    }

    private void removeSingleCartItemFromCart(Cart cart, Copy copy, int cartItemID, int cartItemKey) {
        if (cart.getCartItems().get(cartItemKey).getQuantity() > 1) {
            int quantityBefore = cart.getCartItems().get(cartItemKey).getQuantity();
            cart.getCartItems().get(cartItemKey).setQuantity(quantityBefore - 1);
        } else {
            cart.removeCartItem(new CartItem(copy));
            cartItemRepository.deleteById(cartItemID);
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

    private Cart getCartByThisCustomerIdIfExistsOrCreateNew(int customerId) {
        Cart cart = cartRepository.getCartByCustomerId(customerId);
        if (cart == null) {
            cart = cartRepository.save(new Cart(customerId));
        }
        return cart;
    }
}
