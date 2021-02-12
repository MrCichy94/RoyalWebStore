package pl.cichy.RoyalWebStore.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cichy.RoyalWebStore.model.Product;
import pl.cichy.RoyalWebStore.model.repository.ProductRepository;

import java.util.List;

@Repository
public interface SqlProductRepository extends ProductRepository, JpaRepository<Product, Integer> {

    @Override
    @Query(nativeQuery = true, value ="SELECT * from PRODUCTS where PRODUCT_ID=:id")
    Product getById(@Param("id") Integer id);
}
