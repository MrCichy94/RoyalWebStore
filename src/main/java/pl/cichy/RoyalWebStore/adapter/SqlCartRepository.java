package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Cart;
import pl.cichy.RoyalWebStore.model.repository.CartRepository;

import java.util.Set;

@Repository
public interface SqlCartRepository extends CartRepository, JpaRepository<Cart, Integer> {

    @Override
    @Query(nativeQuery = true, value = "SELECT * from CARTS where CUSTOMER_ID=:id")
    Set<Cart> getCartsByCustomerId(@Param("id") Integer id);

    @Override
    @Query(nativeQuery = true, value = "SELECT * from CARTS where CART_ID=:id")
    Cart getById(@Param("id") Integer id);

    @Override
    @Query(nativeQuery = true, value = "SELECT * from CARTS")
    Set<Cart> findAllUnique();
}
