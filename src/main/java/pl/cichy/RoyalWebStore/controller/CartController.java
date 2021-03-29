package pl.cichy.RoyalWebStore.controller;

import net.bytebuddy.pool.TypePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cichy.RoyalWebStore.logic.CartService;
import pl.cichy.RoyalWebStore.model.Cart;

import javax.validation.Valid;
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
        logger.info("Read all carts!");
        return ResponseEntity.ok(cartService.findAllUnique());
    }

    @PostMapping("/products/{productId}/copies/{copyId}")
    ResponseEntity<Cart> addCopyOfProductToCart(@PathVariable int productId,
                                                @PathVariable int copyId) {
        cartService.createNewCart(productId, copyId);
        logger.info("New cart was created!");
        return ResponseEntity.created(URI.create("/")).body(null);
    }

}
