package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.CartItem;
import pl.cichy.RoyalWebStore.model.repository.CartItemRepository;

import java.util.Set;

@Repository
public interface SqlCartItemRepository extends CartItemRepository, JpaRepository<CartItem, Integer> {

    @Override
    @Query(nativeQuery = true, value = "SELECT * from CARTITEMS where CART_ITEM_ID=:id")
    CartItem getById(@Param("id") Integer id);

    @Override
    @Query(nativeQuery = true, value = "SELECT * from CARTITEMS")
    Set<CartItem> findAllUnique();

    @Override
    @Query(nativeQuery = true, value = "SELECT CART_ITEMS_KEY from CARTS_CART_ITEMS where CART_CART_ID=:id")
    int getCartItemByCustomerId(@Param("id") Integer id);

    @Override
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM CARTITEMS where CART_ITEM_ID=:id")
    void deleteById(@Param("id") Integer id);

}
