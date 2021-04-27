package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.cichy.RoyalWebStore.logic.CartService;
import pl.cichy.RoyalWebStore.model.Cart;
import pl.cichy.RoyalWebStore.model.CartItem;
import pl.cichy.RoyalWebStore.model.Order;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Set;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    public CartController() {
    }

    @GetMapping("/customers/carts")
    ResponseEntity<Set<Cart>> readAllCarts() {
        logger.info("Read all carts.");
        return ResponseEntity.ok(cartService.findAllUnique());
    }

    @GetMapping("/customers/{id}/cart")
    ResponseEntity<Cart> readCartByClientId(@PathVariable int id) {
        logger.info("Read cart of this customer!");
        return ResponseEntity.ok(cartService.getCartByCustomerId(id));
    }

    @PostMapping("/products/{productId}/copies/{copyId}")
    ResponseEntity<CartItem> addCopyOfProductToCart(@PathVariable int productId,
                                                    @PathVariable int copyId,
                                                    HttpServletRequest request) {
        cartService.addToCart(productId, copyId, request);
        logger.info("New cart was created.");
        return ResponseEntity.created(URI.create("/")).body(null);
    }

    @PatchMapping("/products/{productId}/copies/{copyId}")
    ResponseEntity<CartItem> removeCopyOfProductFromCart(@PathVariable int productId,
                                                         @PathVariable int copyId,
                                                         HttpServletRequest request) {
        cartService.removeItem(copyId, request);
        //cartService.removeCart(copyId, request);
        logger.info("Remove copy from cart.");
        return ResponseEntity.created(URI.create("/")).body(null);
    }

}
