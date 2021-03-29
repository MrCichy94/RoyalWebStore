package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.CartAlreadyExistException;
import pl.cichy.RoyalWebStore.logic.CartService;
import pl.cichy.RoyalWebStore.model.Cart;
import pl.cichy.RoyalWebStore.model.CartItem;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.repository.CartItemRepository;
import pl.cichy.RoyalWebStore.model.repository.CartRepository;
import pl.cichy.RoyalWebStore.model.repository.CopyRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequestScope
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CopyRepository copyRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(final CartRepository cartRepository,
                           final CopyRepository copyRepository,
                           final CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
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
    public void addToCart(int cartId, int copyId) {

    }

    @Override
    public void createNewCart(int productId, int copyId) {
        try {
            //DO SOMETHING WITH IT! IT IS TERRIBLE IN 21 CENTURY!
            Copy alreadyExistingCopy = copyRepository.getById(copyId);
            CartItem copyInCart = new CartItem();
            copyInCart.setCopyId(alreadyExistingCopy.getCopyId());
            copyInCart.setQuantity(alreadyExistingCopy.getQuantity());
            copyInCart.setMerchandisingCode(alreadyExistingCopy.getMerchandisingCode());
            copyInCart.setBuyNetPrice(alreadyExistingCopy.getBuyNetPrice());
            copyInCart.setBuyGrossPrice(alreadyExistingCopy.getBuyGrossPrice());
            copyInCart.setBuyVatPercentage(alreadyExistingCopy.getBuyVatPercentage());
            copyInCart.setBuyVatValue(alreadyExistingCopy.getBuyVatValue());
            copyInCart.setSellCurrentNetPrice(alreadyExistingCopy.getSellCurrentNetPrice());
            copyInCart.setSellCurrentGrossPrice(alreadyExistingCopy.getSellCurrentGrossPrice());
            copyInCart.setDiscoutValue(alreadyExistingCopy.getDiscountValue());
            copyInCart.setPercentageDiscoutValue(alreadyExistingCopy.getDiscountValue());
            cartItemRepository.save(copyInCart);

            Cart cartToAdd = new Cart();
            //CART SHOULD BE CREATED WITH DATA FROM LOGGED WITH JWT USER
            //CART SHOULD BE ACTIVE FOR A SPECIFIED TIME, THEN DISAPEAR IF NO PROCCESSED.
            //CART SHOULD BE PROCESSED IN THE OFFICIAL ORDER AND THEN SUMM QUANTITY CARTITEMS : COPIES.
            //TODO AFTER TESTING WHEN I CAN GET USER DATA FROM CONTROLLER FROM OAUTH2.
            //WORK TEMPORARILY SUSPENDED HERE. NEED MORE KNOWLEDGE AND FRESH VIEWS.

            if (cartToAdd.getCartItems() == null) {
                cartToAdd.setCartItems(Collections.singletonList(copyInCart));
            } else {
                cartToAdd.getCartItems().add(copyInCart);
            }
            cartRepository.save(cartToAdd);

        } catch (RuntimeException noCustomer) {
            throw new CartAlreadyExistException(HttpStatus.BAD_REQUEST,
                    "Cart with given id already exist!",
                    new RuntimeException());
        }

    }

}
