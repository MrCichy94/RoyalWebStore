package pl.cichy.RoyalWebStore.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.cichy.RoyalWebStore.model.Order;

import java.util.List;
import java.util.Set;

public interface OrderRepository {

    List<Order> findAll();

    Set<Order> findAllUnique();

    Page<Order> findAll(Pageable page);

    Order getById(Integer id);

    Set<Order> getOrdersByClientId(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    Order save(Order entity);

}
