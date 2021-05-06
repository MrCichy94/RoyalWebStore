package pl.cichy.RoyalWebStore.logic.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.cichy.RoyalWebStore.exception.CustomerNotFoundException;
import pl.cichy.RoyalWebStore.exception.OrderNotFoundException;
import pl.cichy.RoyalWebStore.logic.OrderService;
import pl.cichy.RoyalWebStore.model.*;
import pl.cichy.RoyalWebStore.model.repository.*;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequestScope
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CopyRepository copyRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public OrderServiceImpl(final OrderRepository orderRepository,
                            final CustomerRepository customerRepository,
                            final CopyRepository copyRepository,
                            final CartRepository cartRepository,
                            final CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.copyRepository = copyRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }


    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Set<Order> findAllUnique() {
        return orderRepository.findAllUnique();
    }

    @Override
    public Page<Order> findAll(Pageable page) {
        return orderRepository.findAll(page);
    }

    @Override
    public Set<Order> getOrdersByClientId(Integer id) {
        return orderRepository.getOrdersByClientId(id);
    }

    @Transactional
    @Override
    public void proccessCartToOrder(String cartId) {
        Cart cart = cartRepository.getById(cartId);
        Collection<CartItem> cartItemInCart = cart.getCartItems().values();
        List<Integer> cartItemIds = new ArrayList<>();
        List<Copy> copiesInCart = new ArrayList<>();

        for(CartItem c: cartItemInCart) {
            copiesInCart.add(c.getCopy());
            cartItemIds.add(c.getCartItemId());
            //mamy listę egzemplarzy z koszyka- czas zrobić z nich zamówienie
        }

        Order order = new Order("NO");
        order.setCopies(copiesInCart);
        order.setCustomerId(cart.getCustomerId());

        //a teraz podepnij ten order pod customersa
        Customer customer = customerRepository.getById(cart.getCustomerId());
        Set<Order> customerOrders = customer.getOrders();
        customerOrders.add(order);

        customerRepository.save(customer);

        //a teraz wyczysc cart z caritemsow
        Iterator<CartItem> iter = cartItemInCart.iterator();
        while (iter.hasNext()) {
            CartItem c = iter.next();
            iter.remove();
        }
        //pamiętaj o DB
        for (Integer itemId : cartItemIds) {
            cartItemRepository.deleteById(itemId);
        }
        cartRepository.save(cart);

        if (cart.getCartItems().isEmpty()) {
            cartRepository.deleteById(cart.getCartId());
        }
    }

    @Override
    public void setOrderForCustomer(int customerId, Order customerOrderToAdd) {

        try {
            Customer customerToActualizeOrder = customerRepository.getById(customerId);
            Set<Order> listOfOrdersToRefresh = customerRepository.getById(customerId).getOrders();

            assignDataToOrderObject(customerId, customerOrderToAdd);

            listOfOrdersToRefresh.add(customerOrderToAdd);

            customerToActualizeOrder.setOrders(listOfOrdersToRefresh);
            customerRepository.save(customerToActualizeOrder);
        } catch (RuntimeException noCustomer) {
            throw new CustomerNotFoundException(HttpStatus.NOT_FOUND,
                    "No customer found with id: " + customerId,
                    new RuntimeException(),
                    customerId);
        }

    }

    @Override
    public void addToOrder(int orderId, int copyId) {

        try {

            Order orderToAddThisCopy = orderRepository.getById(orderId);
            List<Copy> copies = orderToAddThisCopy.getCopies();

            copies.add(copyRepository.getById(copyId));

            orderToAddThisCopy.setCopies(copies);
            orderRepository.save(orderToAddThisCopy);
        } catch (RuntimeException noOrder) {
            throw new OrderNotFoundException(HttpStatus.NOT_FOUND,
                    "No order found with id: " + orderId,
                    new RuntimeException(),
                    orderId);
        }
    }

    private void assignDataToOrderObject(int customerId, Order customerOrderToAdd) {

        customerOrderToAdd.setOrderId(customerOrderToAdd.getOrderId());
        customerOrderToAdd.setCustomerId(customerId);
        customerOrderToAdd.setPaid(customerOrderToAdd.getPaid());

    }
}


