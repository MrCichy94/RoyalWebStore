package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.AccountAlreadyExistException;
import pl.cichy.RoyalWebStore.logic.CartService;
import pl.cichy.RoyalWebStore.model.Cart;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.repository.CartRepository;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;
import pl.cichy.RoyalWebStore.model.repository.CustomerRepository;

import java.util.List;
import java.util.Set;

@Service
@RequestScope
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CopyRepository copyRepository;

    public CartServiceImpl(final CartRepository cartRepository,
                           final CopyRepository copyRepository) {
        this.cartRepository = cartRepository;
        this.copyRepository = copyRepository;
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
    public void addToCart(int cartId, int copyId) {

    }

    @Override
    public void createNewCart(int productId, int copyId) {
        try {
            Copy alreadyExistingCopy = copyRepository.getById(copyId);
            CartItem copyInCart = new CartItem();

            Cart cartToAdd = new Cart();
            cartToAdd.getCopies().add(copyInCart);
            cartRepository.save(cartToAdd);
        } catch (RuntimeException noCustomer) {
            throw new AccountAlreadyExistException(HttpStatus.BAD_REQUEST,
                    "Cart with given id already exist!",
                    new RuntimeException());
        }
    }

}
