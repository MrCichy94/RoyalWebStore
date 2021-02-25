package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Order;

import java.util.List;

public interface OrderRepository {

    List<Order> findAll();

    Page<Order> findAll(Pageable page);

    Order getById(Integer id);

    List<Order> getOrdersByClientId(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Order save(Order entity);

}
