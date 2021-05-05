package pl.cichy.RoyalWebStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cichy.RoyalWebStore.logic.OrderService;
import pl.cichy.RoyalWebStore.model.Order;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@Controller
@RequestMapping("/customers")
public class OrderContorller {

    @Autowired
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderContorller.class);

    public OrderContorller() {
    }

    @GetMapping("/orders")
    ResponseEntity<Set<Order>> readAllOrders() {
        logger.info("Read all orders!");
        return ResponseEntity.ok(orderService.findAllUnique());
    }

    @GetMapping("/{id}/orders")
    ResponseEntity<Set<Order>> readAllOrdersByClientId(@PathVariable int id) {
        logger.info("Read all of the orders of this customer!");
        return ResponseEntity.ok(orderService.getOrdersByClientId(id));
    }

    @PostMapping("/customers/cart/{cartId}")
    ResponseEntity<Order> createOrderFromExistingCart(@PathVariable String cartId) {
        orderService.proccessCartToOrder(cartId);
        logger.info("New order was created!");
        return ResponseEntity.created(URI.create("/" + cartId)).build();
    }

    @PostMapping("/{customerId}")
    ResponseEntity<Order> addOrderForCustomerWithGivenId(@PathVariable int customerId,
                                                         @RequestBody @Valid Order customerOrderToAdd) {
        orderService.setOrderForCustomer(customerId, customerOrderToAdd);
        logger.info("New order was created!");
        return ResponseEntity.created(URI.create("/" + customerOrderToAdd.getOrderId())).body(customerOrderToAdd);
    }

    @PatchMapping("/{orderId}/{copyId}")
    ResponseEntity<Order> addCopyOfGivenProductToOrder(@PathVariable int orderId, @PathVariable int copyId) {
        orderService.addToOrder(orderId, copyId);
        logger.info("Copy added to order!");
        return ResponseEntity.created(URI.create("/" + copyId)).build();
    }

}
