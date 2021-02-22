package pl.cichy.RoyalWebStore.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Copy;
import pl.cichy.RoyalWebStore.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAll();

    Page<Order> findAll(Pageable page);

    Optional<Order> findById(Integer id);

    List<Order> getOrdersByClientId(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Order save(Order entity);

    void setOrderForCustomer(int customerId, Order customerOrderToAdd);

    void addToOrder(int orderId, int copyId, Copy copyToAddToThisOrder);
}
