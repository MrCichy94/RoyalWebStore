package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Order;
import pl.cichy.RoyalWebStore.model.repository.OrderRepository;

import java.util.Set;

@Repository
public interface SqlOrderRepository extends OrderRepository, JpaRepository<Order, Integer> {

    @Override
    @Query(nativeQuery = true, value = "SELECT * from ORDERS where CUSTOMER_ID=:id")
    Set<Order> getOrdersByClientId(@Param("id") Integer id);

    @Override
    @Query(nativeQuery = true, value = "SELECT * from ORDERS where ORDER_ID=:id")
    Order getById(@Param("id") Integer id);

    @Override
    @Query(nativeQuery = true, value = "SELECT * from ORDERS")
    Set<Order> findAllUnique();

}
