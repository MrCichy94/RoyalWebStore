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
import pl.cichy.RoyalWebStore.model.Customer;
import pl.cichy.RoyalWebStore.model.repository.CartItemRepository;
import pl.cichy.RoyalWebStore.model.repository.CartRepository;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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
    public Set<Cart> getCartsByCustomerId(Integer id) {
        return cartRepository.getCartsByCustomerId(id);
    }

    @Override
    public void addToCart(int productId, int copyId, HttpServletRequest request) {
        String sessionId = request.getSession(true).getId();

        //BOTH LINE WILL WORK WHEN START USIGN OAUTH2
        //Principal principal = request.getUserPrincipal();
        //Customer customer = customerRepository.getByEmailLogin(principal.getName());

        //todo: try reduce shot to repo to 1.
        Cart cart = cartRepository.getById(sessionId);
        if (cart == null) {
            //customerId hardcoded
            cart = cartRepository.save(new Cart(1, sessionId));
        }

        Copy copy = copyRepository.getById(copyId);
        if (copy == null) {
            throw new CopyNotFoundException(HttpStatus.NOT_FOUND,
                    "No copy found with id: " + copyId,
                    new RuntimeException(),
                    productId);
        }

        cart.addCartItem(new CartItem(copy));
        cartRepository.save(cart);

    }

}
