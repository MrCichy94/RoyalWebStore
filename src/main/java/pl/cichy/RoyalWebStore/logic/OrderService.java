package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Page<Order> findAll(Pageable page);

    List<Order> getOrdersByClientId(Integer id);

    void setOrderForCustomer(int customerId, Order customerOrderToAdd);

    void addToOrder(int orderId, int copyId);
}
