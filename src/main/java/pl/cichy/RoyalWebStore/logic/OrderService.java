package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderService {

    List<Order> findAll();

    Set<Order> findAllUnique();

    Page<Order> findAll(Pageable page);

    Set<Order> getOrdersByClientId(Integer id);

    void proccessCartToOrder(String cartId);

    void setOrderForCustomer(int customerId, Order customerOrderToAdd);

    void addToOrder(int orderId, int copyId);

    Order getCustomersOrderById(int customerId, int orderId);
}
